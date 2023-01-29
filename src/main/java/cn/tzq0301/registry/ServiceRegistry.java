package cn.tzq0301.registry;

import cn.tzq0301.server.model.service.RpcService;

public interface ServiceRegistry {
    void register(final String serviceName, final RpcService service);
    RpcService get(final String serviceName) throws NullPointerException;
}
