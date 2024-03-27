package client;

import com.zeydie.vivox.client.VivoxClient;
import com.zeydie.vivox.client.api.VivoxClientAPI;
import com.zeydie.vivox.client.api.natives.VivoxClientNative;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VivoxLoginTest {
    private static final VivoxClient vivoxClient = new VivoxClient();

    @SneakyThrows
    @Test
    public void login() {
        vivoxClient.pre();
        vivoxClient.init();
        vivoxClient.post();

        VivoxClientNative.login(
                "ZeyDie",
                "token"
        );

        Assertions.assertEquals(VivoxClientAPI.getVivoxStateHandler().getLastErrorCode(), 0);
    }
}