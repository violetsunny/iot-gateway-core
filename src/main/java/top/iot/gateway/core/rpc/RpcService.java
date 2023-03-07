package top.iot.gateway.core.rpc;

public interface RpcService<I> {

    String serverNodeId();

    String id();

    String name();

    I service();
}
