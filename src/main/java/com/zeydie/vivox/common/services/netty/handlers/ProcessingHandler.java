package com.zeydie.vivox.common.services.netty.handlers;

import com.zeydie.vivox.common.Vivox;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.NonNull;
import lombok.val;
import org.jetbrains.annotations.NotNull;

public abstract class ProcessingHandler extends ChannelInboundHandlerAdapter {
    private ByteBuf byteBuf;

    @Override
    public void handlerAdded(@NotNull final ChannelHandlerContext channelHandlerContext) {
        Vivox.debug("Handler added");

        this.byteBuf = channelHandlerContext.alloc().buffer(4);
    }

    @Override
    public void handlerRemoved(@NotNull final ChannelHandlerContext channelHandlerContext) {
        Vivox.debug("Handler removed");

        this.byteBuf.release();
        this.byteBuf = null;
    }

    @Override
    public void exceptionCaught(
            @NotNull final ChannelHandlerContext channelHandlerContext,
            @NonNull final Throwable throwable
    ) {
        throwable.printStackTrace();
        channelHandlerContext.close();
    }

    @Override
    public void channelRead(
            @NotNull final ChannelHandlerContext channelHandlerContext,
            @NotNull final Object msg
    ) throws Exception {
        @NonNull val channelByteBuf = (ByteBuf) msg;

        this.byteBuf.writeBytes(channelByteBuf);

        channelByteBuf.release();

        if (this.byteBuf.readableBytes() >= 4) this.handle(channelHandlerContext, this.byteBuf);
    }

    public abstract void handle(
            @NotNull final ChannelHandlerContext channelHandlerContext,
            @NonNull final ByteBuf byteBuf
    );
}