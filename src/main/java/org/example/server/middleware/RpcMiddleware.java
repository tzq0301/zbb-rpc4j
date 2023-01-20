package org.example.server.middleware;

import org.example.server.service.RpcService;

@FunctionalInterface
public interface RpcMiddleware {
    RpcService apply(RpcService service);
}
