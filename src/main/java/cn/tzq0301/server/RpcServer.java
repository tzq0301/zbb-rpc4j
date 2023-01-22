package cn.tzq0301.server;

import cn.tzq0301.server.handler.RpcHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import cn.tzq0301.server.handler.RpcCodec;
import cn.tzq0301.server.model.middleware.RpcMiddleware;
import cn.tzq0301.server.model.service.ServiceRegistry;
import cn.tzq0301.server.service.RpcService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RpcServer {
    private final int port;

    private final List<RpcMiddleware> rpcMiddlewares;

    public RpcServer(int port) {
        this.port = port;
        this.rpcMiddlewares = new ArrayList<>();
    }

    public void addMiddleware(final RpcMiddleware middleware) {
        this.rpcMiddlewares.add(middleware);
    }

    public void addMiddlewares(final RpcMiddleware... middleware) {
        Arrays.stream(middleware).forEach(this::addMiddleware);
    }

    public void registerService(final String serviceName, final RpcService service) {
        ServiceRegistry.register(serviceName, service);
    }

    public void registerServices(final Map<String, RpcService> services) {
        services.forEach(this::registerService);
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
                                    .addLast(new RpcHandler(rpcMiddlewares.toArray(new RpcMiddleware[0])));
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
