package org.example.server.service;

import org.example.common.model.RpcRequest;
import org.example.common.model.RpcResponse;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface RpcService extends BiConsumer<RpcRequest<?>, RpcResponse<?>> {
}
