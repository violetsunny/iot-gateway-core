package top.iot.gateway.core.codec.defaults;

import top.iot.gateway.core.utils.BytesUtils;
import top.iot.gateway.core.Payload;
import top.iot.gateway.core.codec.Codec;

import javax.annotation.Nonnull;

public class LongCodec implements Codec<Long> {

    public static LongCodec INSTANCE = new LongCodec();

    private LongCodec() {

    }

    @Override
    public Class<Long> forType() {
        return Long.class;
    }

    @Override
    public Long decode(@Nonnull Payload payload) {
        return BytesUtils.beToLong(payload.getBytes(false));
    }

    @Override
    public Payload encode(Long body) {
        return Payload.of(BytesUtils.longToBe(body));
    }


}
