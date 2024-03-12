package com.zeydie.vivox.client.api.natives;

import com.zeydie.vivox.client.api.callbacks.IParticipantCallback;
import lombok.NonNull;

public final class VivoxClientParticipantNative {
    public static native void setParticipantCallbacks(@NonNull final IParticipantCallback participantCallback);
}