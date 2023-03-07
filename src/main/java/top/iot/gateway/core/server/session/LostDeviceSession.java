package top.iot.gateway.core.server.session;

import top.iot.gateway.core.exception.DeviceOperationException;
import top.iot.gateway.core.message.codec.EncodedMessage;
import top.iot.gateway.core.message.codec.Transport;
import top.iot.gateway.core.device.DeviceOperator;
import top.iot.gateway.core.enums.ErrorCode;
import top.iot.gateway.core.utils.Reactors;
import io.netty.util.ReferenceCountUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class LostDeviceSession implements DeviceSession {
    @Getter
    private final String id;

    @Getter
    private final DeviceOperator operator;

    @Getter
    private final Transport transport;

    @Override
    public String getDeviceId() {
        return operator.getDeviceId();
    }

    @Override
    public long lastPingTime() {
        return -1;
    }

    @Override
    public long connectTime() {
        return -1;
    }

    @Override
    public Mono<Boolean> send(EncodedMessage encodedMessage) {
        return Mono
                .<Boolean>error(new DeviceOperationException(ErrorCode.CONNECTION_LOST))
                .doAfterTerminate(()-> ReferenceCountUtil.safeRelease(encodedMessage.getPayload()));
    }

    @Override
    public void close() {

    }

    @Override
    public void ping() {

    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void onClose(Runnable call) {

    }

    @Override
    public boolean isChanged(DeviceSession another) {
        return !another.isWrapFrom(LostDeviceSession.class);
    }

    @Override
    public Mono<Boolean> isAliveAsync() {
        return Reactors.ALWAYS_FALSE;
    }
}
