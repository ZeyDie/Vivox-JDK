package wtf.nano.pdxvoice.lib;

import lombok.NonNull;

public interface IParticipantCallback {
    void onParticipantAdded(
            @NonNull final String sip,
            @NonNull final String participant,
            @NonNull final String user,
            final boolean local
    );

    void onParticipantRemoved(
            @NonNull final String sip,
            @NonNull final String participant,
            @NonNull final String user,
            final boolean local
    );

    void onParticipantUpdate(
            @NonNull final String sip,
            @NonNull final String participant,
            @NonNull final String user,
            final boolean speaking,
            final double energy,
            final boolean local
    );
}