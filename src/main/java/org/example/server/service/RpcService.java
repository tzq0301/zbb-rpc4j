package org.example.server.service;

import org.example.common.model.request.RpcRequest;
import org.example.common.model.response.RpcResponse;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface RpcService extends BiConsumer<RpcRequest<?>, RpcResponse<?>> {
}
