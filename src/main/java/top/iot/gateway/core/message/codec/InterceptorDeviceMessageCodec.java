package top.iot.gateway.core.message.codec;

import top.iot.gateway.core.message.Message;
import top.iot.gateway.core.message.interceptor.DeviceMessageCodecInterceptor;
import top.iot.gateway.core.message.interceptor.DeviceMessageDecodeInterceptor;
import top.iot.gateway.core.message.interceptor.DeviceMessageEncodeInterceptor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author bsetfeng
 * @author zhouhao
 * @since 1.0
 **/
public class InterceptorDeviceMessageCodec implements DeviceMessageCodec {

    private final DeviceMessageCodec messageCodec;

    private final List<DeviceMessageDecodeInterceptor> decodeDeviceMessageInterceptors = new CopyOnWriteArrayList<>();

    private final List<DeviceMessageEncodeInterceptor> encodeDeviceMessageInterceptors = new CopyOnWriteArrayList<>();

    public InterceptorDeviceMessageCodec(DeviceMessageCodec codec) {
        this.messageCodec = codec;
    }

    @Override
    public Transport getSupportTransport() {
        return messageCodec.getSupportTransport();
    }

    public void register(DeviceMessageCodecInterceptor interceptor) {
        if (interceptor instanceof DeviceMessageDecodeInterceptor) {
            decodeDeviceMessageInterceptors.add(((DeviceMessageDecodeInterceptor) interceptor));
        }
        if (interceptor instanceof DeviceMessageEncodeInterceptor) {
            encodeDeviceMessageInterceptors.add(((DeviceMessageEncodeInterceptor) interceptor));
        }
    }

    @Nonnull
    @Override
    public Flux<? extends EncodedMessage> encode(@Nonnull MessageEncodeContext context) {
        return Flux.defer(() -> {
            Mono<Void> pre = Mono.empty();
            for (DeviceMessageEncodeInterceptor interceptor : encodeDeviceMessageInterceptors) {
                pre = pre.then(interceptor.preEncode(context));
            }
            Flux<? extends EncodedMessage> message = Flux.from(messageCodec.encode(context));

            for (DeviceMessageEncodeInterceptor interceptor : encodeDeviceMessageInterceptors) {
                message = message.flatMap(msg -> interceptor.postEncode(context, msg));
            }

            return pre.thenMany(message);
        });

    }

    @Nonnull
    @Override
    public Flux<? extends Message> decode(@Nonnull MessageDecodeContext context) {
        return Flux.defer(() -> {
            Mono<Void> pre = Mono.empty();
            for (DeviceMessageDecodeInterceptor interceptor : decodeDeviceMessageInterceptors) {
                pre = pre.then(interceptor.preDecode(context));
            }
            Flux<? extends Message> message = Flux.from(messageCodec.decode(context));

            for (DeviceMessageDecodeInterceptor interceptor : decodeDeviceMessageInterceptors) {
                message = message.flatMap(msg -> interceptor.postDecode(context, msg));
            }

            return pre.thenMany(message);
        });
    }
}
