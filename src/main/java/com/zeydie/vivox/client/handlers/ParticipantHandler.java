package com.zeydie.vivox.client.handlers;

import com.zeydie.vivox.client.api.callbacks.IParticipantCallback;
import com.zeydie.vivox.common.Vivox;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class ParticipantHandler implements IParticipantCallback {
    private boolean speaking;

    @Override
    public void onParticipantAdded(
            @NonNull final String user,
            @NonNull final String channel,
            @NonNull final String name,
            final boolean local
    ) {
        Vivox.debug(
                "Participant added: %s\n   Channel: %s\n   Name: %s\n   Local: %b",
                user,
                channel,
                name,
                local
        );
    }

    @Override
    public void onParticipantRemoved(
            @NonNull final String user,
            @NonNull final String channel,
            @NonNull final String name,
            final boolean local
    ) {
        Vivox.debug(
                "Participant removed: %s\n   Channel: %s\n   Name: %s\n   Local: %b",
                user,
                channel,
                name,
                local
        );
    }

    @Override
    public void onParticipantUpdate(
            @NonNull final String user,
            @NonNull final String channel,
            @NonNull final String name,
            final boolean speaking,
            final double energy,
            final boolean local
    ) {
        Vivox.debug(
                "Participant update: %s\n   Channel: %s\n   Name: %s\n   Speaking: %b\n   Energy: %f\n   Local: %b",
                user,
                channel,
                name,
                speaking,
                energy,
                local
        );

        if (local)
            this.speaking = speaking;
    }
}