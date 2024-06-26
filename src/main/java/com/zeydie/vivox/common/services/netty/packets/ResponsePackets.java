package com.zeydie.vivox.common.services.netty.packets;

import com.zeydie.vivox.common.services.netty.utils.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

public enum ResponsePackets {
    RESPONSE_LOGIN,
    RESPONSE_JOIN_CHANNEL;

    public @NotNull ByteBuf createByteBufPacket() {
        return ByteBufUtil.writeString(ByteBufUtil.createByteByf(), this.name());
    }
}