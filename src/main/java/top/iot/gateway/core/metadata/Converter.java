package top.iot.gateway.core.metadata;

public interface Converter<T> {

    T convert(Object value);
}
