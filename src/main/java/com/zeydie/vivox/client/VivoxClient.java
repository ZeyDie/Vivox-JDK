package com.zeydie.vivox.client;

import com.zeydie.vivox.client.api.*;
import com.zeydie.vivox.common.Vivox;
import lombok.Getter;
import lombok.Setter;
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

    @Getter
    private static final @NotNull NettyClientAPI nettyClientAPI = new NettyClientAPI();

    @Setter
    @Getter
    private static @NotNull String username = "ZeyDie";
    @Setter
    @Getter
    private static @NotNull String channel = "unison";

    @Override
    public void pre() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> close()));

        vivoxClientAPI.pre();
        vivoxDevicesAPI.pre();
        vivoxChannelsAPI.pre();
        vivoxClientParticipantAPI.pre();

        nettyClientAPI.pre();
    }

    @Override
    public void init() {
        vivoxClientAPI.init();
        vivoxDevicesAPI.init();
        vivoxChannelsAPI.init();
        vivoxClientParticipantAPI.init();

        nettyClientAPI.init();
    }

    @Override
    public void post() {
        vivoxClientAPI.post();
        vivoxDevicesAPI.post();
        vivoxChannelsAPI.post();
        vivoxClientParticipantAPI.post();

        nettyClientAPI.post();
    }

    @Override
    public void close() {
        vivoxClientAPI.close();

        nettyClientAPI.close();
    }

    public static @NotNull Path getConfigsPath() {
        return Vivox.getConfigsPath().resolve("client");
    }
}