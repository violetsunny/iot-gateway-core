package top.iot.gateway.core.device;

import top.iot.gateway.core.message.DeviceMessageReply;

public interface ReplyFailureHandler {

    void handle(Throwable err, DeviceMessageReply message);
}
