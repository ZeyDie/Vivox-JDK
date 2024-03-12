package com.zeydie.vivox.client.handlers;

import com.zeydie.vivox.client.api.callbacks.IStateCallbacks;
import com.zeydie.vivox.common.Vivox;
import lombok.Getter;
import lombok.NonNull;

//@Setter
@Getter
public class VivoxStateHandler implements IStateCallbacks {
    private boolean connected;
    private boolean login;

    @Override
    public void onConnect() {
        Vivox.info("Connected to Vivox Server");
        this.connected = true;
    }

    @Override
    public void onLoginStateChanged(final int state) {
        Vivox.info("State: %d", state);
        this.login = true;
    }

    @Override
    public void onErrorReceived(
            final int code,
            final @NonNull String error
    ) {
        if (code != -1)
            Vivox.error("Error: %d %s", code, error);
    }
}
