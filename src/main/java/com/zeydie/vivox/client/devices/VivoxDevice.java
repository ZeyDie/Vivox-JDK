package com.zeydie.vivox.client.devices;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public abstract class VivoxDevice {
    private @NotNull String device;
    private int volume;

    public @NotNull VivoxDevice setDevice(@NonNull final String device) {
        this.device = device;

        return this;
    }

    public @NotNull VivoxDevice setVolume(final int volume) {
        this.volume = volume;

        return this;
    }

    public abstract void init();

    public abstract void update();
}