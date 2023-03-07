package top.iot.gateway.core.device;

import top.iot.gateway.core.metadata.DeviceMetadata;
import top.iot.gateway.core.things.ThingTemplate;
import top.iot.gateway.core.ProtocolSupport;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 设备产品型号操作
 *
 * @author zhouhao
 * @since 1.0.0
 */
public interface DeviceProductOperator extends ThingTemplate {

    String getId();

    /**
     * @return 设备产品物模型
     */
    Mono<DeviceMetadata> getMetadata();

    /**
     * 更新设备型号元数据信息
     *
     * @param metadata 元数据信息
     */
    Mono<Boolean> updateMetadata(String metadata);

    /**
     * @return 获取协议支持
     */
    Mono<ProtocolSupport> getProtocol();

    /**
     * @return 获取产品下的所有设备
     */
    Flux<DeviceOperator> getDevices();
}
