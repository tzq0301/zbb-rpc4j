package cn.tzq0301.server.model.middleware.impl;

import cn.tzq0301.server.service.RpcService;
import lombok.extern.java.Log;
import cn.tzq0301.server.model.middleware.RpcMiddleware;

@Log
public class JdkLogMiddleware implements RpcMiddleware {
    @Override
    public RpcService apply(RpcService service) {
        return (req, resp) -> {
            log.info(String.format("Request:  %s", req.toString()));
            service.accept(req, resp);
            log.info(String.format("Response: %s", resp.toString()));
        };
    }
}
