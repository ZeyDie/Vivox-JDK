package com.zeydie.vivox.client.api.callbacks;

import lombok.NonNull;

public interface IParticipantCallback {
    void onParticipantAdded(
            @NonNull final String user,
            @NonNull final String channel,
            @NonNull final String name,
            final boolean local
    );

    void onParticipantRemoved(
            @NonNull final String user,
            @NonNull final String channel,
            @NonNull final String name,
            final boolean local
    );

    void onParticipantUpdate(
            @NonNull final String user,
            @NonNull final String channel,
            @NonNull final String name,
            final boolean speaking,
            final double energy,
            final boolean local
    );
}