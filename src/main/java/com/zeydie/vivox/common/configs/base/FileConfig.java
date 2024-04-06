package com.zeydie.vivox.common.configs.base;

import com.zeydie.sgson.SGsonFile;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;

public abstract class FileConfig {
    private final @NotNull SGsonFile gsonFile;

    public FileConfig(@NonNull final Path path) {
        this(path.toFile());
    }

    public FileConfig(
            @NonNull final Path path,
            @NonNull final String file
    ) {
        this(path.resolve(file).toFile());
    }

    public FileConfig(@NonNull final File file) {
        this.gsonFile = new SGsonFile(file).setPretty();
    }

    public <O> void save(@NonNull final O object) {
        this.gsonFile.writeJsonFile(object);
    }

    public <O> @NotNull O readData(@NonNull final O object) {
        return this.gsonFile.fromJsonToObject(object);
    }
}