package com.zeydie.vivox.common.configs;

import com.zeydie.vivox.common.configs.base.FileConfig;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

@Data
@EqualsAndHashCode(callSuper = false)
public class VivoxConfig extends FileConfig {
    private final @NotNull Data data;

    public VivoxConfig(@NonNull final Path directory) {
        super(directory, "vivox.json");

        this.data = super.readData(
                Data
                        .builder()
                        .server("https://mt1s.www.vivox.com/api2")
                        .user("user")
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
        private @NotNull String server;
        private @NotNull String user;
    }
}