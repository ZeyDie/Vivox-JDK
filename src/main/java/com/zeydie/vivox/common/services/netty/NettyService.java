package com.zeydie.vivox.common.services.netty;

import com.zeydie.vivox.common.interfaces.IServiceCloseable;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public abstract class NettyService implements IServiceCloseable {
    @Getter
    private static final @NotNull String serverAddress = "localhost";
    @Getter
    private static final int serverPort = 1337;
    @Getter
    private static final int serverThreads = 4;

    @Getter
    private static final int clientThreads = 1;
}