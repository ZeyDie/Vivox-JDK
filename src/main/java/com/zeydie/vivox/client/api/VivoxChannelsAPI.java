package com.zeydie.vivox.client.api;

import com.google.common.collect.Maps;
import com.zeydie.vivox.api.VivoxChannel;
import com.zeydie.vivox.client.api.natives.VivoxChannelsNative;
import com.zeydie.vivox.common.IService;
import com.zeydie.vivox.common.Vivox;
import lombok.NonNull;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public final class VivoxChannelsAPI implements IService {
    private static final @NotNull Map<String, VivoxChannel> vivoxChannels = Maps.newHashMap();

    @Override
    public void pre() {
        vivoxChannels.clear();
    }

    @Override
    public void init() {
    }

    @Override
    public void post() {
    }

    public static void setVivoxChannel(@NonNull final VivoxChannel vivoxChannel) {
        @NonNull val channelName = vivoxChannel.getChannelName();
        @NonNull val vivoxChannelOfMap = vivoxChannels.get(channelName);

        if (vivoxChannelOfMap != null) {
            leaveFromChannel(vivoxChannelOfMap);

            vivoxChannelOfMap.copy(vivoxChannel);
        } else vivoxChannels.put(channelName, vivoxChannel);
    }

    public static @Nullable VivoxChannel getVivoxChannel(@NonNull final String channelName) {
        return vivoxChannels.get(channelName);
    }

    public static void leaveFromAllChannels() {
        vivoxChannels.forEach((channelName, vivoxChannel) -> leaveFromChannel(channelName));
    }

    public static void leaveFromCurrentChannel() {
        @Nullable val vivoxChannel = vivoxChannels.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isConnected())
                .findFirst()
                .orElse(null);

        if (vivoxChannel == null) {
            Vivox.warning("No current vivox channel!");
            return;
        }

        leaveFromChannel(vivoxChannel.getKey());
    }

    public static void leaveFromChannel(@NonNull final VivoxChannel vivoxChannel) {
        leaveFromChannel(vivoxChannel.getChannelName());
    }

    public static void leaveFromChannel(@NonNull final String channelName) {
        Vivox.info("Leaving from channel {}...", channelName);
        VivoxChannelsNative.leaveChannel(channelName);
    }

    public static void joinToChannel(@NonNull final VivoxChannel vivoxChannel) {
        joinToChannel(vivoxChannel.getChannelName());
    }

    public static void joinToChannel(@NonNull final String channelName) {
        @Nullable val vivoxChannel = vivoxChannels.get(channelName);

        if (vivoxChannel == null) {
            Vivox.error("Can't found channel {}!", channelName);
            return;
        }
        if (vivoxChannel.isConnecting()) {
            Vivox.warning("Channel {} is connecting!", channelName);
            return;
        }
        if (vivoxChannel.isConnected()) {
            Vivox.warning("Channel {} is already connected!", channelName);
            return;
        }

        Vivox.info("Join to channel {}...", channelName);

        vivoxChannel.setStatus(VivoxChannel.Status.CONNECTING);

        VivoxChannelsNative.joinChannel(
                vivoxChannel.getParticipantChannel(),
                vivoxChannel.getSip(),
                vivoxChannel.getToken()
        );
    }
}