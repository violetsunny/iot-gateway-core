package top.iot.gateway.core.message;

public class DisconnectDeviceMessage extends CommonDeviceMessage<DisconnectDeviceMessage> implements RepayableDeviceMessage<DisconnectDeviceMessageReply> {

    @Override
    public DisconnectDeviceMessageReply newReply() {
        return new DisconnectDeviceMessageReply().from(this);
    }

    public MessageType getMessageType() {
        return MessageType.DISCONNECT;
    }

}
