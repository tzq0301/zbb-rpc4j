package org.example.server.model;

public class LogErrMiddleware implements RpcMiddleware {
    @Override
    public RpcService apply(RpcService service) {
        return (req, resp) -> {
            System.err.println(req);
            System.err.flush();
            service.accept(req, resp);
            System.err.println(resp);
            System.err.flush();
        };
    }
}
