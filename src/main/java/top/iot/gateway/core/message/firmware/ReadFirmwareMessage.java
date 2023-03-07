package top.iot.gateway.core.message.firmware;

import top.iot.gateway.core.message.CommonDeviceMessage;
import top.iot.gateway.core.message.MessageType;
import top.iot.gateway.core.message.RepayableDeviceMessage;

/**
 * 读取设备固件信息
 *
 * @since 1.0.3
 */
public class ReadFirmwareMessage extends CommonDeviceMessage<ReadFirmwareMessage> implements RepayableDeviceMessage<ReadFirmwareMessageReply> {

    @Override
    public ReadFirmwareMessageReply newReply() {
        return new ReadFirmwareMessageReply().from(this);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.READ_FIRMWARE;
    }
}
