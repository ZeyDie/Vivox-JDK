package com.zeydie.vivox.client.api;

import com.zeydie.vivox.client.api.natives.VivoxClientNative;
import com.zeydie.vivox.client.api.natives.VivoxClientParticipantNative;
import com.zeydie.vivox.client.handlers.ParticipantHandler;
import com.zeydie.vivox.client.handlers.VivoxStateHandler;
import com.zeydie.vivox.common.IInitialization;
import com.zeydie.vivox.common.Vivox;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.val;

public final class VivoxClientAPI implements IInitialization {
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

    @Override
    public void init() {
        Vivox.info("Initialization VivoxAPI...");
        VivoxClientNative.setStateCallbacks(new VivoxStateHandler());
        VivoxClientParticipantNative.setParticipantCallbacks(new ParticipantHandler());

        @NonNull val server = "https://unity.vivox.com/appconfig/90715-vivox-90002-udash";
        @NonNull val user = "ZeyDie";
        @NonNull val participant = String.format(".%s.%s.", "90715-vivox-90002-udash", user);

        VivoxClientNative.init(
                server,
                participant,
                user
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