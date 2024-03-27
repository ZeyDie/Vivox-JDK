package wtf.nano.pdxvoice.lib;

import lombok.NonNull;

public interface ICaptureDevicesCallback {
    void onCaptureDevices(
            @NonNull final String[] names,
            @NonNull final String[] ids,
            @NonNull final String currentDevice
    );
}