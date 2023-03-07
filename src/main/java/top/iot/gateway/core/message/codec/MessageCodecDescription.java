package top.iot.gateway.core.message.codec;

import top.iot.gateway.core.metadata.ConfigMetadata;

import javax.annotation.Nullable;

/**
 * @see MqttMessageCodecDescription
 */
public interface MessageCodecDescription {

    String getDescription();

    @Nullable
    ConfigMetadata getConfigMetadata();

}
