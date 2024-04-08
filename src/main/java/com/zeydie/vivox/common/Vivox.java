package com.zeydie.vivox.common;

import com.zeydie.vivox.common.configs.VivoxConfig;
import com.zeydie.vivox.common.interfaces.IServiceCloseable;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
public abstract class Vivox implements IServiceCloseable {
    private static final boolean test = true;
    private static final boolean info = true || test;
    private static final boolean debug = false || test;
    private static final boolean warning = true || test;

    @Getter
    private static final @NotNull Path vivoxPath = Paths.get("vivox");
    @Getter
    private static final @NotNull Path configsPath = vivoxPath.resolve("configs");

    @Getter
    private static final @NotNull VivoxConfig vivoxConfig = new VivoxConfig(configsPath);

    public static void info(
            @NonNull final String message,
            final Object... objects
    ) {
        if (info)
            log.info(message, objects);
    }

    public static void debug(
            @NonNull final String message,
            final Object... objects
    ) {
        if (debug)
            log.info(message, objects);
    }

    public static void warning(
            @NonNull final String message,
            final Object... objects
    ) {
        if (warning)
            log.warn(message, objects);
    }

    public static void error(
            @NonNull final String message,
            final Object... objects
    ) {
        if (warning)
            log.error(message, objects);
    }
}