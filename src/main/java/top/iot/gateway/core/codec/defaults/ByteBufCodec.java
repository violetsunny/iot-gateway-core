package top.iot.gateway.core.codec.defaults;

import top.iot.gateway.core.Payload;
import top.iot.gateway.core.codec.Codec;
import io.netty.buffer.ByteBuf;

import javax.annotation.Nonnull;

public class ByteBufCodec implements Codec<ByteBuf> {

    public static final ByteBufCodec INSTANCE = new ByteBufCodec();

    @Override
    public Class<ByteBuf> forType() {
        return ByteBuf.class;
    }

    @Override
    public ByteBuf decode(@Nonnull Payload payload) {
        return payload.getBody();
    }

    @Override
    public Payload encode(ByteBuf body) {
        return Payload.of(body);
    }
}
