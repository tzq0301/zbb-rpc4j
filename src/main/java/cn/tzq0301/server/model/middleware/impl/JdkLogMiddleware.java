package cn.tzq0301.server.model.middleware.impl;

import cn.tzq0301.server.model.context.RpcContext;
import cn.tzq0301.server.model.middleware.RpcMiddleware;
import cn.tzq0301.server.model.service.RpcService;
import lombok.extern.java.Log;

@Log
public class JdkLogMiddleware implements RpcMiddleware {
    @Override
    public RpcService apply(RpcContext ctx, RpcService service) {
        return (req, resp) -> {
            log.info(String.format("Request:  %s", req.toString()));
            service.accept(req, resp);
            log.info(String.format("Response: %s", resp.toString()));
        };
    }
}
