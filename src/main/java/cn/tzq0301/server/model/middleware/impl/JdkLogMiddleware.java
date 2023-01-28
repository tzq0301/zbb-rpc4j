package cn.tzq0301.server.model.middleware.impl;

import cn.tzq0301.server.model.middleware.RpcMiddleware;
import cn.tzq0301.server.model.service.RpcService;
import lombok.extern.java.Log;

@Log
public class JdkLogMiddleware implements RpcMiddleware {
    @Override
    public RpcService apply(RpcService service) {
        return (ctx, req, resp) -> {
            log.info(String.format("Request:  %s", req.toString()));
            service.call(ctx, req, resp);
            log.info(String.format("Response: %s", resp.toString()));
        };
    }
}
