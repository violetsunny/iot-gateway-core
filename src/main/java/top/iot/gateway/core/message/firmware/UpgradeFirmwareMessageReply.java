package top.iot.gateway.core.message.firmware;

import top.iot.gateway.core.message.CommonDeviceMessageReply;
import top.iot.gateway.core.message.MessageType;
import lombok.Getter;
import lombok.Setter;

/**
 * 固件更新回复
 *
 * @author zhouhao
 * @since 1.0.3
 */
@Getter
@Setter
public class UpgradeFirmwareMessageReply extends CommonDeviceMessageReply<UpgradeFirmwareMessageReply> {

    @Override
    public MessageType getMessageType() {
        return MessageType.UPGRADE_FIRMWARE_REPLY;
    }
}
