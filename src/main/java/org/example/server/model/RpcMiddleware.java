package org.example.server.model;

@FunctionalInterface
public interface RpcMiddleware {
    RpcService apply(RpcService service);
}
