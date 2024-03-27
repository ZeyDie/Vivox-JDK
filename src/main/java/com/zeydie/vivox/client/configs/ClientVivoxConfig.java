package com.zeydie.vivox.client.configs;

import com.zeydie.vivox.common.configs.FileConfig;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

@Data
@EqualsAndHashCode(callSuper = false)
public class ClientVivoxConfig extends FileConfig {
    private final @NotNull Data data;

    public ClientVivoxConfig(@NonNull final Path directory) {
        super(directory.resolve("config.json").toFile());

        this.data = super.readData(
                Data
                        .builder()
                        .server("https://unity.vivox.com/appconfig/")
                        .user("90715-vivox-90002-udash")
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
        private String server;
        private String user;

        public @NotNull String getParticipant(@NonNull final String player) {
            return "." + this.user + "." + player + ".";
        }
    }
}