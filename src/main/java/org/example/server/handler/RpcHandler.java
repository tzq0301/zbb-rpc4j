package org.example.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.java.Log;
import org.example.common.model.request.RpcRequest;
import org.example.common.model.response.RpcResponse;
import org.example.server.demo.DemoService;
import org.example.server.model.middleware.RpcMiddleware;
import org.example.server.model.service.ServiceRegistry;
import org.example.server.service.RpcService;

import java.util.Arrays;
import java.util.List;

@Log
public class RpcHandler extends ChannelInboundHandlerAdapter {
    private final List<RpcMiddleware> rpcMiddlewares;

    public RpcHandler(RpcMiddleware ...rpcMiddlewares) {
        this.rpcMiddlewares = Arrays.asList(rpcMiddlewares);

        // TODO 反射扫描
        ServiceRegistry.register("Hello", (req, resp) -> {
            log.info("Hello World!!!");
            resp.setCode(0);
        });

        ServiceRegistry.register("Demo", new DemoService()::demo);
    }

    private RpcResponse<?> handleWithMiddleWares(RpcRequest<?> req) {
        RpcService service;

        try {
            service = ServiceRegistry.get(req.getServiceName());
        } catch (NullPointerException e) {
            return RpcResponse.invalidParam("Service not found");
        }

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
