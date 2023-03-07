package top.iot.gateway.core.codec.defaults;

import top.iot.gateway.core.utils.BytesUtils;
import top.iot.gateway.core.Payload;
import top.iot.gateway.core.codec.Codec;

import javax.annotation.Nonnull;

public class DoubleCodec implements Codec<Double> {

    public static DoubleCodec INSTANCE = new DoubleCodec();

    private DoubleCodec() {

    }

    @Override
    public Class<Double> forType() {
        return Double.class;
    }

    @Override
    public Double decode(@Nonnull Payload payload) {
        return BytesUtils.beToDouble(payload.getBytes(false));
    }

    @Override
    public Payload encode(Double body) {
        return Payload.of(BytesUtils.doubleToBe(body));
    }


}
