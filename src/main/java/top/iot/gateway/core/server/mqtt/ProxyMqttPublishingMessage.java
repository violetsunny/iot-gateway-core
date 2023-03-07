package top.iot.gateway.core.server.mqtt;

import top.iot.gateway.core.message.codec.MqttMessage;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;

import javax.annotation.Nonnull;

@AllArgsConstructor(staticName = "of")
class ProxyMqttPublishingMessage implements MqttPublishingMessage {

    private final MqttMessage target;

    private final Runnable ack;

    @Nonnull
    @Override
    public ByteBuf getPayload() {
        return target.getPayload();
    }

    @Nonnull
    @Override
    public String getTopic() {
        return target.getTopic();
    }

    @Override
    public String getClientId() {
        return target.getClientId();
    }

    @Override
    public int getMessageId() {
        return target.getMessageId();
    }

    @Override
    public void acknowledge() {
        ack.run();
        if (target instanceof MqttPublishingMessage) {
            ((MqttPublishingMessage) target).acknowledge();
        }
    }

    @Override
    public int getQosLevel() {
        return target.getQosLevel();
    }

    @Override
    public boolean isDup() {
        return target.isDup();
    }

    @Override
    public boolean isRetain() {
        return target.isRetain();
    }

    @Override
    public boolean isWill() {
        return target.isWill();
    }
}
