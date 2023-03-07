package top.iot.gateway.core.codec.defaults;


import top.iot.gateway.core.Payload;
import top.iot.gateway.core.codec.Codec;

import javax.annotation.Nonnull;

public class VoidCodec implements Codec<Void> {

    public static VoidCodec INSTANCE = new VoidCodec();

    @Override
    public Class<Void> forType() {
        return Void.class;
    }

    @Override
    public Void decode(@Nonnull Payload payload) {
        return null;
    }

    @Override
    public Payload encode(Void body) {

        return Payload.of(new byte[0]);
    }

    @Override
    public boolean isDecodeFrom(Object nativeObject) {
        return nativeObject == null;
    }
}
