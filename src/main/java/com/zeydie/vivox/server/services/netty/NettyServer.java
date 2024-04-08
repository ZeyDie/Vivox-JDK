package com.zeydie.vivox.server.services.netty;

import com.zeydie.vivox.common.services.netty.NettyService;
import com.zeydie.vivox.server.services.netty.handlers.ServerProcessingHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

public class NettyServer extends NettyService implements INettyServerTransfer {
    private final @NotNull EventLoopGroup bossGroup = new NioEventLoopGroup(NettyService.getServerThreads());
    private final @NotNull EventLoopGroup workerGroup = new NioEventLoopGroup(NettyService.getServerThreads());

    private final @NotNull ServerBootstrap serverBootstrap = new ServerBootstrap().group(this.bossGroup, this.workerGroup);
    private ChannelFuture channelFuture;

    @Override
    public void pre() {
        this.serverBootstrap.channel(NioServerSocketChannel.class);
    }

    @Override
    public void init() {
        this.serverBootstrap.childHandler(
                new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(@NotNull final SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new ServerProcessingHandler());
                    }
                }
        );
    }

    @SneakyThrows
    @Override
    public void post() {
        this.channelFuture = this.serverBootstrap
                .option(ChannelOption.SO_BACKLOG, 500)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .bind(NettyService.getServerPort())
                .sync();
    }

    @SneakyThrows
    @Override
    public void close() {
        this.workerGroup.shutdownGracefully();
        this.bossGroup.shutdownGracefully();

        this.channelFuture
                .channel()
                .closeFuture()
                .sync();
    }

    @Override
    public void sendPacketToClient(
            @NonNull final SocketChannel clientChannel,
            @NonNull final ByteBuf byteBuf
    ) {
        clientChannel.writeAndFlush(byteBuf);
    }
}