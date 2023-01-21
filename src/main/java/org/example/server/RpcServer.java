package org.example.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.example.server.handler.RpcCodec;
import org.example.server.handler.RpcHandler;
import org.example.server.model.middleware.RpcMiddleware;

import java.nio.charset.StandardCharsets;

public class RpcServer {
    private final RpcMiddleware[] rpcMiddlewares;

    private final int port;

    public RpcServer(int port, RpcMiddleware ...rpcMiddlewares) {
        this.port = port;
        this.rpcMiddlewares = rpcMiddlewares;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline()
                                    .addLast(new StringDecoder(StandardCharsets.UTF_8), new StringEncoder(StandardCharsets.UTF_8))
                                    .addLast(new RpcCodec())
                                    .addLast(new RpcHandler(rpcMiddlewares));
                        }
                    });

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
