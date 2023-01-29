package cn.tzq0301.server.handler;

import cn.tzq0301.common.request.RpcRequest;
import cn.tzq0301.common.response.RpcResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

public class RpcCodec extends MessageToMessageCodec<String, RpcResponse<?>> {
    private final ObjectMapper mapper;

    public RpcCodec() {
        this.mapper = new ObjectMapper();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcResponse<?> msg, List<Object> out) throws Exception {
        out.add(mapper.writeValueAsString(msg));
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        out.add(mapper.readValue(msg, RpcRequest.class));
    }


}
