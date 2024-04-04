package client;

import com.zeydie.vivox.client.VivoxClient;
import com.zeydie.vivox.client.api.VivoxClientAPI;
import com.zeydie.vivox.client.api.natives.VivoxClientNative;
import com.zeydie.vivox.server.VivoxServer;
import com.zeydie.vivox.server.api.data.ServerVivoxData;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Log
public class VivoxLoginTest {
    private static final VivoxClient vivoxClient = new VivoxClient();
    private static final VivoxServer vivoxServer = new VivoxServer();

    @SneakyThrows
    @Test
    public void login() {
        vivoxClient.pre();
        vivoxServer.pre();
        vivoxClient.init();
        vivoxServer.init();
        vivoxClient.post();
        vivoxServer.post();

        val data = VivoxServer.getVivoxServerAPI().getServerVivoxConfig().getData();
        val player = "ZeyDie";
        val secretKey = data.getSecretKey();
        val loginToken = ServerVivoxData
                .builder()
                .player(player)
                .user(data.getUser())
                .domain(data.getDomain())
                .exp(System.currentTimeMillis() / 1000 + 5 * 60 * 60)
                .build()
                .getLoginToken(secretKey);

        log.info("Data: " + data);
        log.info("Player: " + player);
        log.info("SecretKey: " + secretKey);
        log.info("LoginToken: " + loginToken);

        VivoxClientNative.login(
                player,
                loginToken
        );

        Assertions.assertEquals(VivoxClientAPI.getVivoxStateHandler().getLastErrorCode(), 0);
    }
}