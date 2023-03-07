package top.iot.gateway.core.server.monitor;


public interface GatewayServerMonitor {

    String getCurrentServerId();

    GatewayServerMetrics metrics();
}
