package top.iot.gateway.core.cache;

public interface FileQueueBuilderFactory {

    <T> FileQueue.Builder<T> create();

}
