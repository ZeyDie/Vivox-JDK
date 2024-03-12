package com.zeydie.vivox.api.data;

import lombok.Data;
import lombok.NonNull;
import lombok.val;
import org.jetbrains.annotations.NotNull;

@Data
public class ParticipantData {
    private @NotNull String user;
    private @NotNull String channelName;

    public ParticipantData(
            @NonNull final String user,
            @NonNull final String channelName
    ) {
        this.user = user;
        this.channelName = channelName;
    }

    public ParticipantData(@NonNull final String participant) {
        @NonNull val userChannelName = participant.replace("confctl-g-", "").split("\\.");

        this.user = userChannelName[0];
        this.channelName = userChannelName[1];
    }

    public @NotNull ParticipantData copy(@NonNull final ParticipantData participantData) {
        this.user = participantData.getUser();
        this.channelName = participantData.getChannelName();

        return this;
    }

    public @NotNull String getParticipant() {
        return "confctl-g-" + this.user + "." + this.channelName;
    }
}