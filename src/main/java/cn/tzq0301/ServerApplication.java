package cn.tzq0301;

import cn.tzq0301.server.RpcServer;
import cn.tzq0301.server.model.middleware.impl.JdkLogMiddleware;
import lombok.extern.java.Log;

import java.util.Map;

@Log
public class ServerApplication {
    public static void main(String[] args) throws Exception {
        RpcServer server = new RpcServer(8080);

        server.addMiddlewares(new JdkLogMiddleware());

        server.registerServices(Map.of( // TODO 反射扫描
                "Hello", (ctx, req, resp) -> {
                    log.info("Hello World!!!");
                    resp.setCode(0);
                }
        ));
        server.run();
    }
}
