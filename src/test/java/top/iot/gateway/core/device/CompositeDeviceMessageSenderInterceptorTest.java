package top.iot.gateway.core.device;

import top.iot.gateway.core.message.DeviceMessage;
import top.iot.gateway.core.message.interceptor.DeviceMessageSenderInterceptor;
import top.iot.gateway.core.message.property.WritePropertyMessageReply;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class CompositeDeviceMessageSenderInterceptorTest {


    @Test
    public void test() {
        DeviceMessageSenderInterceptor sender = DeviceMessageSenderInterceptor.DO_NOTING;

        WritePropertyMessageReply a = new WritePropertyMessageReply();
        WritePropertyMessageReply b = new WritePropertyMessageReply();
        WritePropertyMessageReply c = new WritePropertyMessageReply();

        sender = sender.andThen(new DeviceMessageSenderInterceptor() {
            @Override
            public <R extends DeviceMessage> Flux<R> afterSent(DeviceOperator device, DeviceMessage message, Flux<R> reply) {
                return Flux.just((R) a);
            }
        });

        sender = sender.andThen(new DeviceMessageSenderInterceptor() {
            @Override
            public <R extends DeviceMessage> Flux<R> afterSent(DeviceOperator device, DeviceMessage message, Flux<R> reply) {
                return Flux.just((R) c);
            }
        });

        sender.afterSent(null, null, Flux.just(b))
                .as(StepVerifier::create)
                .expectNext(c)
                .verifyComplete()
        ;


    }

}