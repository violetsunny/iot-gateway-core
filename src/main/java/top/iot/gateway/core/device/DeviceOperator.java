package top.iot.gateway.core.device;


import top.iot.gateway.core.device.session.DeviceSessionManager;
import top.iot.gateway.core.metadata.DeviceMetadata;
import top.iot.gateway.core.server.session.DeviceSession;
import top.iot.gateway.core.things.Thing;
import top.iot.gateway.core.things.ThingType;
import top.iot.gateway.core.ProtocolSupport;
import top.iot.gateway.core.Value;
import top.iot.gateway.core.Values;
import top.iot.gateway.core.config.ConfigKey;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 设备操作接口,用于发送指令到设备{@link DeviceOperator#messageSender()}以及获取配置等相关信息
 *
 * @author zhouhao
 * @since 1.0.0
 */
public interface DeviceOperator extends Thing {

    @Override
    default ThingType getType() {
        return DeviceThingType.device;
    }

    /**
     * @return 设备ID
     */
    String getDeviceId();

    /**
     * @return 当前设备连接所在服务器ID，如果设备未上线 {@link DeviceState#online}，则返回<code>null</code>
     */
    Mono<String> getConnectionServerId();

    /**
     * @return 当前设备连接会话ID
     */
    Mono<String> getSessionId();

    /**
     * 获取设备地址,通常是ip地址.
     *
     * @return 地址
     */
    Mono<String> getAddress();

    /**
     * 设置设备地址
     *
     * @param address 地址
     * @return Mono
     */
    Mono<Void> setAddress(String address);

    /**
     * @param state 状态
     * @see DeviceState#online
     */
    Mono<Boolean> putState(byte state);

    /**
     * 获取设备当前缓存的状态,此状态可能与实际的状态不一致.
     *
     * @return 获取当前状态
     * @see DeviceState
     */
    Mono<Byte> getState();

    /**
     * 检查设备的真实状态,此操作将检查设备真实的状态.
     * 如果设备协议中指定了{@link ProtocolSupport#getStateChecker()},则将调用指定的状态检查器进行检查.
     * <br>
     * 默认的状态检查逻辑:
     * <br>
     * <img src="doc-files/device-state-check.svg">
     *
     * @see DeviceStateChecker
     */
    Mono<Byte> checkState();

    /**
     * @return 上线时间
     */
    Mono<Long> getOnlineTime();

    /**
     * @return 离线时间
     */
    Mono<Long> getOfflineTime();

    /**
     * 设备上线,通常不需要手动调用
     *
     * @param serverId  设备所在服务ID
     * @param sessionId 会话ID
     */
    default Mono<Boolean> online(String serverId, String sessionId) {
        return online(serverId, sessionId, null);
    }

    Mono<Boolean> online(String serverId, String sessionId, String address);

    /**
     * 设备上线,通常不需要手动调用
     *
     * @param serverId   设备所在服务ID {@link DeviceSessionManager#getCurrentServerId()}
     * @param address    设备地址
     * @param onlineTime 上线时间 {@link DeviceSession#connectTime()} 大于0有效
     */
    Mono<Boolean> online(String serverId, String address, long onlineTime);

    /**
     * 获取设备自身的配置,如果配置不存在则返回{@link Mono#empty()}
     *
     * @param key 配置Key
     * @return 配置值
     */
    Mono<Value> getSelfConfig(String key);

    /**
     * 获取设备自身的多个配置,不会返回{@link Mono#empty()},通过从{@link Values}中获取对应的值
     *
     * @param keys 配置key列表
     * @return 配置值
     */
    Mono<Values> getSelfConfigs(Collection<String> keys);

    /**
     * 获取设备自身的多个配置
     *
     * @param keys 配置key列表
     * @return 配置值
     */
    default Mono<Values> getSelfConfigs(String... keys) {
        return getSelfConfigs(Arrays.asList(keys));
    }

    /**
     * 获取设备自身的配置
     *
     * @param key 配置key
     * @return 配置值
     * @see DeviceConfigKey
     */
    default <V> Mono<V> getSelfConfig(ConfigKey<V> key) {
        return getSelfConfig(key.getKey())
                .map(value -> value.as(key.getValueType()));
    }

    /**
     * 获取设备自身的多个配置
     *
     * @param keys 配置key
     * @return 配置值
     * @see DeviceConfigKey
     */
    default Mono<Values> getSelfConfigs(ConfigKey<?>... keys) {
        return getSelfConfigs(Arrays.stream(keys).map(ConfigKey::getKey).collect(Collectors.toSet()));
    }

    /**
     * @return 是否在线
     */
    default Mono<Boolean> isOnline() {
        return checkState()
                .map(state -> state.equals(DeviceState.online))
                .defaultIfEmpty(false);
    }

    /**
     * 设置设备离线
     *
     * @see DeviceState#offline
     */
    Mono<Boolean> offline();

    /**
     * 断开设备连接
     *
     * @return 断开结果
     */
    Mono<Boolean> disconnect();

    /**
     * 进行授权
     *
     * @param request 授权请求
     * @return 授权结果
     * @see MqttAuthenticationRequest
     */
    Mono<AuthenticationResponse> authenticate(AuthenticationRequest request);

    /**
     * @return 获取设备的元数据
     */
    Mono<DeviceMetadata> getMetadata();

    /**
     * @return 获取此设备使用的协议支持
     */
    Mono<ProtocolSupport> getProtocol();

    /**
     * @return 消息发送器, 用于发送消息给设备
     */
    DeviceMessageSender messageSender();

    /**
     * 设置当前设备的独立物模型,如果没有设置,这使用产品的物模型配置
     *
     * @param metadata 物模型
     */
    Mono<Boolean> updateMetadata(String metadata);

    /**
     * 重置当前设备的独立物模型
     *
     * @return void
     * @since 1.1.6
     */
    Mono<Void> resetMetadata();

    /**
     * @return 获取设备对应的产品操作接口
     */
    Mono<DeviceProductOperator> getProduct();

    @Override
    default Mono<DeviceProductOperator> getTemplate() {
        return getProduct();
    }
}
