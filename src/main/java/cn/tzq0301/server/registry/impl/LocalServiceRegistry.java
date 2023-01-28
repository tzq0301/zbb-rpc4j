package cn.tzq0301.server.registry.impl;

import cn.tzq0301.server.model.service.RpcService;
import cn.tzq0301.server.registry.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LocalServiceRegistry implements ServiceRegistry {
    private final Map<String, RpcService> services;

    public LocalServiceRegistry() {
        this.services = new HashMap<>();
    }

    @Override
    public void register(final String serviceName, final RpcService service) {
        services.put(serviceName, service);
    }

    @Override
    public RpcService get(final String serviceName) throws NullPointerException {
        return Objects.requireNonNull(services.get(serviceName));
    }
}
