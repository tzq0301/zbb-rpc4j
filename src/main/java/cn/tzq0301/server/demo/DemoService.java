package cn.tzq0301.server.demo;

import cn.tzq0301.common.model.request.RpcRequest;
import cn.tzq0301.common.model.response.RpcResponse;

public class DemoService {
    public void demo(RpcRequest<?> req, RpcResponse<?> resp) {
        System.out.println("Demo!!!");
        System.out.flush();
    }
}
