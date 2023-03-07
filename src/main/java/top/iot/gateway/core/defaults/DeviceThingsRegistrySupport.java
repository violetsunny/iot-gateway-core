package top.iot.gateway.core.defaults;

import lombok.AllArgsConstructor;
import top.iot.gateway.core.device.DeviceInfo;
import top.iot.gateway.core.device.DeviceRegistry;
import top.iot.gateway.core.device.DeviceThingType;
import top.iot.gateway.core.device.ProductInfo;
import reactor.core.publisher.Mono;
import top.iot.gateway.core.things.*;

import javax.annotation.Nonnull;

@AllArgsConstructor
public class DeviceThingsRegistrySupport implements ThingsRegistrySupport {

    private final DeviceRegistry registry;

    public void checkThingType(String type) {
        if (!DeviceThingType.device.getId().equals(type)) {
            throw new UnsupportedOperationException("unsupported thing type:" + type);
        }
    }

    @Override
    public Mono<Thing> getThing(@Nonnull String thingType, @Nonnull String thingId) {
        checkThingType(thingType);
        return registry
                .getDevice(thingId)
                .cast(Thing.class);
    }

    @Override
    public Mono<ThingTemplate> getTemplate(@Nonnull String thingType, @Nonnull String templateId) {
        return registry
                .getProduct(templateId)
                .cast(ThingTemplate.class);
    }

    @Override
    public Mono<Thing> register(@Nonnull String thingType, @Nonnull ThingInfo info) {
        checkThingType(thingType);

        return registry
                .register(DeviceInfo.builder()
                                    .id(info.getId())
                                    .productId(info.getTemplateId())
                                    .metadata(info.getMetadata())
                                    .configuration(info.getConfiguration())
                                    .build())
                .cast(Thing.class);
    }

    @Override
    public Mono<Void> unregisterThing(@Nonnull String thingType, @Nonnull String thingId) {
        checkThingType(thingType);
        return registry.unregisterDevice(thingId);
    }

    @Override
    public Mono<ThingTemplate> register(@Nonnull String thingType, @Nonnull ThingTemplateInfo templateInfo) {
        checkThingType(thingType);
        return registry
                .register(ProductInfo.builder()
                                     .id(templateInfo.getId())
                                     .metadata(templateInfo.getMetadata())
                                     .configuration(templateInfo.getConfiguration())
                                     .build())
                .cast(ThingTemplate.class);
    }

    @Override
    public Mono<Void> unregisterTemplate(@Nonnull String thingType, @Nonnull String templateId) {
        checkThingType(thingType);
        return registry.unregisterProduct(templateId);
    }

    @Override
    public boolean isSupported(String thingType) {
        return DeviceThingType.device.getId().equals(thingType);
    }
}
