package com.zeydie.vivox.server;

import com.zeydie.vivox.common.Vivox;
import com.zeydie.vivox.server.api.VivoxServerAPI;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class VivoxServer extends Vivox {
    @Getter
    private static final VivoxServerAPI vivoxServerAPI = new VivoxServerAPI();

    @Override
    public void pre() {
        vivoxServerAPI.pre();
    }

    @Override
    public void init() {
        vivoxServerAPI.init();
    }

    @Override
    public void post() {
        vivoxServerAPI.post();
    }

    public static @NotNull Path getConfigsPath() {
        return Vivox.getConfigsPath().resolve("server");
    }
}