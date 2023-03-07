package top.iot.gateway.core.codec.defaults;

import top.iot.gateway.core.utils.BytesUtils;
import top.iot.gateway.core.Payload;
import top.iot.gateway.core.codec.Codec;

import javax.annotation.Nonnull;

public class FloatCodec implements Codec<Float> {

    public static FloatCodec INSTANCE = new FloatCodec();

    private FloatCodec() {

    }

    @Override
    public Class<Float> forType() {
        return Float.class;
    }

    @Override
    public Float decode(@Nonnull Payload payload) {
        return BytesUtils.beToFloat(payload.getBytes(false));
    }

    @Override
    public Payload encode(Float body) {
        return Payload.of(BytesUtils.floatToBe(body));
    }


}
