package com.zeydie.vivox.client;

import com.zeydie.vivox.client.api.VivoxChannelsAPI;
import com.zeydie.vivox.client.api.VivoxClientAPI;
import com.zeydie.vivox.client.api.VivoxClientParticipantAPI;
import com.zeydie.vivox.client.api.VivoxDevicesAPI;
import com.zeydie.vivox.common.Vivox;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class VivoxClient extends Vivox {
    @Getter
    private static final @NotNull VivoxClientAPI vivoxClientAPI = new VivoxClientAPI();
    @Getter
    private static final @NotNull VivoxDevicesAPI vivoxDevicesAPI = new VivoxDevicesAPI();
    @Getter
    private static final @NotNull VivoxChannelsAPI vivoxChannelsAPI = new VivoxChannelsAPI();
    @Getter
    private static final @NotNull VivoxClientParticipantAPI vivoxClientParticipantAPI = new VivoxClientParticipantAPI();

    @Override
    public void pre() {
        vivoxClientAPI.pre();
        vivoxDevicesAPI.pre();
        vivoxChannelsAPI.pre();
        vivoxClientParticipantAPI.pre();
    }

    @Override
    public void init() {
        vivoxClientAPI.init();
        vivoxDevicesAPI.init();
        vivoxChannelsAPI.init();
        vivoxClientParticipantAPI.init();
    }

    @Override
    public void post() {
        vivoxClientAPI.post();
        vivoxDevicesAPI.post();
        vivoxChannelsAPI.post();
        vivoxClientParticipantAPI.post();
    }

    public void exit() {
        vivoxClientAPI.exit();
    }

    public static @NotNull Path getConfigsPath() {
        return Vivox.getConfigsPath().resolve("client");
    }
}