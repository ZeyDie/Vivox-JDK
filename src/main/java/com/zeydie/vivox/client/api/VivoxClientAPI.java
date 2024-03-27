package com.zeydie.vivox.client.api;

import com.zeydie.vivox.client.VivoxClient;
import com.zeydie.vivox.client.api.natives.VivoxClientNative;
import com.zeydie.vivox.client.api.natives.VivoxClientParticipantNative;
import com.zeydie.vivox.client.configs.ClientVivoxConfig;
import com.zeydie.vivox.client.handlers.ParticipantHandler;
import com.zeydie.vivox.client.handlers.VivoxStateHandler;
import com.zeydie.vivox.common.IService;
import com.zeydie.vivox.common.Vivox;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public final class VivoxClientAPI implements IService {
    private @NotNull String player = "ZeyDie";

    @Getter
    private static final @NotNull VivoxStateHandler vivoxStateHandler = new VivoxStateHandler();
    @Getter
    private static final @NotNull ParticipantHandler participantHandler = new ParticipantHandler();

    @Setter
    @Getter
    private static boolean connected;
    @Setter
    @Getter
    private static boolean login;

    @Getter
    private static final @NotNull ClientVivoxConfig clientVivoxConfig = new ClientVivoxConfig(VivoxClient.getConfigsPath());

    @Override
    public void pre() {
        VivoxClientNative.loadLibs();
    }

    @SneakyThrows
    @Override
    public void init() {
        Vivox.info("Initialization VivoxAPI...");

        VivoxClientNative.setStateCallbacks(vivoxStateHandler);

        VivoxClientParticipantNative.setParticipantCallbacks(participantHandler);

        @NonNull val data = clientVivoxConfig.getData();

        VivoxClientNative.init(
                data.getServer(),
                data.getParticipant(this.player),
                this.player
        );
        VivoxClientNative.connect();

        while (!this.isConnected())
            TimeUnit.MILLISECONDS.sleep(250);
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