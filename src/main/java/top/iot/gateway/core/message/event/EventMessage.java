package top.iot.gateway.core.message.event;

import top.iot.gateway.core.message.CommonDeviceMessage;
import top.iot.gateway.core.message.MessageType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class EventMessage extends CommonDeviceMessage<EventMessage> implements ThingEventMessage {

    private String event;

    private Object data;

    public MessageType getMessageType() {
        return MessageType.EVENT;
    }

    @Override
    public EventMessage event(String event) {
        this.event = event;
        return this;
    }

    @Override
    public EventMessage data(Object data) {
        this.data = data;
        return this;
    }
}
