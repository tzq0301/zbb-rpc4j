package cn.tzq0301;

import cn.tzq0301.client.RpcClient;
import cn.tzq0301.common.request.RpcRequest;
import cn.tzq0301.common.response.RpcResponse;

public class ClientApplication {
    public static void main(String[] args) throws Exception {
        RpcClient client = new RpcClient("localhost", 8080);
        RpcRequest<String> req = new RpcRequest<>("Hello", "No data");
        RpcResponse<?> resp = client.call(req);
        System.out.println(resp);
        client.shutdown();
    }
}
