package com.zeydie.vivox.client.services.netty.handlers;

import com.google.gson.Gson;
import com.zeydie.vivox.api.VivoxChannel;
import com.zeydie.vivox.client.VivoxClient;
import com.zeydie.vivox.client.api.VivoxChannelsAPI;
import com.zeydie.vivox.client.api.natives.VivoxClientNative;
import com.zeydie.vivox.common.services.netty.handlers.ProcessingHandler;
import com.zeydie.vivox.common.services.netty.packets.RequestPackets;
import com.zeydie.vivox.common.services.netty.packets.ResponsePackets;
import com.zeydie.vivox.common.services.netty.utils.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Log4j2
public class ClientProcessingHandler extends ProcessingHandler {
    private final @NonNull Gson gson = new Gson();

    @Override
    public void handle(
            @NotNull final ChannelHandlerContext channelHandlerContext,
            @NonNull final ByteBuf byteBuf
    ) {
        @NonNull val packetId = ByteBufUtil.readString(byteBuf);

        log.debug("Packet {}", packetId);

        if (packetId.startsWith("REQUEST_")) {
            @Nullable val requestPacket = RequestPackets.valueOf(packetId);

            log.debug("requestPacket {}", requestPacket);

            switch (requestPacket) {
                default -> log.error("Can't found packet {}", packetId);
            }
        } else {
            @Nullable val responsePackets = ResponsePackets.valueOf(packetId);

            switch (responsePackets) {
                case RESPONSE_LOGIN -> {
                    @NonNull val token = ByteBufUtil.readString(byteBuf);

                    VivoxClientNative.login(
                            VivoxClient.getUsername(),
                            token
                    );

                    val byteBufJoinChannel = RequestPackets.REQUEST_JOIN_CHANNEL.createByteBufPacket();

                    ByteBufUtil.writeString(byteBufJoinChannel, VivoxClient.getUsername());
                    ByteBufUtil.writeString(byteBufJoinChannel, VivoxClient.getChannel());

                    VivoxClient.getNettyClientAPI().sendPacketToServer(byteBufJoinChannel);
                }
                case RESPONSE_JOIN_CHANNEL -> {
                    @NonNull val vivoxChannel = this.gson.fromJson(ByteBufUtil.readString(byteBuf), VivoxChannel.class);

                    log.debug("vivoxChannel {}", vivoxChannel);

                    VivoxChannelsAPI.setVivoxChannel(vivoxChannel);
                    VivoxChannelsAPI.joinToChannel(vivoxChannel);
                }
                default -> log.error("Can't found packet {}", packetId);
            }
        }
    }
}
