package top.iot.gateway.core.device;

import top.iot.gateway.core.message.codec.Transport;

import java.io.Serializable;

public interface AuthenticationRequest extends Serializable {
    Transport getTransport();
}