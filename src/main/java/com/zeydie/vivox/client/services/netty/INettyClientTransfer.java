package com.zeydie.vivox.client.services.netty;

import io.netty.buffer.ByteBuf;
import lombok.NonNull;

public interface INettyClientTransfer {
    void sendPacketToServer(@NonNull final ByteBuf byteBuf);
}