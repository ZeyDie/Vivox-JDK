package com.zeydie.vivox.server.services.netty.handlers;

import com.google.gson.Gson;
import com.zeydie.vivox.api.VivoxChannel;
import com.zeydie.vivox.api.data.SipData;
import com.zeydie.vivox.api.data.TokenData;
import com.zeydie.vivox.common.services.netty.handlers.ProcessingHandler;
import com.zeydie.vivox.common.services.netty.packets.RequestPackets;
import com.zeydie.vivox.common.services.netty.packets.ResponsePackets;
import com.zeydie.vivox.common.services.netty.utils.ByteBufUtil;
import com.zeydie.vivox.server.VivoxServer;
import com.zeydie.vivox.server.api.NettyServerAPI;
import com.zeydie.vivox.server.api.data.ServerVivoxData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.zeydie.vivox.common.services.netty.packets.ResponsePackets.RESPONSE_JOIN_CHANNEL;
import static com.zeydie.vivox.common.services.netty.packets.ResponsePackets.RESPONSE_LOGIN;

@Log4j2
public class ServerProcessingHandler extends ProcessingHandler {
    private final @NonNull Gson gson = new Gson();

    @SneakyThrows
    @Override
    public void handle(
            @NotNull final ChannelHandlerContext channelHandlerContext,
            @NonNull final ByteBuf byteBuf
    ) {
        @NonNull val packetId = ByteBufUtil.readString(byteBuf);

        log.debug("Packet {}", packetId);

        @NonNull val data = VivoxServer.getVivoxServerAPI().getServerVivoxConfig().getData();
        @NonNull val secretKey = data.getSecretKey();

        if (packetId.startsWith("REQUEST_")) {
            @Nullable val requestPacket = RequestPackets.valueOf(packetId);

            switch (requestPacket) {
                case REQUEST_LOGIN -> {
                    @NonNull val player = ByteBufUtil.readString(byteBuf);

                    @NonNull val buffer = RESPONSE_LOGIN.createByteBufPacket();

                    @NonNull val token = ServerVivoxData
                            .builder()
                            .player(player)
                            .user(data.getUser())
                            .domain(data.getDomain())
                            .exp(System.currentTimeMillis() / 1000 + 5 * 60 * 60)
                            .build()
                            .getLoginToken(secretKey);

                    ByteBufUtil.writeString(buffer, token);

                    NettyServerAPI.sendPacketToClient((SocketChannel) channelHandlerContext.channel(), buffer);
                }
                case REQUEST_JOIN_CHANNEL -> {
                    @NonNull val player = ByteBufUtil.readString(byteBuf);
                    @NonNull val channel = ByteBufUtil.readString(byteBuf);

                    @NonNull val buffer = RESPONSE_JOIN_CHANNEL.createByteBufPacket();

                    @NonNull val serverVivoxData = ServerVivoxData
                            .builder()
                            .player(player)
                            .user(data.getUser())
                            .channelName(channel)
                            .domain(data.getDomain())
                            .exp(System.currentTimeMillis() / 1000 + 5 * 60 * 60)
                            .build();

                    @NonNull val vivoxChannel = new VivoxChannel(
                            new SipData(
                                    serverVivoxData.getUser(),
                                    channel,
                                    data.getDomain()
                            ),
                            new TokenData(serverVivoxData.getJoinToken(secretKey))
                    );

                    ByteBufUtil.writeString(buffer, this.gson.toJson(vivoxChannel));

                    NettyServerAPI.sendPacketToClient((SocketChannel) channelHandlerContext.channel(), buffer);
                }
                default -> log.error("Can't found packet {}", packetId);
            }
        } else {
            @Nullable val responsePackets = ResponsePackets.valueOf(packetId);

            switch (responsePackets) {
                case RESPONSE_LOGIN -> {

                }
                default -> log.error("Can't found packet {}", packetId);
            }
        }
    }
}