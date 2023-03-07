package top.iot.gateway.core.trace;

import top.iot.gateway.core.trace.data.SpanDataInfo;
import top.iot.gateway.core.utils.StringBuilderUtils;
import io.opentelemetry.sdk.common.CompletableResultCode;
import io.opentelemetry.sdk.trace.data.SpanData;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import lombok.AllArgsConstructor;
import top.iot.gateway.core.codec.Codec;
import top.iot.gateway.core.codec.Codecs;
import top.iot.gateway.core.event.EventBus;
import reactor.core.publisher.Mono;

import java.util.Collection;

@AllArgsConstructor(staticName = "create")
public class EventBusSpanExporter implements SpanExporter {
    private final EventBus eventBus;

    private final static Codec<SpanDataInfo> codec = Codecs.lookup(SpanDataInfo.class);

    @Override
    public CompletableResultCode export(Collection<SpanData> spans) {

        for (SpanData span : spans) {
            doPublish(span).subscribe();
        }
        return CompletableResultCode.ofSuccess();
    }

    //  /trace/{app}/{span}
    Mono<Void> doPublish(SpanData data) {
        String topic = StringBuilderUtils
                .buildString(data, (_data, builder) -> {
                    builder.append("/trace/")
                           .append(_data.getInstrumentationLibraryInfo().getName());
                    if (!_data.getName().startsWith("/")) {
                        builder.append("/");
                    }
                    builder.append(_data.getName());
                });
        return eventBus
                .publish(topic, codec, SpanDataInfo.of(data))
                .then();
    }


    @Override
    public CompletableResultCode flush() {
        return CompletableResultCode.ofSuccess();
    }

    @Override
    public CompletableResultCode shutdown() {
        return CompletableResultCode.ofSuccess();
    }
}
