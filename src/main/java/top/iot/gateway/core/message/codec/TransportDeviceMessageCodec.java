package top.iot.gateway.core.message.codec;

import top.iot.gateway.core.message.DeviceMessage;
import reactor.core.publisher.Mono;

/**
 * @since 1.0
 **/
public interface TransportDeviceMessageCodec {

    Transport getSupportTransport();

    Mono<EncodedMessage> encode(MessageEncodeContext context);

    Mono<DeviceMessage> decode(MessageDecodeContext context);
}
