package com.zeydie.vivox.common.services.netty;

import com.zeydie.vivox.common.interfaces.IServiceCloseable;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

@Log4j2
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