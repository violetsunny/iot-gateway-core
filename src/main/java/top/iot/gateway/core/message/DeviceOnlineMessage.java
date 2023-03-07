package top.iot.gateway.core.message;

public class DeviceOnlineMessage extends CommonDeviceMessage<DeviceOnlineMessage> {
    public MessageType getMessageType() {
        return MessageType.ONLINE;
    }
}
