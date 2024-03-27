package com.zeydie.vivox.client.api.natives;

import com.zeydie.vivox.client.api.VivoxClientAPI;
import com.zeydie.vivox.client.api.callbacks.IVivoxStateCallbacks;
import com.zeydie.vivox.common.Vivox;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.nano.pdxvoice.lib.VivoxAPI;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

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
        @NonNull val resource = "/" + arch + "-" + fileName;

        try (@Nullable val inputStream = VivoxClientAPI.class.getResourceAsStream(resource)) {
            Vivox.info("Loading lib {}", resource);

            if (inputStream == null)
                throw new RuntimeException("Can't find " + resource);

            @Nullable val files = cacheFolder.toFile().listFiles();

            if (files != null)
                Arrays.stream(files)
                        .filter(filtered -> filtered != null && filtered.getName().endsWith("png"))
                        .forEach(File::delete);

            @NonNull val file = vivoxFolder.resolve(fileName).toFile();

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            @NonNull val absolutePath = file.getAbsolutePath();

            System.load(absolutePath);

            Vivox.info("{} is loaded!", absolutePath);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void init(
            @NonNull final String server,
            @NonNull final String user,
            @NonNull final String displayName
    ) {
        Vivox.debug("Vivox Init {} {} {}", server, user, displayName);
        VivoxAPI.init(server, user, displayName);
    }

    public static void connect() {
        Vivox.info("Vivox Connecting");
        VivoxAPI.connect();
    }

    @SneakyThrows
    public static void login(
            @NonNull final String player,
            @NonNull final String token
    ) {
        Vivox.debug("Vivox Login {} {}", player, token);
        VivoxAPI.login(player, token);

        while (!VivoxClientAPI.isLogin())
            TimeUnit.MILLISECONDS.sleep(200);
    }

    public static void setStateCallbacks(@NonNull final IVivoxStateCallbacks stateCallback) {
        VivoxAPI.setStateCallbacks(stateCallback);
    }

    public static void disconnect() {
        Vivox.info("Vivox Disconnect");
        VivoxAPI.disconnect();
    }

    public static void logout() {
        Vivox.info("Vivox Logout");
        VivoxAPI.logout();
    }
}
