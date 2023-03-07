package top.iot.gateway.core.message.function;

import top.iot.gateway.core.enums.ErrorCode;
import org.junit.Assert;
import org.junit.Test;

public class FunctionInvokeMessageReplyTest {

    @Test
    public void test() {



        FunctionInvokeMessageReply reply = FunctionInvokeMessageReply.create();

        reply.error(ErrorCode.TIME_OUT).messageId("test");
        reply.addHeader("test","test");

        Assert.assertTrue(reply.getHeader("test").isPresent());

        Assert.assertFalse(reply.isSuccess());
        Assert.assertEquals(reply.getCode(), ErrorCode.TIME_OUT.name());
        Assert.assertEquals(reply.getMessage(), ErrorCode.TIME_OUT.getText());
        Assert.assertEquals(reply.getMessageId(), "test");

        reply.success().output(1);

        Assert.assertTrue(reply.isSuccess());
        Assert.assertEquals(reply.getOutput(),1);

    }
}