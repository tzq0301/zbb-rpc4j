package org.example;

import lombok.extern.java.Log;
import org.example.server.RpcServer;
import org.example.server.demo.DemoService;
import org.example.server.model.middleware.impl.JdkLogMiddleware;

import java.util.Map;

@Log
public class ServerApplication {
    public static void main(String[] args) throws Exception {
        RpcServer server = new RpcServer(8080);
        server.addMiddlewares(new JdkLogMiddleware());
        server.registerServices(Map.of( // TODO 反射扫描
                "Hello", (req, resp) -> {
                    log.info("Hello World!!!");
                    resp.setCode(0);
                },
                "Demo", new DemoService()::demo
        ));
        server.run();
    }
}
