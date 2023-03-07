package top.iot.gateway.core.message.codec;

import top.iot.gateway.core.message.Message;
import top.iot.gateway.core.trace.FluxTracer;
import top.iot.gateway.core.trace.ProtocolTracer;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

import javax.annotation.Nonnull;

@AllArgsConstructor
public class TraceDeviceMessageCodec implements DeviceMessageCodec {
    private final String protocolId;
    private final DeviceMessageCodec target;

    @Override
    public Transport getSupportTransport() {
        return target.getSupportTransport();
    }

    @Nonnull
    @Override
    public Flux<? extends Message> decode(@Nonnull MessageDecodeContext context) {
        return Flux
                .from(target.decode(context))
                .as(FluxTracer.create(ProtocolTracer.SpanName.decode(protocolId)));
    }

    @Nonnull
    @Override
    public Flux<? extends EncodedMessage> encode(@Nonnull MessageEncodeContext context) {
        return Flux
                .from(target.encode(context))
                .as(FluxTracer.create(ProtocolTracer.SpanName.encode(protocolId)));
    }
}
