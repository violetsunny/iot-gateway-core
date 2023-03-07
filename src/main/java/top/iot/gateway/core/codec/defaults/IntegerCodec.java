package top.iot.gateway.core.codec.defaults;

import top.iot.gateway.core.utils.BytesUtils;
import top.iot.gateway.core.Payload;
import top.iot.gateway.core.codec.Codec;

import javax.annotation.Nonnull;

public class IntegerCodec implements Codec<Integer> {

    public static IntegerCodec INSTANCE = new IntegerCodec();

    private IntegerCodec() {

    }

    @Override
    public Class<Integer> forType() {
        return Integer.class;
    }

    @Override
    public Integer decode(@Nonnull Payload payload) {
        return BytesUtils.beToInt(payload.getBytes(false));
    }

    @Override
    public Payload encode(Integer body) {
        return Payload.of(BytesUtils.intToBe(body));
    }


}
