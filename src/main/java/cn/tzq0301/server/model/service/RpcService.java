package cn.tzq0301.server.model.service;

import cn.tzq0301.common.request.RpcRequest;
import cn.tzq0301.common.response.RpcResponse;
import cn.tzq0301.server.model.context.RpcContext;

@FunctionalInterface
public interface RpcService {
    void call(RpcContext context, RpcRequest<?> req, RpcResponse<?> resp);
}
