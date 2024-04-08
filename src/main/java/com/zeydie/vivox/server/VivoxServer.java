package com.zeydie.vivox.server;

import com.zeydie.vivox.common.Vivox;
import com.zeydie.vivox.server.api.NettyServerAPI;
import com.zeydie.vivox.server.api.VivoxServerAPI;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class VivoxServer extends Vivox {
    @Getter
    private static final @NotNull VivoxServerAPI vivoxServerAPI = new VivoxServerAPI();

    @Getter
    private static final @NotNull NettyServerAPI nettyServerAPI = new NettyServerAPI();

    @Override
    public void pre() {
        vivoxServerAPI.pre();

        nettyServerAPI.pre();
    }

    @Override
    public void init() {
        vivoxServerAPI.init();

        nettyServerAPI.init();
    }

    @Override
    public void post() {
        vivoxServerAPI.post();

        nettyServerAPI.post();
    }

    @Override
    public void close() {
        nettyServerAPI.close();
    }

    public static @NotNull Path getConfigsPath() {
        return Vivox.getConfigsPath().resolve("server");
    }
}