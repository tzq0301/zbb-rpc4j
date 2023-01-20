package org.example.server.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.server.demo.DemoService;
import org.example.server.model.RpcMiddleware;
import org.example.server.model.RpcRequest;
import org.example.server.model.RpcResponse;
import org.example.server.model.RpcService;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class RpcHandler extends ChannelInboundHandlerAdapter {
    private final Map<String, RpcService> registeredServices;

    private final List<RpcMiddleware> rpcMiddlewares;

    public RpcHandler(List<RpcMiddleware> rpcMiddlewares) {
        this.rpcMiddlewares = rpcMiddlewares;

        // FIXME 反射 -> 扫描
        this.registeredServices = Map.of("Hello", (req, resp) -> {
            System.out.println("Hello World!!!");
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
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws JsonProcessingException {
        ByteBuf buf = (ByteBuf) msg;
        String s = buf.toString(StandardCharsets.UTF_8);
        RpcRequest<?> rpcRequest = new ObjectMapper().readValue(s, RpcRequest.class);
        RpcResponse<?> resp = handleWithMiddleWares(rpcRequest);
        ctx.writeAndFlush(Unpooled.copiedBuffer(new ObjectMapper().writeValueAsString(resp), StandardCharsets.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
