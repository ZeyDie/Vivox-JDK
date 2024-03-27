package com.zeydie.vivox.client.handlers;

import com.zeydie.vivox.api.VivoxChannel;
import com.zeydie.vivox.api.data.ParticipantData;
import com.zeydie.vivox.client.api.VivoxChannelsAPI;
import com.zeydie.vivox.client.api.VivoxDevicesAPI;
import com.zeydie.vivox.client.api.callbacks.IChannelParticipantCallback;
import com.zeydie.vivox.common.Vivox;
import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import org.jetbrains.annotations.Nullable;

@Getter
public class ParticipantHandler implements IChannelParticipantCallback {
    @Override
    public void onParticipantAdded(
            @NonNull final String sip,
            @NonNull final String participant,
            @NonNull final String user,
            final boolean local
    ) {
        Vivox.debug(
                "Participant added: {}\n   Channel: {}\n   User: {}\n   Local: {}",
                sip,
                participant,
                user,
                local
        );

        if (local) {
            @Nullable val vivoxChannel = VivoxChannelsAPI.getVivoxChannel(new ParticipantData(participant).getChannelName());

            if (vivoxChannel == null) {
                Vivox.warning("Can't found channel {}!", participant);
                return;
            }

            vivoxChannel.setStatus(VivoxChannel.Status.CONNECTED);
        }
    }

    @Override
    public void onParticipantRemoved(
            @NonNull final String sip,
            @NonNull final String participant,
            @NonNull final String user,
            final boolean local
    ) {
        Vivox.debug(
                "Participant removed: {}\n   Channel: {}\n   User: {}\n   Local: {}",
                sip,
                participant,
                user,
                local
        );

        if (local) {
            @Nullable val vivoxChannel = VivoxChannelsAPI.getVivoxChannel(new ParticipantData(participant).getChannelName());

            if (vivoxChannel == null) {
                Vivox.warning("Can't found channel {}!", participant);
                return;
            }

            vivoxChannel.setStatus(VivoxChannel.Status.DISCONNECTED);
        }
    }

    @Override
    public void onParticipantUpdate(
            @NonNull final String sip,
            @NonNull final String participant,
            @NonNull final String user,
            final boolean speaking,
            final double energy,
            final boolean local
    ) {
        Vivox.debug(
                "Participant update: {}\n   Channel: {}\n   User: {}\n   Speaking: {}\n   Energy: {}\n   Local: {}",
                sip,
                participant,
                user,
                speaking,
                energy,
                local
        );

        if (local)
            VivoxDevicesAPI.setTalking(speaking);
    }
}