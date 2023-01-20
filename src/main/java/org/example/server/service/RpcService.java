package org.example.server.service;

import org.example.server.model.RpcRequest;
import org.example.server.model.RpcResponse;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface RpcService extends BiConsumer<RpcRequest<?>, RpcResponse<?>> {
}
