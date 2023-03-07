package top.iot.gateway.core.codec.defaults;

import top.iot.gateway.core.Payload;
import top.iot.gateway.core.codec.Codec;

import javax.annotation.Nonnull;

public class BytesCodec implements Codec<byte[]> {

    public static BytesCodec INSTANCE = new BytesCodec();

    private BytesCodec() {

    }

    @Override
    public Class<byte[]> forType() {
        return byte[].class;
    }

    @Override
    public byte[] decode(@Nonnull Payload payload) {
        return payload.getBytes(false);
    }

    @Override
    public Payload encode(byte[] body) {
        return Payload.of(body);
    }


}
