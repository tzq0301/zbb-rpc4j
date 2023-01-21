package org.example.server.demo;

import org.example.common.model.request.RpcRequest;
import org.example.common.model.response.RpcResponse;

public class DemoService {
    public void demo(RpcRequest<?> req, RpcResponse<?> resp) {
        System.out.println("Demo!!!");
        System.out.flush();
    }
}
