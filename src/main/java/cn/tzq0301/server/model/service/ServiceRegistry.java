package cn.tzq0301.server.model.service;

import cn.tzq0301.server.service.RpcService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ServiceRegistry {
    private final Map<String, RpcService> services;

    public static void register(final String serviceName, final RpcService service) {
        getInstance().services.put(serviceName, service);
    }

    public static RpcService get(final String serviceName) throws NullPointerException {
        return Objects.requireNonNull(getInstance().services.get(serviceName));
    }

    private ServiceRegistry() {
        this.services = new HashMap<>();
    }

    private static class Instance {
        private static final ServiceRegistry INSTANCE = new ServiceRegistry();
    }

    private static ServiceRegistry getInstance() {
        return Instance.INSTANCE;
    }
}
