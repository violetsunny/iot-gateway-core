package top.iot.gateway.core.things;

import top.iot.gateway.core.message.ThingMessage;
import reactor.core.publisher.Flux;

public interface ThingRpcSupportChain {

    Flux<? extends ThingMessage> call(ThingMessage message, ThingRpcSupport next);

}
