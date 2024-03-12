package wtf.nano.pdxvoice.lib;

import lombok.NonNull;

public class VivoxAPI {
    public static native void init(
            @NonNull final String server,
            @NonNull final String user,
            @NonNull final String displayName
    );

    public static native void connect();

    public static native void disconnect();

    public static native void login(
            @NonNull final String player,
            @NonNull final String token
    );

    public static native void logout();

    public static native void joinChannel(
            @NonNull final String channel,
            @NonNull final String sip,
            @NonNull final String token
    );

    public static native void leaveChannel(@NonNull final String channel);

    public static native void getCaptureDevices(@NonNull final ICaptureDevicesCallback captureDevicesCallback);

    public static native void getRenderDevices(@NonNull final IRenderDevices renderDevices);

    public static native void setStateCallbacks(@NonNull final IStateCallbacks stateCallback);

    public static native void setParticipantCallbacks(@NonNull final IParticipantCallback participantCallback);

    public static native void setCaptureDevice(@NonNull final String device);

    public static native void setRenderDevice(@NonNull final String device);

    public static native void setMicMuted(final boolean muted);

    public static native void setSpeakerLevel(final int level);

    public static native void setMicLevel(final int level);

    public static native void querySpeakerLevel(@NonNull final ISoundLevelCallback soundLevelCallback);

    public static native void queryMicLevel(@NonNull final ISoundLevelCallback soundLevelCallback);

    public static native void setTalkChannel(@NonNull final String channel);

    public static native void setTalkAll();

    public static native void setSelfMuted();
}