package com.zeydie.vivox.client.devices;

import com.zeydie.vivox.client.api.natives.VivoxDevicesNative;
import com.zeydie.vivox.common.Vivox;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.val;

@Data
@EqualsAndHashCode(callSuper = false)
public class VivoxOutputDevice extends VivoxDevice {
    @Override
    public void init() {
        @NonNull val device = super.getDevice();
        val volume = super.getVolume();

        Vivox.info("Set output device '{}' = {}", device, volume);

        VivoxDevicesNative.setRenderDevice(device);
        VivoxDevicesNative.setSpeakerLevel(volume);
    }

    @Override
    public void update() {
    }
}
