package org.example.server;

import org.example.server.model.LogErrMiddleware;
import org.example.server.model.LogMiddleware;

/**
 * @author tzq0301
 * @version 1.0
 */
public class Application {
    public static void main(String[] args) throws Exception {
        new Server(8080, new LogMiddleware(), new LogErrMiddleware()).run();
    }
}
