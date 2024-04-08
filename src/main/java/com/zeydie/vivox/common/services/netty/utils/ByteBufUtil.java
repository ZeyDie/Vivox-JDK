package com.zeydie.vivox.common.services.netty.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.NonNull;
import lombok.val;
import org.jetbrains.annotations.NotNull;

public final class ByteBufUtil {
    public static @NotNull ByteBuf createByteByf() {
        return Unpooled.buffer();
    }

    public static @NotNull ByteBuf writeString(
            @NonNull final ByteBuf byteBuf,
            @NonNull final String string
    ) {
        byteBuf.writeInt(string.length());

        for (val ch : string.toCharArray())
            byteBuf.writeChar(ch);

        return byteBuf;
    }

    public static @NotNull String readString(@NonNull final ByteBuf byteBuf) {
        val stringBuilder = new StringBuilder();
        val length = byteBuf.readInt();

        for (int i = 0; i < length; i++)
            stringBuilder.append(byteBuf.readChar());

        return stringBuilder.toString();
    }
}