package top.iot.gateway.core.codec.defaults;

import top.iot.gateway.core.Payload;
import top.iot.gateway.core.codec.Codec;

import javax.annotation.Nonnull;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringCodec implements Codec<String> {

    public static StringCodec DEFAULT = of(Charset.defaultCharset());

    public static StringCodec UTF8 = of(StandardCharsets.UTF_8);

    public static StringCodec ASCII = of(StandardCharsets.US_ASCII);

    public static StringCodec of(Charset charset) {
        return new StringCodec(charset);
    }

    private final Charset charset;

    private StringCodec(Charset charset) {
        this.charset = charset;
    }

    @Override
    public Class<String> forType() {
        return String.class;
    }

    @Override
    public String decode(@Nonnull Payload payload) {
        return payload.getBody().toString(charset);
    }

    @Override
    public Payload encode(String body) {
        return Payload.of(body.getBytes(charset));
    }


}
