package cn.tzq0301;

import cn.tzq0301.server.registry.ServiceRegistry;
import cn.tzq0301.server.registry.impl.LocalServiceRegistry;
import lombok.extern.java.Log;
import cn.tzq0301.server.RpcServer;
import cn.tzq0301.server.demo.DemoService;
import cn.tzq0301.server.model.middleware.impl.JdkLogMiddleware;

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
