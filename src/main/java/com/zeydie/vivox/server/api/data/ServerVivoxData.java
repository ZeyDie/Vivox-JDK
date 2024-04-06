package com.zeydie.vivox.server.api.data;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Data
@Builder
public class ServerVivoxData {
    @Expose(deserialize = false, serialize = false)
    private String api;

    @Expose(deserialize = false, serialize = false)
    private @NotNull String domain;

    @Expose(deserialize = false, serialize = false)
    private String channelName;

    @Expose(deserialize = false, serialize = false)
    private @NotNull String player;

    @SerializedName("iss")
    private @NotNull String user;
    @SerializedName("vxa")
    private String action;
    @SerializedName("f")
    private String from;
    @SerializedName("t")
    private String to;
    @SerializedName("exp")
    private long exp;

    public @NotNull String getParticipant() {
        return String.format(
                "sip:.%s.%s.@%s",
                this.user,
                this.player,
                this.domain
        );
    }

    public @NotNull String getLoginToken(@NonNull final String secretKey) {
        this.action = "login";
        this.from = this.getParticipant();

        return this.generateToken(secretKey, this.getJson());
    }

    public @NotNull String getJoinToken(@NonNull final String secretKey) {
        this.action = "join";
        this.from = this.getParticipant();
        this.to = String.format(
                "sip:confctl-g-%s.%s@%s",
                this.user,
                this.channelName,
                this.domain
        );

        return this.generateToken(secretKey, this.getJson());
    }

    public @NotNull String generateToken(
            @NonNull final String secretKey,
            @NonNull final String value
    ) {
        val stringBuilder = new StringBuilder();

        stringBuilder.append(this.encodeBase64("{}"));
        stringBuilder.append(".");
        stringBuilder.append(this.encodeBase64(value));

        val signature = this.encodeHmacSHA256(
                secretKey,
                stringBuilder.toString()
        );

        stringBuilder.append(".");
        stringBuilder.append(signature);

        return stringBuilder.toString();
    }

    public @NotNull String getJson() {
        return new Gson().toJson(this);
    }

    private @NotNull String encodeBase64(@NonNull final String string) {
        return this.encodeBase64(string.getBytes(StandardCharsets.UTF_8));
    }

    private @NotNull String encodeBase64(final byte[] bytes) {
        return Base64.getUrlEncoder().encodeToString(bytes)
                .replaceAll("=", "")
                .replaceAll("\\+", "-")
                .replaceAll("/", "_");
    }

    @SneakyThrows
    private @NotNull String encodeHmacSHA256(
            @NonNull final String secretKey,
            @NonNull final String string
    ) {
        val algorithm = "HmacSHA256";
        val hmacSHA256 = Mac.getInstance(algorithm);

        hmacSHA256.init(new SecretKeySpec(secretKey.getBytes(), algorithm));

        return this.encodeBase64(hmacSHA256.doFinal(string.getBytes()));
    }
}