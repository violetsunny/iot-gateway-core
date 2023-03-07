package top.iot.gateway.core.codec.defaults;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import top.iot.gateway.core.message.Message;
import top.iot.gateway.core.message.MessageType;
import top.iot.gateway.core.Payload;
import top.iot.gateway.core.codec.Codec;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeviceMessageCodec implements Codec<Message> {
    public static DeviceMessageCodec INSTANCE = new DeviceMessageCodec();

    @Override
    public Class<Message> forType() {
        return Message.class;
    }

    @Nullable
    @Override
    public Message decode(@Nonnull Payload payload) {
        JSONObject json = JSON.parseObject(payload.bodyToString(false));
        return MessageType
                .convertMessage(json)
                .orElseThrow(() -> new UnsupportedOperationException("unsupported message : " + json));
    }

    @Override
    public Payload encode(Message body) {
        return Payload.of(JSON.toJSONBytes(body.toJson()));
    }
}
