package org.example.server.model.middleware.impl;

import lombok.extern.java.Log;
import org.example.server.model.middleware.RpcMiddleware;
import org.example.server.service.RpcService;

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
