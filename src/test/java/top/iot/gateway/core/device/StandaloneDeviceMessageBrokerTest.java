package top.iot.gateway.core.device;

import top.iot.gateway.core.message.DeviceMessageReply;
import top.iot.gateway.core.message.Headers;
import top.iot.gateway.core.message.function.FunctionInvokeMessage;
import top.iot.gateway.core.message.function.FunctionInvokeMessageReply;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class StandaloneDeviceMessageBrokerTest {


    @Test
    public void testSimpleSend() {
        StandaloneDeviceMessageBroker handler = new StandaloneDeviceMessageBroker();

        handler.handleSendToDeviceMessage("test")
                .subscribe(msg -> {
                    handler.reply(new FunctionInvokeMessageReply().from(msg).success())
                            .subscribe();
                });

        FunctionInvokeMessage message = new FunctionInvokeMessage();
        message.setFunctionId("test");
        message.setMessageId("test");

        Flux<Boolean> successReply = handler.handleReply("test",message.getMessageId(), Duration.ofSeconds(10))
                .map(DeviceMessageReply::isSuccess);

        handler.send("test", Mono.just(message))
                .as(StepVerifier::create)
                .expectNext(1)
                .verifyComplete();

        successReply.as(StepVerifier::create)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    public void testNoHandler() {
        StandaloneDeviceMessageBroker handler = new StandaloneDeviceMessageBroker();
        FunctionInvokeMessage message = new FunctionInvokeMessage();
        message.setFunctionId("test");
        message.setMessageId("test");

        handler.send("test", Mono.just(message))
                .as(StepVerifier::create)
                .expectNext(0)
                .verifyComplete();


    }

    //@Test
    public void testParting() {
        StandaloneDeviceMessageBroker handler = new StandaloneDeviceMessageBroker();
        handler.handleSendToDeviceMessage("test")
                .subscribe(msg -> {
                    handler.reply(new FunctionInvokeMessageReply()
                            .from(msg)
                            .addHeader(Headers.fragmentBodyMessageId, msg.getMessageId())
                            .addHeader(Headers.fragmentNumber, 2)
                            .messageId("2")
                            .success())
                            .delayElement(Duration.ofSeconds(1))
                            .flatMap(success ->
                                    handler.reply(new FunctionInvokeMessageReply()
                                            .from(msg)
                                            .messageId("1")
                                            .addHeader(Headers.fragmentBodyMessageId, msg.getMessageId())
                                            .addHeader(Headers.fragmentNumber, 2)
                                            .success()))
                            .subscribe();
                });

        FunctionInvokeMessage message = new FunctionInvokeMessage();
        message.setFunctionId("test");
        message.setMessageId("test");

        Flux<Boolean> successReply = handler
                .handleReply("test",message.getMessageId(), Duration.ofSeconds(2))
                .map(DeviceMessageReply::isSuccess);

        handler.send("test", Mono.just(message))
                .as(StepVerifier::create)
                .expectNext(1)
                .verifyComplete();

        successReply.
                as(StepVerifier::create)
                .expectNext(true, true)
                .verifyComplete();
    }


}