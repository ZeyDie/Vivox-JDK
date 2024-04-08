package com.zeydie.vivox.client.services.netty;

import com.zeydie.vivox.client.services.netty.handlers.ClientProcessingHandler;
import com.zeydie.vivox.common.services.netty.NettyService;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

@Log4j2
public class NettyClient extends NettyService implements INettyClientTransfer {
    private final @NotNull EventLoopGroup workerGroup = new NioEventLoopGroup(NettyService.getClientThreads());

    private final @NotNull Bootstrap clientBootstrap = new Bootstrap().group(this.workerGroup);
    private ChannelFuture channelFuture;

    private SocketChannel serverSocketChannel;

    @Override
    public void pre() {
        this.clientBootstrap.channel(NioSocketChannel.class);
    }

    @Override
    public void init() {
        this.clientBootstrap.handler(
                new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(@NotNull final SocketChannel socketChannel) throws Exception {
                        log.debug("Init socket channel {}", socketChannel);

                        serverSocketChannel = socketChannel;
                        serverSocketChannel.pipeline().addLast(new ClientProcessingHandler());
                    }
                }
        );
    }

    @SneakyThrows
    @Override
    public void post() {
        this.channelFuture = this.clientBootstrap
                .option(ChannelOption.SO_KEEPALIVE, true)
                .connect(NettyService.getServerAddress(), NettyService.getServerPort())
                .sync();
    }

    @SneakyThrows
    @Override
    public void close() {
        this.workerGroup.shutdownGracefully();

        this.channelFuture
                .channel()
                .closeFuture()
                .sync();
    }

    @Override
    public void sendPacketToServer(@NonNull final ByteBuf byteBuf) {
        this.serverSocketChannel.writeAndFlush(byteBuf);
    }
}