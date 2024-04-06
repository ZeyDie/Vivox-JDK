package com.zeydie.vivox.server.configs;

import com.zeydie.vivox.common.configs.base.FileConfig;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

@Getter
public class ServerVivoxConfig extends FileConfig {
    private final @NotNull Data data;

    public ServerVivoxConfig(@NonNull final Path directory) {
        super(directory, "config.json");

        this.data = super.readData(
                Data
                        .builder()
                        .domain("mt1s.vivox.com")
                        .channel("global")
                        .user("user")
                        .secretKey("secretKey")
                        .build()
        );
        this.save();
    }

    public void save() {
        this.save(this.data);
    }

    @lombok.Data
    @Builder
    public static class Data {
        private @NotNull String domain;
        private @NotNull String channel;
        private @NotNull String user;
        private @NotNull String secretKey;
    }
}