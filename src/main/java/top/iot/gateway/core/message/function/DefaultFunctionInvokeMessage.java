package top.iot.gateway.core.message.function;

import com.alibaba.fastjson.JSONObject;
import top.iot.gateway.core.message.CommonThingMessage;
import top.iot.gateway.core.message.MessageType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class DefaultFunctionInvokeMessage extends CommonThingMessage<DefaultFunctionInvokeMessage>
        implements ThingFunctionInvokeMessage<DefaultFunctionInvokeMessageReply> {

    private String functionId;

    public DefaultFunctionInvokeMessage() {

    }

    private List<FunctionParameter> inputs = new ArrayList<>();

    @Override
    public MessageType getMessageType() {
        return ThingFunctionInvokeMessage.super.getMessageType();
    }

    public DefaultFunctionInvokeMessage addInput(FunctionParameter parameter) {
        inputs.add(parameter);
        return this;
    }
    @Override
    public DefaultFunctionInvokeMessage functionId(String id) {
        this.functionId=id;
        return this;
    }

    @Override
    public void fromJson(JSONObject jsonObject) {
        super.fromJson(jsonObject);
        this.functionId = jsonObject.getString("functionId");
    }

    @Override
    public DefaultFunctionInvokeMessageReply newReply() {
        DefaultFunctionInvokeMessageReply reply = new DefaultFunctionInvokeMessageReply().from(this);
        reply.setFunctionId(this.functionId);
        return reply;
    }
}
