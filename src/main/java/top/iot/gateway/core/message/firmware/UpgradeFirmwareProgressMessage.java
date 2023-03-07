package top.iot.gateway.core.message.firmware;

import top.iot.gateway.core.message.CommonDeviceMessage;
import top.iot.gateway.core.message.MessageType;
import top.iot.gateway.core.utils.SerializeUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * 上报固件更新进度
 *
 * @author zhouhao
 * @since 1.3
 */
@Getter
@Setter
public class UpgradeFirmwareProgressMessage extends CommonDeviceMessage<UpgradeFirmwareProgressMessage> {

    /**
     * 进度0-100
     */
    private int progress;

    /**
     * 是否已完成
     */
    private boolean complete;

    /**
     * 升级中的固件版本
     */
    private String version;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 错误原因
     */
    private String errorReason;

    /**
     * 固件ID
     *
     * @since 1.1.4
     */
    private String firmwareId;

    @Override
    public MessageType getMessageType() {
        return MessageType.UPGRADE_FIRMWARE_PROGRESS;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeInt(progress);
        out.writeBoolean(complete);
        out.writeBoolean(success);

        SerializeUtils.writeNullableUTF(version, out);
        SerializeUtils.writeNullableUTF(errorReason, out);
        SerializeUtils.writeNullableUTF(firmwareId, out);

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        this.progress = in.readInt();
        this.complete = in.readBoolean();
        this.success = in.readBoolean();
        this.version = SerializeUtils.readNullableUTF(in);
        this.errorReason = SerializeUtils.readNullableUTF(in);
        this.firmwareId = SerializeUtils.readNullableUTF(in);
    }
}
