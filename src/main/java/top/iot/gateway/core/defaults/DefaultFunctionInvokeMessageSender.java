package top.iot.gateway.core.defaults;

import top.iot.gateway.core.enums.ErrorCode;
import top.iot.gateway.core.exception.DeviceOperationException;
import top.iot.gateway.core.exception.MetadataUndefinedException;
import top.iot.gateway.core.message.exception.FunctionIllegalParameterException;
import top.iot.gateway.core.metadata.PropertyMetadata;
import top.iot.gateway.core.metadata.ValidateResult;
import top.iot.gateway.core.device.DeviceOperator;
import top.iot.gateway.core.message.FunctionInvokeMessageSender;
import top.iot.gateway.core.message.Headers;
import top.iot.gateway.core.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import top.iot.gateway.core.message.exception.FunctionUndefinedException;
import top.iot.gateway.core.message.exception.IllegalParameterException;
import top.iot.gateway.core.message.function.FunctionInvokeMessage;
import top.iot.gateway.core.message.function.FunctionInvokeMessageReply;
import top.iot.gateway.core.message.function.FunctionParameter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
public class DefaultFunctionInvokeMessageSender implements FunctionInvokeMessageSender {

    private final FunctionInvokeMessage message = new FunctionInvokeMessage();

    private final DeviceOperator operator;

    public DefaultFunctionInvokeMessageSender(DeviceOperator operator, String functionId) {
        this.operator = operator;
        message.setMessageId(IdUtils.newUUID());
        message.setFunctionId(functionId);
        message.setDeviceId(operator.getDeviceId());
    }

    @Override
    public FunctionInvokeMessageSender custom(Consumer<FunctionInvokeMessage> messageConsumer) {
        messageConsumer.accept(message);
        return this;
    }

    @Override
    public FunctionInvokeMessageSender addParameter(FunctionParameter parameter) {
        message.addInput(parameter);
        return this;
    }

    @Override
    public FunctionInvokeMessageSender setParameter(List<FunctionParameter> parameter) {
        message.setInputs(new ArrayList<>(parameter));
        return this;
    }

    /**
     * 添加消息ID
     *
     * @param messageId 消息ID
     * @return 调用设备功能的消息发送器
     */
    @Override
    public FunctionInvokeMessageSender messageId(String messageId) {
        message.setMessageId(messageId);
        return this;
    }

    /**
     * 添加消息头
     *
     * @param header 消息头标识
     * @param value 消息头存储的值
     * @return 调用设备功能的消息发送器
     */
    @Override
    public FunctionInvokeMessageSender header(String header, Object value) {
        message.addHeader(header, value);
        return this;
    }

    /**
     * 执行参数校验
     * <pre>
     *     function("door-open")
     *     .validate()
     *     .flatMany(FunctionInvokeMessageSender::send)
     *     .doOnError(IllegalParameterException.class,err->log.error(err.getMessage(),err))
     *     ...
     * </pre>
     *
     * @see Mono#doOnError(Consumer)
     * @see IllegalParameterException
     * @see FunctionUndefinedException
     * @see FunctionIllegalParameterException
     */
    @Override
    public Mono<FunctionInvokeMessageSender> validate() {
        String function = message.getFunctionId();

        return operator
                .getMetadata()
                .switchIfEmpty(Mono.error(() -> new MetadataUndefinedException(operator.getDeviceId())))
                .flatMap(metadata -> Mono
                        .justOrEmpty(metadata.getFunction(function))
                        .switchIfEmpty(Mono.error(() -> new FunctionUndefinedException(function)))
                )
                .doOnNext(functionMetadata -> {
                    List<PropertyMetadata> metadataInputs = functionMetadata.getInputs();
                    List<FunctionParameter> inputs = message.getInputs();

                    Map<String, FunctionParameter> properties = inputs
                            .stream()
                            .collect(Collectors.toMap(FunctionParameter::getName, Function.identity(), (t1, t2) -> t1));
                    for (PropertyMetadata metadata : metadataInputs) {
                        FunctionParameter parameter = properties.get(metadata.getId());
                        Object value = Optional
                                .ofNullable(parameter)
                                .map(FunctionParameter::getValue)
                                .orElse(null);
                        if (value == null) {
                            continue;
                        }

                        ValidateResult validateResult = metadata.getValueType().validate(value);

                        validateResult.ifFail(result -> {
                            throw new IllegalParameterException(metadata.getId(), result.getErrorMsg());
                        });
                        if (validateResult.getValue() != null) {
                            parameter.setValue(validateResult.getValue());
                        }
                    }
                })
                .thenReturn(this)
                ;
    }

    /**
     * 发送消息
     *
     * @return 返回结果
     * @see DeviceOperationException
     * @see ErrorCode#CLIENT_OFFLINE
     */
    @Override
    public Flux<FunctionInvokeMessageReply> send() {
        if (message.getHeader(Headers.async).isPresent()) {
            return doSend();
        }
        return operator
                .getMetadata()
                .flatMap(meta -> Mono.justOrEmpty(meta.getFunction(message.getFunctionId())))
                .doOnNext(func -> async(func.isAsync()))
                .thenMany(doSend());
    }

    /**
     * 发送消息
     *
     * @return 回复内容
     */
    private Flux<FunctionInvokeMessageReply> doSend() {
        return operator
                .messageSender()
                .send(Mono.just(message));
    }
}
