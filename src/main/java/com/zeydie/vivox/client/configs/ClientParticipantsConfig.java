package com.zeydie.vivox.client.configs;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zeydie.vivox.client.api.data.ClientParticipantData;
import com.zeydie.vivox.common.configs.FileConfig;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class ClientParticipantsConfig extends FileConfig {
    private final @NotNull Data data;

    public ClientParticipantsConfig(@NotNull final Path directory) {
        super(directory.resolve("participants.json").toFile());

        this.data = super.readData(Data.builder().build());
        this.save();
    }

    public void save() {
        super.save(this.data);
    }

    @lombok.Data
    @Builder
    public static class Data {
        @Builder.Default
        private @NotNull Map<String, List<ClientParticipantData>> participants = Maps.newHashMap();

        public @NotNull ClientParticipantData getParticipantData(
                @NonNull final String user,
                @NonNull final String channelName
        ) {
            @NonNull val clientParticipantData = this.participants.getOrDefault(channelName, Lists.newArrayList())
                    .stream()
                    .filter(participantData -> participantData.getUser().equals(user))
                    .findFirst()
                    .orElse(new ClientParticipantData(user, channelName, true));

            if (clientParticipantData.isDirty())
                if (this.participants.containsKey(channelName)) {
                    @NonNull val list = this.participants.get(channelName);

                    list.add(clientParticipantData);

                    this.participants.replace(channelName, list);
                } else this.participants.put(channelName, Lists.newArrayList(clientParticipantData));

            return clientParticipantData;
        }
    }
}