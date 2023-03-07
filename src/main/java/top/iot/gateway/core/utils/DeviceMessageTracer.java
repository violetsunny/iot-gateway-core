package top.iot.gateway.core.utils;

import top.iot.gateway.core.message.Headers;
import top.iot.gateway.core.message.Message;

@Deprecated
public class DeviceMessageTracer {

    public static void trace(Message message, String name) {
        trace(message, name, System.currentTimeMillis());
    }

    public static void trace(Message message, String name, Object value) {
        if (message.getHeaderOrDefault(Headers.enableTrace)) {
            message.addHeader("_trace:" + name, value);
        }
    }

}
