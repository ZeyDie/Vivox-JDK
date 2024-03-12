package client;

import com.zeydie.vivox.client.VivoxClient;
import lombok.extern.java.Log;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@Log
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VivoxClientTest {
    private static final VivoxClient vivoxClient = new VivoxClient();

    @Test
    @Order(2)
    public void pre() {
        vivoxClient.pre();
    }

    @Test
    @Order(3)
    public void init() {
        vivoxClient.init();
    }

    @Test
    @Order(4)
    public void post() {
        vivoxClient.post();
    }

    @Test
    @Order(5)
    public void logout() {
        vivoxClient.exit();
    }
}