package com.zeydie.vivox.api.data;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@RequiredArgsConstructor
public class TokenData {
    private @NonNull String token;
    private boolean dead;

    public @NotNull TokenData copy(@NonNull final TokenData tokenData) {
        this.token = tokenData.getToken();
        this.dead = tokenData.isDead();

        return this;
    }
}