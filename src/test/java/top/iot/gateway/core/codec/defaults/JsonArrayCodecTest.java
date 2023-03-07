package top.iot.gateway.core.codec.defaults;

import top.iot.gateway.core.Payload;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class JsonArrayCodecTest {

    @Test
    public void test() {
        JsonArrayCodec codec = JsonArrayCodec.of(Object.class);

        Object val = codec.decode(Payload.of("[1,2,3]"));

        Assert.assertEquals(val, Arrays.asList(1,2,3));
    }

}