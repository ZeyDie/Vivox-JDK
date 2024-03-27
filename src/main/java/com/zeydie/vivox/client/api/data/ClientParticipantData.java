package com.zeydie.vivox.client.api.data;

import com.google.gson.annotations.Expose;
import com.zeydie.vivox.api.data.ParticipantData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper = false)
public class ClientParticipantData extends ParticipantData {
    private boolean banned;
    private int inputVolume;

    @Expose(deserialize = false, serialize = false)
    private transient boolean talking;
    @Expose(deserialize = false, serialize = false)
    private transient boolean nearby;
    @Expose(deserialize = false, serialize = false)
    private transient boolean dirty;

    public ClientParticipantData(
            @NonNull final String user,
            @NonNull final String channelName
    ) {
        super(user, channelName);
    }

    public ClientParticipantData(
            @NonNull final String user,
            @NonNull final String channelName,
            final boolean dirty
    ) {
        super(user, channelName);

        this.dirty = dirty;
    }
}