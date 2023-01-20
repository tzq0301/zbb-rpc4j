package org.example;

import org.example.client.RpcClient;
import org.example.server.model.RpcRequest;
import org.example.server.model.RpcResponse;

public class ClientApplication {
    public static void main(String[] args) throws Exception {
        RpcClient client = new RpcClient("localhost", 8080);
        RpcRequest<String> req = new RpcRequest<>("Hello", "No data");
        RpcResponse<?> resp = client.call(req);
        System.out.println(resp);
        client.shutdown();
    }
}
