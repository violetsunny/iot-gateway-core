package top.iot.gateway.core.codec.defaults;

import top.iot.gateway.core.Payload;
import top.iot.gateway.core.codec.Codec;
import lombok.AllArgsConstructor;

import javax.annotation.Nonnull;
import java.util.Arrays;

@AllArgsConstructor(staticName = "of")
public class EnumCodec<T extends Enum<?>> implements Codec<T> {

    private final T[] values;

    @Override
    @SuppressWarnings("all")
    public Class<T> forType() {
        return (Class<T>) values[0].getDeclaringClass();
    }

    @Override
    public T decode(@Nonnull Payload payload) {
        byte[] bytes = payload.getBytes(false);

        if (bytes.length > 0 && bytes[0] <= values.length) {
            return values[bytes[0] & 0xFF];
        }
        throw new IllegalArgumentException("can not decode payload " + Arrays.toString(bytes) + " to enums " + Arrays.toString(values));
    }

    @Override
    public Payload encode(T body) {
        return  Payload.of(new byte[]{(byte) body.ordinal()});
    }


}
