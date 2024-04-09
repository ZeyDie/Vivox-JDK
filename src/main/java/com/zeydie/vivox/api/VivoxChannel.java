package com.zeydie.vivox.api;

import com.zeydie.vivox.api.data.SipData;
import com.zeydie.vivox.api.data.TokenData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

@Data
@EqualsAndHashCode
public class VivoxChannel {
    private @NotNull Status status = Status.DISCONNECTED;
    private final @NonNull SipData sipData;
    private final @NonNull TokenData tokenData;

    public VivoxChannel(
            @NonNull final SipData sipData,
            @NonNull final TokenData tokenData
    ) {
        this.sipData = sipData;
        this.tokenData = tokenData;
    }

    public @NotNull VivoxChannel copy(@NonNull final VivoxChannel vivoxChannel) {
        this.sipData.copy(vivoxChannel.getSipData());
        this.tokenData.copy(vivoxChannel.getTokenData());

        return this;
    }

    public @NotNull String getChannelName() {
        return this.sipData.getParticipantData().getChannelName();
    }

    public @NotNull String getParticipantChannel() {
        return this.sipData.getParticipantData().getParticipant();
    }

    public @NotNull String getSip() {
        return this.sipData.getSip();
    }

    public @NotNull String getToken() {
        return this.tokenData.getToken();
    }

    public boolean isConnecting() {
        return this.status == Status.CONNECTING;
    }

    public boolean isConnected() {
        return this.status == Status.CONNECTED;
    }

    public boolean isDisconnected() {
        return this.status == Status.DISCONNECTED;
    }

    public boolean equals(@NonNull final VivoxChannel vivoxChannel) {
        return this.getChannelName().equals(vivoxChannel.getChannelName());
    }

    public enum Status {
        CONNECTING, CONNECTED, DISCONNECTED;
    }
}
