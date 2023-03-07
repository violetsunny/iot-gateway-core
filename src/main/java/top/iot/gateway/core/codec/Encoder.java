package top.iot.gateway.core.codec;

import top.iot.gateway.core.Payload;

public interface Encoder<T> {

    Payload encode(T body);

}
