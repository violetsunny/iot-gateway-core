package top.iot.gateway.core.codec.defaults;

import top.iot.gateway.core.Payload;
import top.iot.gateway.core.message.Message;
import top.iot.gateway.core.message.property.ReadPropertyMessage;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageCodecTest {


    @Test
    public void test() {
        MessageCodec codec = MessageCodec.INSTANCE;
        ReadPropertyMessage message = new ReadPropertyMessage();
        message.addHeader("test", "1234");

        Payload payload = codec.encode(message);

        Message msg = codec.decode(payload);

        assertNotNull(msg);
        assertTrue(msg instanceof ReadPropertyMessage);
        assertEquals("1234",msg.getHeaderOrElse("test",null));
        System.out.println(payload.bodyToJson());
    }
}