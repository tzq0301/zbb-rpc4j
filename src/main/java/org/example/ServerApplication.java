package org.example;

import org.example.server.Server;
import org.example.server.middleware.impl.LogMiddleware;

/**
 * @author tzq0301
 * @version 1.0
 */
public class ServerApplication {
    public static void main(String[] args) throws Exception {
        new Server(8080, new LogMiddleware()).run();
    }
}
