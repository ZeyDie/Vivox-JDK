package com.zeydie.vivox.client.api;

import com.zeydie.vivox.client.services.netty.INettyClientTransfer;
import com.zeydie.vivox.client.services.netty.NettyClient;
import com.zeydie.vivox.common.interfaces.IServiceCloseable;
import io.netty.buffer.ByteBuf;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

public final class NettyClientAPI implements IServiceCloseable, INettyClientTransfer {
    private final @NotNull NettyClient nettyClient = new NettyClient();

    @Override
    public void pre() {
        this.nettyClient.pre();
    }

    @Override
    public void init() {
        this.nettyClient.init();
    }

    @Override
    public void post() {
        this.nettyClient.post();
    }

    @Override
    public void close() {
        this.nettyClient.close();
    }

    @Override
    public void sendPacketToServer(@NonNull final ByteBuf byteBuf) {
        this.nettyClient.sendPacketToServer(byteBuf);
    }
}