package com.zeydie.vivox.server.api;

import com.zeydie.vivox.common.interfaces.IServiceCloseable;
import com.zeydie.vivox.server.services.netty.NettyServer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.SocketChannel;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

public final class NettyServerAPI implements IServiceCloseable {
    private final static @NotNull NettyServer nettyServer = new NettyServer();

    @Override
    public void pre() {
        this.nettyServer.pre();
    }

    @Override
    public void init() {
        this.nettyServer.init();
    }

    @Override
    public void post() {
        this.nettyServer.post();
    }

    @Override
    public void close() {
        this.nettyServer.close();
    }

    public static void sendPacketToClient(
            @NonNull final SocketChannel socketChannel,
            @NonNull final ByteBuf byteBuf
    ) {
        nettyServer.sendPacketToClient(socketChannel, byteBuf);
    }
}