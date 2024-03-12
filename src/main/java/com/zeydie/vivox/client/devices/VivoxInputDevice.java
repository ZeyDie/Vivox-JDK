package com.zeydie.vivox.client.devices;

import com.zeydie.vivox.client.api.natives.VivoxDevicesNative;
import com.zeydie.vivox.common.Vivox;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.val;
import org.jetbrains.annotations.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
public class VivoxInputDevice extends VivoxDevice {
    private boolean banned;
    private boolean talking;
    private boolean buttonSpeaking;

    @Override
    public void init() {
        @NonNull val device = super.getDevice();
        val volume = super.getVolume();

        Vivox.info("Set input device %s = %d", device, volume);

        VivoxDevicesNative.setCaptureDevice(device);
        VivoxDevicesNative.setMicLevel(volume);

        this.talking = this.buttonSpeaking;

        Vivox.info("Input device %s is talking now ", device, this.talking);
    }

    public @NotNull VivoxInputDevice setTalking(final boolean value) {
        if (!this.banned)
            this.talking = value;
        else if (!value)
            this.talking = false;

        return this;
    }

    @Override
    public void update() {
        if (this.banned && this.talking)
            this.talking = false;
        else if (!this.banned && this.buttonSpeaking)
            this.talking = true;
    }
}