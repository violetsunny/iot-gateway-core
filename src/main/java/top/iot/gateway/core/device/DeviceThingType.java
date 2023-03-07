package top.iot.gateway.core.device;

import top.iot.gateway.core.things.ThingType;
import top.iot.gateway.core.things.ThingTypes;
import top.iot.gateway.core.things.TopicSupport;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DeviceThingType implements ThingType, TopicSupport {
    device("设备");

    static {
        for (DeviceThingType value : values()) {
            ThingTypes.register(value);
        }
    }
    @Getter
    private final String name;

    @Override
    public String getId() {
        return name();
    }

}
