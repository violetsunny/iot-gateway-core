package top.iot.gateway.core.rpc;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ServiceEvent{

    private final String serviceId;

    private final String serviceName;

    private final String serverNodeId;

    private final Type type;


    public enum Type{
        added,
        removed
    }
}
