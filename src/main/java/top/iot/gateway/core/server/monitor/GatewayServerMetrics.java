package top.iot.gateway.core.server.monitor;

import top.iot.gateway.core.server.session.DeviceSession;

public interface GatewayServerMetrics {

    void reportSession(String transport, int sessionTotal);

    void newConnection(String transport);

    void acceptedConnection(String transport);

    void rejectedConnection(String transport);

    void receiveFromDeviceMessage(DeviceSession session);

}
