package com.zeydie.vivox.client.handlers;

import com.zeydie.vivox.client.api.VivoxClientAPI;
import com.zeydie.vivox.client.api.callbacks.IVivoxStateCallbacks;
import com.zeydie.vivox.common.Vivox;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class VivoxStateHandler implements IVivoxStateCallbacks {
    private int lastErrorCode;
    private String lastError;

    @Override
    public void onConnect() {
        Vivox.info("Connected to Vivox Server");
        VivoxClientAPI.setConnected(true);
    }

    @Override
    public void onLoginStateChanged(final int state) {
        Vivox.info("State: {}", state);

        if (state == 1)
            VivoxClientAPI.setLogin(true);
    }

    @Override
    public void onErrorReceived(
            final int code,
            final @NonNull String error
    ) {
        if (code != -1) {
            Vivox.error("{} {}", code, error);
            this.lastErrorCode = code;
            this.lastError = error;
        }
    }
}
