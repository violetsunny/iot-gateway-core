package top.iot.gateway.core.message;

import com.alibaba.fastjson.JSONObject;
import top.iot.gateway.core.utils.SerializeUtils;
import lombok.Getter;
import lombok.Setter;
import top.iot.gateway.core.enums.ErrorCode;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.function.BiConsumer;

/**
 * 子设备消息
 *
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class ChildDeviceMessageReply extends CommonDeviceMessageReply<ChildDeviceMessageReply> {
    private String childDeviceId;

    private Message childDeviceMessage;

    public MessageType getMessageType() {
        return MessageType.CHILD_REPLY;
    }

    @Override
    public ChildDeviceMessageReply error(Throwable e) {
        doWithChildReply(e, DeviceMessageReply::error);
        return super.error(e);
    }

    @Override
    public ChildDeviceMessageReply error(ErrorCode errorCode) {
        doWithChildReply(errorCode, DeviceMessageReply::error);
        return super.error(errorCode);
    }

    @Override
    public ChildDeviceMessageReply message(String message) {
        doWithChildReply(message, DeviceMessageReply::message);
        return super.message(message);
    }

    @Override
    public ChildDeviceMessageReply code(String code) {
        doWithChildReply(code, DeviceMessageReply::code);
        return super.code(code);
    }

    public <T> void doWithChildReply(T arg, BiConsumer<DeviceMessageReply, T> childReplyConsumer) {
        if (childDeviceMessage instanceof DeviceMessageReply) {
            DeviceMessageReply child = ((DeviceMessageReply) childDeviceMessage);
            childReplyConsumer.accept(child, arg);
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        if (null != childDeviceMessage) {
            json.put("childDeviceMessage", childDeviceMessage.toJson());
        }
        return json;
    }


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        SerializeUtils.writeNullableUTF(childDeviceId, out);
        out.writeObject(childDeviceMessage);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        this.childDeviceId = SerializeUtils.readNullableUTF(in);
        this.childDeviceMessage = (Message) in.readObject();
    }
}
