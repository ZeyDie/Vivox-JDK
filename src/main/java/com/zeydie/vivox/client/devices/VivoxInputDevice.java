package com.zeydie.vivox.client.devices;

import com.zeydie.vivox.client.api.VivoxDevicesAPI;
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
    private SpeakerMode speakerMode;

    @Override
    public void init() {
        @NonNull val device = super.getDevice();
        val volume = super.getVolume();

        Vivox.info("Set input device '{}' = {}", device, volume);

        VivoxDevicesNative.setCaptureDevice(device);
        VivoxDevicesNative.setMicLevel(volume);

        this.speakerMode = VivoxDevicesAPI.getClientDevicesConfig().getData().getSpeakerMode();
        this.talking = this.speakerMode.isVoice();

        Vivox.info("Input device '{}' is talking now {}, speaker mode {}", device, this.talking, this.speakerMode);
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
        else if (!this.banned && this.speakerMode.isButton())
            this.talking = true;
    }

    public static enum SpeakerMode {
        BUTTON, VOICE;

        public boolean isButton() {
            return this == BUTTON;
        }

        public boolean isVoice() {
            return this == VOICE;
        }

        public @NotNull SpeakerMode nextMode() {
            switch (this) {
                case VOICE -> {
                    return BUTTON;
                }
                case BUTTON -> {
                    return VOICE;
                }
            }

            return BUTTON;
        }
    }
}