package cn.tzq0301.server.handler;

import cn.tzq0301.server.model.context.RpcContext;
import cn.tzq0301.server.model.context.RpcContexts;
import cn.tzq0301.registry.ServiceRegistry;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.java.Log;
import cn.tzq0301.common.request.RpcRequest;
import cn.tzq0301.common.response.RpcResponse;
import cn.tzq0301.server.model.middleware.RpcMiddleware;
import cn.tzq0301.server.model.service.RpcService;

import java.util.Arrays;
import java.util.List;

@Log
public class RpcHandler extends ChannelInboundHandlerAdapter {
    private final ServiceRegistry serviceRegistry;

    private final List<RpcMiddleware> rpcMiddlewares;

    public RpcHandler(ServiceRegistry serviceRegistry, RpcMiddleware ...rpcMiddlewares) {
        this.serviceRegistry = serviceRegistry;
        this.rpcMiddlewares = Arrays.asList(rpcMiddlewares);
    }

    private RpcResponse<?> handleWithMiddleWares(ChannelHandlerContext ctx, RpcRequest<?> req) {
        RpcService service;

        try {
            service = serviceRegistry.get(req.getServiceName());
        } catch (NullPointerException e) {
            return RpcResponse.invalidParam("Service not found");
        }

        for (RpcMiddleware middleware : rpcMiddlewares) {
            service = middleware.apply(service);
        }

        RpcResponse<?> resp = new RpcResponse<>();
        RpcContext context = RpcContexts.newRpcContext(ctx);

        service.call(context, req, resp);

        return resp;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        RpcRequest<?> req = (RpcRequest<?>) msg;
        RpcResponse<?> reps = handleWithMiddleWares(ctx, req);
        ctx.writeAndFlush(reps);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
