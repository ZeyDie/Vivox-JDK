package com.zeydie.vivox.server.api;

import com.zeydie.vivox.common.interfaces.IService;
import com.zeydie.vivox.server.VivoxServer;
import com.zeydie.vivox.server.configs.ServerVivoxConfig;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public final class VivoxServerAPI implements IService {
    @Getter
    private final @NotNull ServerVivoxConfig serverVivoxConfig = new ServerVivoxConfig(VivoxServer.getConfigsPath());

    @Override
    public void pre() {

    }

    @Override
    public void init() {

    }

    @Override
    public void post() {

    }
}