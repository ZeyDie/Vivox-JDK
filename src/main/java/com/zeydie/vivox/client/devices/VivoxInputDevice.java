package com.zeydie.vivox.client.devices;

import com.zeydie.vivox.client.api.natives.VivoxDevicesNative;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class VivoxInputDevice extends VivoxDevice {
    private boolean banned;
    private boolean talking;
    private boolean buttonSpeaking;

    @Override
    public void init() {
        VivoxDevicesNative.setCaptureDevice(super.getDevice());
        VivoxDevicesNative.setMicLevel(super.getVolume());

        this.talking = this.buttonSpeaking;
    }

    @Override
    public void update() {
        if (this.banned && this.talking)
            this.talking = false;
        else if (!this.banned && this.buttonSpeaking)
            this.talking = true;
    }
}