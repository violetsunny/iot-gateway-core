package top.iot.gateway.core.cluster.load;

import reactor.core.publisher.Mono;

public interface LoadSupplier {

    String loadId();

    Mono<Long> currentLoad();

    void init(LoadBalancer balancer);
}
