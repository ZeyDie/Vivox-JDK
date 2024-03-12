package com.zeydie.vivox.client.api.natives;

import com.zeydie.vivox.client.api.callbacks.ICaptureDevicesCallback;
import com.zeydie.vivox.client.api.callbacks.IRenderDevices;
import com.zeydie.vivox.client.api.callbacks.ISoundLevelCallback;
import lombok.NonNull;

public final class VivoxDevicesNative {
    public static native void getCaptureDevices(@NonNull final ICaptureDevicesCallback captureDevicesCallback);

    public static native void getRenderDevices(@NonNull final IRenderDevices renderDevices);

    public static native void setCaptureDevice(@NonNull final String device);

    public static native void setRenderDevice(@NonNull final String device);

    public static native void setMicMuted(final boolean muted);

    public static native void setSpeakerLevel(final int level);

    public static native void setMicLevel(final int level);

    public static native void querySpeakerLevel(@NonNull final ISoundLevelCallback soundLevelCallback);

    public static native void queryMicLevel(@NonNull final ISoundLevelCallback soundLevelCallback);

    public static native void setSelfMuted();
}