package org.example.server.demo;

import org.example.common.model.RpcRequest;
import org.example.common.model.RpcResponse;

public class DemoService {
    public void demo(RpcRequest<?> req, RpcResponse<?> resp) {
        System.out.println("Demo!!!");
        System.out.flush();
    }
}
