package netty;

import com.zeydie.vivox.client.VivoxClient;
import com.zeydie.vivox.common.services.netty.packets.RequestPackets;
import com.zeydie.vivox.common.services.netty.utils.ByteBufUtil;
import com.zeydie.vivox.server.VivoxServer;
import lombok.SneakyThrows;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NettyTest {
    private static final @NotNull VivoxClient vivoxClient = new VivoxClient();
    private static final @NotNull VivoxServer vivoxServer = new VivoxServer();

    @Test
    @Order(0)
    public void init() {
        vivoxServer.pre();
        vivoxServer.init();
        vivoxServer.post();

        vivoxClient.pre();
        vivoxClient.init();
        vivoxClient.post();
    }

    @SneakyThrows
    @Test
    @Order(3)
    public void sendLoginPacket() {
        val byteBuf = RequestPackets.REQUEST_LOGIN.createByteBufPacket();

        ByteBufUtil.writeString(byteBuf, VivoxClient.getUsername());

        VivoxClient.getNettyClientAPI().sendPacketToServer(byteBuf);
    }

    @SneakyThrows
    @Test
    @Order(4)
    public void sendJoinChannelPacket() {
        val byteBuf = RequestPackets.REQUEST_JOIN_CHANNEL.createByteBufPacket();

        ByteBufUtil.writeString(byteBuf, VivoxClient.getUsername());
        ByteBufUtil.writeString(byteBuf, "test_channel");

        VivoxClient.getNettyClientAPI().sendPacketToServer(byteBuf);
    }
}