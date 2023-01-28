package cn.tzq0301.server.model.service;

import cn.tzq0301.common.model.request.RpcRequest;
import cn.tzq0301.common.model.response.RpcResponse;
import cn.tzq0301.server.model.context.RpcContext;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface RpcService {
    void call(RpcContext context, RpcRequest<?> req, RpcResponse<?> resp);
}
