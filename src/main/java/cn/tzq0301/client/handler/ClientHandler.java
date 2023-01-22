package cn.tzq0301.client.handler;

import cn.tzq0301.common.model.request.RpcRequest;
import cn.tzq0301.common.model.response.RpcResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private ChannelHandlerContext ctx;

    private RpcResponse<?> resp;

    private final ObjectMapper mapper;

    public ClientHandler() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String s = (String) msg;
        this.resp = mapper.readValue(s, RpcResponse.class);
        notify();
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public synchronized RpcResponse<?> call(RpcRequest<?> req) throws InterruptedException, JsonProcessingException {
        ctx.writeAndFlush(mapper.writeValueAsString(req));
        wait();
        return resp;
    }
}
