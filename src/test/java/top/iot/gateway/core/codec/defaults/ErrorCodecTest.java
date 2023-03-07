package top.iot.gateway.core.codec.defaults;

import top.iot.gateway.core.Payload;
import org.junit.Test;

public class ErrorCodecTest {

    //@Test
    public void test() {

        Payload payload = ErrorCodec.DEFAULT.encode(new RuntimeException("test"));

        Throwable exception = ErrorCodec.DEFAULT.decode(payload);

        exception.printStackTrace();

    }
}