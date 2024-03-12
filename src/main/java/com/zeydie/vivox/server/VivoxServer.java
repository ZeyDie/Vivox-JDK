package com.zeydie.vivox.server;

import com.zeydie.vivox.common.Vivox;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class VivoxServer extends Vivox {
    @Override
    public void pre() {
    }

    @Override
    public void init() {
    }

    @Override
    public void post() {
    }

    public static @NotNull Path getConfigsPath() {
        return Vivox.getConfigsPath().resolve("server");
    }
}