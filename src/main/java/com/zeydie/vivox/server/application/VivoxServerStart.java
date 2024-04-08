package com.zeydie.vivox.server.application;

import com.zeydie.vivox.server.VivoxServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VivoxServerStart {
    private static final @NotNull VivoxServer vivoxServer = new VivoxServer();

    public static void main(@Nullable final String[] args) {
        vivoxServer.pre();
        vivoxServer.init();
        vivoxServer.post();
    }
}