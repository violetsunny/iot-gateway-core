package top.iot.gateway.core.defaults;

import top.iot.gateway.core.Configurable;
import top.iot.gateway.core.config.ConfigStorage;
import top.iot.gateway.core.config.ConfigStorageManager;
import top.iot.gateway.core.config.StorageConfigurable;
import top.iot.gateway.core.device.DeviceConfigKey;
import reactor.core.publisher.Mono;
import top.iot.gateway.core.things.*;

import java.util.HashMap;
import java.util.Map;

class DefaultThingTemplate implements ThingTemplate, StorageConfigurable {

    private final String id;

    private final Mono<ConfigStorage> storageMono;

    private final Mono<ThingMetadata> metadataMono;

    private volatile long lastMetadataTime = -1;

    private volatile ThingMetadata metadataCache;

    private final ThingMetadataCodec metadataCodec;

    public DefaultThingTemplate(ThingType thingType,
                                String id,
                                ConfigStorageManager storageManager,
                                ThingMetadataCodec metadataCodec) {
        this.id = id;
        this.storageMono = storageManager.getStorage("thing-template:" + thingType.getId() + ":" + id);
        this.metadataCodec = metadataCodec;
        this.metadataMono = this
                //获取最后更新物模型的时间
                .getConfig(ThingsConfigKeys.lastMetadataTimeKey)
                .flatMap(i -> {
                    //如果时间一致,则直接返回物模型缓存.
                    if (i.equals(lastMetadataTime) && metadataCache != null) {
                        return Mono.just(metadataCache);
                    }
                    lastMetadataTime = i;

                    //加载真实的物模型
                    return this
                            .getConfig(DeviceConfigKey.metadata)
                            .flatMap(metadataCodec::decode)
                            .doOnNext(metadata -> metadataCache = metadata);

                });
    }

    @Override
    public Mono<? extends Configurable> getParent() {
        return Mono.empty();
    }

    @Override
    public Mono<ConfigStorage> getReactiveStorage() {
        return storageMono;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Mono<ThingMetadata> getMetadata() {
        return metadataMono;
    }

    @Override
    public Mono<Boolean> updateMetadata(String metadata) {
        Map<String, Object> configs = new HashMap<>();
        configs.put(DeviceConfigKey.metadata.getKey(), metadata);
        return setConfigs(configs);
    }

    @Override
    public Mono<Boolean> setConfigs(Map<String, Object> conf) {
        Map<String, Object> configs = new HashMap<>(conf);
        if (conf.containsKey(DeviceConfigKey.metadata.getKey())) {
            configs.put(ThingsConfigKeys.lastMetadataTimeKey.getKey(), lastMetadataTime = System.currentTimeMillis());
            return StorageConfigurable.super
                    .setConfigs(configs)
                    .doOnNext(suc -> this.metadataCache = null)
                    .thenReturn(true);
        }
        return StorageConfigurable.super.setConfigs(configs);
    }

    @Override
    public Mono<Boolean> updateMetadata(ThingMetadata metadata) {
        return this.metadataCodec
                .encode(metadata)
                .flatMap(this::updateMetadata);
    }
}
