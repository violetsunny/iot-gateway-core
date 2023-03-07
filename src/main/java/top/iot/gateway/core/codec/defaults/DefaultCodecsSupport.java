package top.iot.gateway.core.codec.defaults;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import top.iot.gateway.core.codec.CodecsSupport;
import top.iot.gateway.core.event.Subscription;
import top.iot.gateway.core.event.TopicPayload;
import top.iot.gateway.core.message.DeviceMessage;
import top.iot.gateway.core.message.Message;
import top.iot.gateway.core.things.ThingProperty;
import top.iot.gateway.core.Payload;
import top.iot.gateway.core.codec.Codec;
import io.netty.buffer.ByteBuf;
import org.reactivestreams.Publisher;
import org.springframework.core.ResolvableType;

import java.util.*;

@SuppressWarnings("all")
public class DefaultCodecsSupport implements CodecsSupport {

    private static Map<Class, Codec> staticCodec = new HashMap<>();

    static {


        staticCodec.put(byte.class, ByteCodec.INSTANCE);
        staticCodec.put(Byte.class, ByteCodec.INSTANCE);

        staticCodec.put(int.class, IntegerCodec.INSTANCE);
        staticCodec.put(Integer.class, IntegerCodec.INSTANCE);

        staticCodec.put(long.class, LongCodec.INSTANCE);
        staticCodec.put(Long.class, LongCodec.INSTANCE);

        staticCodec.put(double.class, DoubleCodec.INSTANCE);
        staticCodec.put(Double.class, DoubleCodec.INSTANCE);

        staticCodec.put(float.class, FloatCodec.INSTANCE);
        staticCodec.put(Float.class, FloatCodec.INSTANCE);

        staticCodec.put(boolean.class, BooleanCodec.INSTANCE);
        staticCodec.put(Boolean.class, BooleanCodec.INSTANCE);

        staticCodec.put(String.class, StringCodec.UTF8);
        staticCodec.put(byte[].class, BytesCodec.INSTANCE);

        staticCodec.put(Void.class, VoidCodec.INSTANCE);
        staticCodec.put(void.class, VoidCodec.INSTANCE);

        staticCodec.put(DeviceMessage.class, DeviceMessageCodec.INSTANCE);
        staticCodec.put(Message.class, MessageCodec.INSTANCE);

        {
            JsonCodec<Map> codec = JsonCodec.of(Map.class);
            staticCodec.put(Map.class, codec);
            staticCodec.put(HashMap.class, codec);
            staticCodec.put(LinkedHashMap.class, codec);
        }

        staticCodec.put(TopicPayload.class, TopicPayloadCodec.INSTANCE);
        staticCodec.put(Subscription.class, SubscriptionCodec.INSTANCE);

        staticCodec.put(ByteBuf.class, ByteBufCodec.INSTANCE);

        staticCodec.put(JSONObject.class, FastJsonCodec.INSTANCE);

        staticCodec.put(JSONArray.class, FastJsonArrayCodec.INSTANCE);

        staticCodec.put(ThingProperty.class,ThingPropertyCodec.INSTANCE);

    }

    @Override
    public <T> Optional<Codec<T>> lookup(ResolvableType type) {
        ResolvableType ref = type;
        if (Publisher.class.isAssignableFrom(ref.toClass())) {
            ref = ref.getGeneric(0);
        }
        Class refType = ref.toClass();

        Codec<T> codec = staticCodec.get(refType);
        if (codec == null) {
            if (List.class.isAssignableFrom(refType)) {
                codec = (Codec<T>) JsonArrayCodec.of(ref.getGeneric(0).toClass());
            } else if (ref.toClass().isEnum()) {
                codec = (Codec<T>) EnumCodec.of((Enum[]) ref.toClass().getEnumConstants());
            } else if (Payload.class.isAssignableFrom(refType)) {
                codec = (Codec<T>) DirectCodec.INSTANCE;
            } else if (Set.class.isAssignableFrom(ref.toClass())) {
                codec = (Codec<T>) JsonArrayCodec.of(ref.getGeneric(0).toClass(), HashSet.class, HashSet::new);
            } else if (ByteBuf.class.isAssignableFrom(refType)) {
                codec = (Codec<T>) ByteBufCodec.INSTANCE;
            } else if (Message.class.isAssignableFrom(refType)) {
                codec = (Codec<T>) MessageCodec.INSTANCE;
            }
        }

        if (codec != null) {
            return Optional.of(codec);
        }
        if (refType.isInterface()) {
            return Optional.empty();
        }
        if (codec == null) {
            codec = JsonCodec.of(refType);
        }
        return Optional.of(codec);
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
