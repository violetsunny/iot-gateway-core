package top.iot.gateway.core.message.state;

import top.iot.gateway.core.device.DeviceConfigKey;
import top.iot.gateway.core.message.ChildDeviceMessage;
import top.iot.gateway.core.message.CommonDeviceMessage;
import top.iot.gateway.core.message.MessageType;
import top.iot.gateway.core.message.RepayableDeviceMessage;
import org.hswebframework.web.id.IDGenerator;

/**
 * 设备状态检查消息，通常用于子设备的状态检查。
 * <p>
 * 当子设备设置{@link DeviceConfigKey#selfManageState}为true时,
 * 在检查子设备状态时,将向网关发送{@link ChildDeviceMessage#getChildDeviceMessage()} 为{@link DeviceStateCheckMessage}的消息
 *
 * @author zhouhao
 * @since 1.1.6
 */
public class DeviceStateCheckMessage extends CommonDeviceMessage<DeviceStateCheckMessage> implements RepayableDeviceMessage<DeviceStateCheckMessageReply> {

    public static DeviceStateCheckMessage create(String deviceId) {
        DeviceStateCheckMessage message = new DeviceStateCheckMessage();
        message.setDeviceId(deviceId);
        message.setMessageId(IDGenerator.SNOW_FLAKE_STRING.generate());
        return message;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.STATE_CHECK;
    }

    @Override
    public DeviceStateCheckMessageReply newReply() {
        return new DeviceStateCheckMessageReply().from(this);
    }
}
