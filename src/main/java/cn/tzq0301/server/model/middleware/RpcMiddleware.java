package cn.tzq0301.server.model.middleware;

import cn.tzq0301.server.model.context.RpcContext;
import cn.tzq0301.server.model.service.RpcService;

@FunctionalInterface
public interface RpcMiddleware {
    RpcService apply(RpcService service);
}
