package com.zeydie.vivox.common;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.nio.file.Paths;

@Log
public abstract class Vivox implements IInitialization {
    private static final boolean test = true;
    private static final boolean info = true || test;
    private static final boolean debug = false || test;
    private static final boolean warning = true || test;

    @Getter
    private static final @NotNull Path vivoxPath = Paths.get("vivox");
    @Getter
    private static final @NotNull Path configsPath = vivoxPath.resolve("configs");

    public static void info(
            @NonNull final String message,
            final Object... objects
    ) {
        if (info)
            log.info(String.format(message, objects));
    }

    public static void debug(
            @NonNull final String message,
            final Object... objects
    ) {
        if (debug)
            log.info(String.format(message, objects));
    }

    public static void warning(
            @NonNull final String message,
            final Object... objects
    ) {
        if (warning)
            log.warning(String.format(message, objects));
    }

    public static void error(
            @NonNull final String message,
            final Object... objects
    ) {
        if (warning)
            log.fine(String.format(message, objects));
    }
}