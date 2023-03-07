package top.iot.gateway.core.codec.defaults;

import top.iot.gateway.core.Payload;
import top.iot.gateway.core.codec.Codec;
import io.netty.buffer.ByteBuf;

import javax.annotation.Nonnull;

public class ByteCodec implements Codec<Byte> {

    public static ByteCodec INSTANCE = new ByteCodec();

    private ByteCodec() {

    }

    @Override
    public Class<Byte> forType() {
        return Byte.class;
    }

    @Override
    public Byte decode(@Nonnull Payload payload) {
        ByteBuf buf = payload.getBody();
        byte val = buf.getByte(0);
        buf.resetReaderIndex();
        return val;
    }

    @Override
    public Payload encode(Byte body) {
        return Payload.of(new byte[]{body});
    }


}
