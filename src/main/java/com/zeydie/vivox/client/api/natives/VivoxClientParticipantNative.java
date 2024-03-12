package com.zeydie.vivox.client.api.natives;

import com.zeydie.vivox.client.api.callbacks.IChannelParticipantCallback;
import lombok.NonNull;
import wtf.nano.pdxvoice.lib.VivoxAPI;

public final class VivoxClientParticipantNative {
    public static void setParticipantCallbacks(@NonNull final IChannelParticipantCallback participantCallback) {
        VivoxAPI.setParticipantCallbacks(participantCallback);
    }
}