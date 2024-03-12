package com.zeydie.vivox.client.devices;

import com.zeydie.vivox.client.api.natives.VivoxDevicesNative;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class VivoxOutputDevice extends VivoxDevice {
    @Override
    public void init() {
        VivoxDevicesNative.setRenderDevice(super.getDevice());
        VivoxDevicesNative.setSpeakerLevel(super.getVolume());
    }

    @Override
    public void update() {
    }
}
