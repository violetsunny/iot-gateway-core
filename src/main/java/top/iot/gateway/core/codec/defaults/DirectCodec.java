package top.iot.gateway.core.codec.defaults;

import top.iot.gateway.core.Payload;
import top.iot.gateway.core.codec.Codec;

import javax.annotation.Nonnull;


public class DirectCodec implements Codec<Payload> {

    public static final DirectCodec INSTANCE = new DirectCodec();

    public static <T extends Payload> Codec<T> instance() {
        return (Codec<T>)INSTANCE;
    }

    @Override
    public Class<Payload> forType() {
        return Payload.class;
    }

    @Override
    public Payload decode(@Nonnull Payload payload) {
        return payload;
    }

    @Override
    public Payload encode(Payload body) {
        return body;
    }
}
