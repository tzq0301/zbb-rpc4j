package org.example.server.model.middleware;

import org.example.server.service.RpcService;

@FunctionalInterface
public interface RpcMiddleware {
    RpcService apply(RpcService service);
}
