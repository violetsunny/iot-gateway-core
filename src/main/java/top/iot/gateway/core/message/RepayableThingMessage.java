package top.iot.gateway.core.message;

import top.iot.gateway.core.message.function.FunctionInvokeMessage;
import top.iot.gateway.core.message.function.FunctionInvokeMessageReply;
import top.iot.gateway.core.message.property.ReadPropertyMessage;
import top.iot.gateway.core.message.property.ReadPropertyMessageReply;
import top.iot.gateway.core.message.property.WritePropertyMessage;
import top.iot.gateway.core.message.property.WritePropertyMessageReply;

/**
 * 支持回复的消息
 *
 * @author zhouhao
 * @see ReadPropertyMessage
 * @see WritePropertyMessage
 * @see FunctionInvokeMessage
 * @since 1.0.0
 */
public interface RepayableThingMessage<R extends ThingMessageReply> extends ThingMessage {

    /**
     * 新建一个回复对象
     *
     * @return 回复对象
     * @see ReadPropertyMessageReply
     * @see WritePropertyMessageReply
     * @see FunctionInvokeMessageReply
     */
    R newReply();

}
