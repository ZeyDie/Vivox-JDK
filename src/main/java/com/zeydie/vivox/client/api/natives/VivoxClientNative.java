package com.zeydie.vivox.client.api.natives;

import com.zeydie.vivox.client.api.VivoxClientAPI;
import com.zeydie.vivox.client.api.callbacks.IStateCallbacks;
import com.zeydie.vivox.common.Vivox;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public final class VivoxClientNative {
    @Getter
    private static final @NotNull Path vivoxFolder = Paths.get("vivox");
    @Getter
    private static final @NotNull Path cacheFolder = vivoxFolder.resolve("cache");
    @Getter
    private static final @NotNull String arch = System.getProperty("os.arch");

    public static void loadLibs() {
        loadLib("vivoxsdk.dll");
        loadLib("PdxVoice.dll");
    }

    private static void loadLib(@NonNull final String fileName) {
        @NonNull final String resource = "/" + arch + "-" + fileName;

        Vivox.info("Starting lib: %s", resource);

        try (@Nullable final InputStream inputStream = VivoxClientAPI.class.getResourceAsStream(resource)) {
            if (inputStream == null)
                throw new RuntimeException("Can't find " + resource);

            @Nullable final File[] files = cacheFolder.toFile().listFiles();

            if (files != null)
                Arrays.stream(files).filter(filtered -> filtered != null && filtered.getName().endsWith("png")).forEach(File::delete);

            @NonNull final File file = vivoxFolder.resolve(fileName).toFile();

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            System.load(file.getAbsolutePath());

            Vivox.info("%s is loaded!", file.getAbsolutePath());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static native void init(
            @NonNull final String server,
            @NonNull final String user,
            @NonNull final String displayName
    );

    public static native void connect();

    public static native void login(
            @NonNull final String player,
            @NonNull final String token
    );

    public static native void setStateCallbacks(@NonNull final IStateCallbacks stateCallback);

    public static native void disconnect();

    public static native void logout();
}
