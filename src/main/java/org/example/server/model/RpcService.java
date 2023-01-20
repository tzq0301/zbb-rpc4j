package org.example.server.model;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface RpcService extends BiConsumer<RpcRequest<?>, RpcResponse<?>> {
}
