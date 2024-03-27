package com.zeydie.vivox.client.api.natives;

import com.zeydie.vivox.common.Vivox;
import lombok.NonNull;
import wtf.nano.pdxvoice.lib.VivoxAPI;

public final class VivoxChannelsNative {
    public static void joinChannel(
            @NonNull final String channel,
            @NonNull final String sip,
            @NonNull final String token
    ) {
        Vivox.debug("Vivox Join to channel {} {} {}", channel, sip, token);
        VivoxAPI.joinChannel(channel, sip, token);
    }

    public static void leaveChannel(@NonNull final String channel) {
        Vivox.debug("Vivox Leave from channel {}", channel);
        VivoxAPI.leaveChannel(channel);
    }

    public static void setTalkChannel(@NonNull final String channel) {
        Vivox.debug("Vivox Talk in channel {}", channel);
        VivoxAPI.setTalkChannel(channel);
    }

    public static void setTalkAll() {
        Vivox.debug("Vivox Talk in all channels");
        VivoxAPI.setTalkAll();
    }
}