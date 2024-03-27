package com.zeydie.vivox.client.api;

import com.zeydie.vivox.client.VivoxClient;
import com.zeydie.vivox.client.api.natives.VivoxClientNative;
import com.zeydie.vivox.client.api.natives.VivoxClientParticipantNative;
import com.zeydie.vivox.client.configs.ClientVivoxConfig;
import com.zeydie.vivox.client.handlers.ParticipantHandler;
import com.zeydie.vivox.client.handlers.VivoxStateHandler;
import com.zeydie.vivox.common.IService;
import com.zeydie.vivox.common.Vivox;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.val;
import org.jetbrains.annotations.NotNull;

public final class VivoxClientAPI implements IService {
    @Setter
    @Getter
    private static boolean connected;
    @Setter
    @Getter
    private static boolean login;

    private @NotNull String player = "ZeyDie";

    @Getter
    private static final @NotNull ClientVivoxConfig clientVivoxConfig = new ClientVivoxConfig(VivoxClient.getConfigsPath());

    @Override
    public void pre() {
        VivoxClientNative.loadLibs();
    }

    @Override
    public void init() {
        Vivox.info("Initialization VivoxAPI...");

        VivoxClientNative.setStateCallbacks(new VivoxStateHandler());

        VivoxClientParticipantNative.setParticipantCallbacks(new ParticipantHandler());

        @NonNull val data = clientVivoxConfig.getData();

        VivoxClientNative.init(
                data.getServer(),
                data.getParticipant(this.player),
                this.player
        );
        VivoxClientNative.connect();
    }

    @Override
    public void post() {

    }

    public void exit() {
        Vivox.info("VivoxClient exit");

        VivoxChannelsAPI.leaveFromAllChannels();

        VivoxClientNative.logout();
        VivoxClientNative.disconnect();
    }
}