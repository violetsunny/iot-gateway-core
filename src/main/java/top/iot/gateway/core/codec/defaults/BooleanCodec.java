package top.iot.gateway.core.codec.defaults;

import top.iot.gateway.core.Payload;
import top.iot.gateway.core.codec.Codec;

import javax.annotation.Nonnull;

public class BooleanCodec implements Codec<Boolean> {

    public static BooleanCodec INSTANCE = new BooleanCodec();

    private BooleanCodec() {

    }

    @Override
    public Class<Boolean> forType() {
        return Boolean.class;
    }

    @Override
    public Boolean decode(@Nonnull Payload payload) {
        byte[] data = payload.getBytes(false);

        return data.length > 0 && data[0] > 0;
    }

    @Override
    public Payload encode(Boolean body) {
        return Payload.of(new byte[]{body ? (byte) 1 : 0});
    }

}
