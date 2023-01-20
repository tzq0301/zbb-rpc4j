package org.example.server.model;

public class LogMiddleware implements RpcMiddleware {
    @Override
    public RpcService apply(RpcService service) {
        return (req, resp) -> {
            System.out.println("Log: " + req);
            System.out.flush();

            service.accept(req, resp);

            System.out.println("Log: " + resp);
            System.out.flush();
        };
    }
}
