package com.zeydie.vivox.server.services.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.SocketChannel;
import lombok.NonNull;

public interface INettyServerTransfer {
    void sendPacketToClient(
            @NonNull final SocketChannel socketChannel,
            @NonNull final ByteBuf byteBuf
    );
}