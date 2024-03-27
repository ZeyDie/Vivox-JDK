package com.zeydie.vivox.api.data;

import lombok.Data;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

@Data
public class SipData {
    private final @NotNull ParticipantData participantData;
    private @NotNull String domain;

    public SipData(
            @NonNull final String user,
            @NonNull final String channelName,
            @NonNull final String domain
    ) {
        this(new ParticipantData(user, channelName), domain);
    }

    public SipData(
            @NonNull final ParticipantData participantData,
            @NonNull final String domain
    ) {
        this.participantData = participantData;
        this.domain = domain;
    }

    public @NotNull SipData copy(@NonNull final SipData sipData) {
        this.participantData.copy(sipData.getParticipantData());
        this.domain = sipData.getDomain();

        return this;
    }

    public @NonNull String getSip() {
        return "sip:" + this.participantData.getParticipant() + "@" + this.domain;
    }
}