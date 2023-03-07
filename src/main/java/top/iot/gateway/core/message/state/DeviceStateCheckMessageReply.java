package top.iot.gateway.core.message.state;

import top.iot.gateway.core.device.DeviceState;
import top.iot.gateway.core.message.CommonDeviceMessageReply;
import top.iot.gateway.core.message.MessageType;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * 设备状态检查回复,如果设备存在则回复{@link DeviceStateCheckMessageReply#success(byte)}
 *
 * @author zhouhao
 * @since 1.1.6
 */
@Getter
@Setter
public class DeviceStateCheckMessageReply extends CommonDeviceMessageReply<DeviceStateCheckMessageReply> {

    /**
     * @see DeviceState
     */
    private byte state;

    public DeviceStateCheckMessageReply success(byte state) {
        this.state = state;
        return this;
    }

    public DeviceStateCheckMessageReply setOnline() {
        this.state = DeviceState.online;
        return this;
    }

    public DeviceStateCheckMessageReply setOffline() {
        this.state = DeviceState.offline;
        return this;
    }

    public DeviceStateCheckMessageReply setNoActive() {
        this.state = DeviceState.noActive;
        return this;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.STATE_CHECK_REPLY;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeByte(state);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        state = in.readByte();
    }
}
