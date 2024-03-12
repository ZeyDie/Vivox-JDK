package com.zeydie.vivox.client.api.natives;

import lombok.NonNull;

public final class VivoxChannelsNative {
    public static native void joinChannel(
            @NonNull final String channel,
            @NonNull final String sip,
            @NonNull final String token
    );

    public static native void leaveChannel(@NonNull final String channel);

    public static native void setTalkChannel(@NonNull final String channel);

    public static native void setTalkAll();
}