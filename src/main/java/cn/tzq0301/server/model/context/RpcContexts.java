package cn.tzq0301.server.model.context;

import io.netty.channel.ChannelHandlerContext;

public final class RpcContexts {
    public static RpcContext newRpcContext(ChannelHandlerContext ctx) {
        return new RpcContext(ctx.channel().remoteAddress());
    }
}
