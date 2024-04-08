package com.zeydie.vivox.client.api;

import com.zeydie.vivox.client.VivoxClient;
import com.zeydie.vivox.client.configs.ClientParticipantsConfig;
import com.zeydie.vivox.common.interfaces.IService;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public final class VivoxClientParticipantAPI implements IService {
    @Getter
    private static final @NotNull ClientParticipantsConfig clientParticipantsConfig = new ClientParticipantsConfig(VivoxClient.getConfigsPath());

    @Override
    public void pre() {

    }

    @Override
    public void init() {

    }

    @Override
    public void post() {
        clientParticipantsConfig.save();
    }
}