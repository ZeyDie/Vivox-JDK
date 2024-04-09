import com.zeydie.vivox.client.VivoxClient;
import com.zeydie.vivox.common.services.netty.packets.RequestPackets;
import com.zeydie.vivox.common.services.netty.utils.ByteBufUtil;
import com.zeydie.vivox.server.VivoxServer;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;

@Log4j2
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VivoxTest {
    private static final @NotNull VivoxClient vivoxClient = new VivoxClient();
    private static final @NotNull VivoxServer vivoxServer = new VivoxServer();

    @Test
    @Order(0)
    public void pre() {
        vivoxServer.pre();
        vivoxClient.pre();
    }

    @Test
    @Order(1)
    public void init() {
        vivoxServer.init();
        vivoxClient.init();
    }

    @Test
    @Order(2)
    public void post() {
        vivoxServer.post();
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
        ByteBufUtil.writeString(byteBuf, VivoxClient.getChannel());

        VivoxClient.getNettyClientAPI().sendPacketToServer(byteBuf);
    }
}