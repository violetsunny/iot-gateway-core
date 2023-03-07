package top.iot.gateway.core.message;

public class DisconnectDeviceMessageReply extends CommonDeviceMessageReply<DisconnectDeviceMessageReply> {
    public MessageType getMessageType() {
        return MessageType.DISCONNECT_REPLY;
    }

}
