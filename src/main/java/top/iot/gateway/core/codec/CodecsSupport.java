package top.iot.gateway.core.codec;

import org.springframework.core.ResolvableType;

import java.util.Optional;

public interface CodecsSupport {

    <T> Optional<Codec<T>> lookup(ResolvableType type);

    int getOrder();
}
