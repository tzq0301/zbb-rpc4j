package org.example;

import org.example.server.RpcServer;
import org.example.server.model.middleware.impl.JdkLogMiddleware;

/**
 * @author tzq0301
 * @version 1.0
 */
public class ServerApplication {
    public static void main(String[] args) throws Exception {
        new RpcServer(8080, new JdkLogMiddleware()).run();
    }
}
