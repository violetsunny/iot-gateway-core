package top.iot.gateway.core.codec.defaults;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import top.iot.gateway.core.things.ThingProperty;
import top.iot.gateway.core.Payload;
import top.iot.gateway.core.codec.Codec;

import javax.annotation.Nonnull;

public class ThingPropertyCodec implements Codec<ThingProperty> {
    public static final ThingPropertyCodec INSTANCE = new ThingPropertyCodec();

    @Override
    public Class<ThingProperty> forType() {
        return ThingProperty.class;
    }

    @Override
    public ThingProperty decode(@Nonnull Payload payload) {
        JSONObject json = payload.bodyToJson(false);

        return ThingProperty.of(json.getString("property"),json.get("value"),json.getLongValue("timestamp"),json.getString("state"));
    }

    @Override
    public Payload encode(ThingProperty body) {
        return Payload.of(JSON.toJSONBytes(body));
    }
}
