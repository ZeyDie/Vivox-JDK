package com.zeydie.vivox.client.api.callbacks;

import lombok.NonNull;

public interface IRenderDevices {
    void onRenderDevices(
            @NonNull final String[] names,
            @NonNull final String[] ids,
            @NonNull final String currentDevice
    );
}