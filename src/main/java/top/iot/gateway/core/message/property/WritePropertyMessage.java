package top.iot.gateway.core.message.property;

import top.iot.gateway.core.message.CommonDeviceMessage;
import top.iot.gateway.core.message.MessageType;
import top.iot.gateway.core.message.RepayableDeviceMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 修改属性指令,方向: 平台->设备
 * <p>
 * 用于修改设备属性,下发指令后,设备需要回复{@link WritePropertyMessageReply}
 *
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class WritePropertyMessage extends CommonDeviceMessage<WritePropertyMessage>
        implements RepayableDeviceMessage<WritePropertyMessageReply>,
        WriteThingPropertyMessage<WritePropertyMessageReply> {

    /**
     * 要修改的属性，key 为物模型中对应的属性ID,value为属性值
     */
    private Map<String, Object> properties = new LinkedHashMap<>();

    public void addProperty(String key, Object value) {
        properties.put(key, value);
    }

    @Override
    public WritePropertyMessageReply newReply() {
        return new WritePropertyMessageReply().from(this);
    }

    public MessageType getMessageType() {
        return MessageType.WRITE_PROPERTY;
    }


}
