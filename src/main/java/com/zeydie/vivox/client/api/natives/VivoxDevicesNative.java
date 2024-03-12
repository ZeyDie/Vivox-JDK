package com.zeydie.vivox.client.api.natives;

import com.zeydie.vivox.client.api.callbacks.IInputDevicesCallback;
import com.zeydie.vivox.client.api.callbacks.IOutputDevicesCallback;
import com.zeydie.vivox.client.api.callbacks.ISoundLevelDeviceCallback;
import lombok.NonNull;
import wtf.nano.pdxvoice.lib.VivoxAPI;

public final class VivoxDevicesNative {
    public static void getInputDevices(@NonNull final IInputDevicesCallback captureDevicesCallback) {
        VivoxAPI.getCaptureDevices(captureDevicesCallback);
    }

    public static void getOutputDevices(@NonNull final IOutputDevicesCallback renderDevices) {
        VivoxAPI.getRenderDevices(renderDevices);
    }

    public static void setCaptureDevice(@NonNull final String device) {
        VivoxAPI.setCaptureDevice(device);
    }

    public static void setRenderDevice(@NonNull final String device) {
        VivoxAPI.setRenderDevice(device);
    }

    public static void setMicMuted(final boolean muted) {
        VivoxAPI.setMicMuted(muted);
    }

    public static void setSpeakerLevel(final int level) {
        VivoxAPI.setSpeakerLevel(level);
    }

    public static void setMicLevel(final int level) {
        VivoxAPI.setMicLevel(level);
    }

    public static void querySpeakerLevel(@NonNull final ISoundLevelDeviceCallback soundLevelCallback) {
        VivoxAPI.querySpeakerLevel(soundLevelCallback);
    }

    public static void queryMicLevel(@NonNull final ISoundLevelDeviceCallback soundLevelCallback) {
        VivoxAPI.queryMicLevel(soundLevelCallback);
    }

    public static void setSelfMuted() {
        VivoxAPI.setSelfMuted();
    }
}