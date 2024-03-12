package com.zeydie.vivox.client.api;

import com.zeydie.vivox.client.api.natives.VivoxClientNative;
import com.zeydie.vivox.common.IInitialization;

public final class VivoxClientAPI implements IInitialization {
    @Override
    public void pre() {
        VivoxClientNative.loadLibs();
    }

    @Override
    public void init() {

    }

    @Override
    public void post() {

    }
}