package org.example.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.example.common.model.RpcRequest;
import org.example.common.model.RpcResponse;

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
