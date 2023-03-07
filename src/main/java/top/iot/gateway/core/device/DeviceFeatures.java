package top.iot.gateway.core.device;

import top.iot.gateway.core.metadata.Feature;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeviceFeatures implements Feature {

    //标识使用此协议的设备支持固件升级
    supportFirmware("支持固件升级");

    private final String name;

    @Override
    public String getId() {
        return name();
    }

    @Override
    public String getType() {
        return "device-manage";
    }
}
