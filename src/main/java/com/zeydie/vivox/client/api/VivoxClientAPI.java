package com.zeydie.vivox.client.api;

import com.zeydie.vivox.client.VivoxClient;
import com.zeydie.vivox.client.api.natives.VivoxClientNative;
import com.zeydie.vivox.client.api.natives.VivoxClientParticipantNative;
import com.zeydie.vivox.client.handlers.ParticipantHandler;
import com.zeydie.vivox.client.handlers.VivoxStateHandler;
import com.zeydie.vivox.common.Vivox;
import com.zeydie.vivox.common.interfaces.IServiceCloseable;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public final class VivoxClientAPI implements IServiceCloseable {
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

        @NonNull val data = Vivox.getVivoxConfig().getData();
        @NonNull val username = VivoxClient.getUsername();

        VivoxClientNative.init(
                data.getServer(),
                data.getParticipant(username),
                username
        );
        VivoxClientNative.connect();

        while (!this.isConnected())
            TimeUnit.MILLISECONDS.sleep(250);
    }

    @Override
    public void post() {

    }

    @Override
    public void close() {
        Vivox.info("VivoxClient exit");

        VivoxChannelsAPI.leaveFromAllChannels();

        VivoxClientNative.logout();
        VivoxClientNative.disconnect();
    }
}