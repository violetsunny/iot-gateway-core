package top.iot.gateway.core.message.event;

import top.iot.gateway.core.message.CommonThingMessage;
import top.iot.gateway.core.message.MessageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultEventMessage extends CommonThingMessage<DefaultEventMessage> implements ThingEventMessage {

    private String event;

    private Object data;

    public MessageType getMessageType() {
        return MessageType.EVENT;
    }

    @Override
    public DefaultEventMessage event(String event) {
        this.event = event;
        return this;
    }

    @Override
    public DefaultEventMessage data(Object data) {
        this.data = data;
        return this;
    }
}
