package org.example.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.example.client.handler.ClientHandler;
import org.example.common.model.request.RpcRequest;
import org.example.common.model.response.RpcResponse;

import java.nio.charset.StandardCharsets;

public class RpcClient {
    private final String host;

    private final int port;

    private final ClientHandler clientHandler;

    private final EventLoopGroup workerGroup;

    private final Bootstrap bootstrap;

    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.clientHandler = new ClientHandler();
        this.workerGroup = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap()
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline()
                                .addLast(new StringDecoder(StandardCharsets.UTF_8), new StringEncoder(StandardCharsets.UTF_8))
                                .addLast(clientHandler);
                    }
                });
    }

    public synchronized RpcResponse<?> call(RpcRequest<?> req) throws InterruptedException, JsonProcessingException {
        ChannelFuture f = bootstrap.connect(host, port).sync();

        RpcResponse<?> resp = clientHandler.call(req);

        f.channel().closeFuture().sync();

        return resp;
    }

    public void shutdown() {
        workerGroup.shutdownGracefully();
    }
}
