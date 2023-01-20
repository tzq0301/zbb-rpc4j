package org.example.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.java.Log;
import org.example.server.demo.DemoService;
import org.example.server.middleware.RpcMiddleware;
import org.example.server.model.RpcRequest;
import org.example.server.model.RpcResponse;
import org.example.server.service.RpcService;

import java.util.List;
import java.util.Map;

@Log
public class RpcHandler extends ChannelInboundHandlerAdapter {
    private final Map<String, RpcService> registeredServices;

    private final List<RpcMiddleware> rpcMiddlewares;

    public RpcHandler(List<RpcMiddleware> rpcMiddlewares) {
        this.rpcMiddlewares = rpcMiddlewares;

        // FIXME 反射 -> 扫描
        this.registeredServices = Map.of("Hello", (req, resp) -> {
            log.info("Hello World!!!");
            resp.setCode(0);
        }, "Demo", new DemoService()::demo); // Should be "DemoService.demo"
    }

    private RpcResponse<?> handleWithMiddleWares(RpcRequest<?> req) {
        RpcService service = registeredServices.get(req.getMethodName());

        RpcResponse<?> resp = new RpcResponse<>();

        for (RpcMiddleware middleware : rpcMiddlewares) {
            service = middleware.apply(service);
        }

        service.accept(req, resp);

        return resp;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        RpcRequest<?> req = (RpcRequest<?>) msg;
        RpcResponse<?> reps = handleWithMiddleWares(req);
        ctx.writeAndFlush(reps);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
