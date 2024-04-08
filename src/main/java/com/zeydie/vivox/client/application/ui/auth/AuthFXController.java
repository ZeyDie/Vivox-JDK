package com.zeydie.vivox.client.application.ui.auth;

import com.zeydie.vivox.client.VivoxClient;
import com.zeydie.vivox.client.api.VivoxClientAPI;
import com.zeydie.vivox.common.services.netty.packets.RequestPackets;
import com.zeydie.vivox.common.services.netty.utils.ByteBufUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.NonNull;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public final class AuthFXController {
    private final @NotNull Timer timer = new Timer();
    private final @NotNull List<Character> allowedCharacters = this.createAllowedCharacters();

    private @NotNull List<Character> createAllowedCharacters() {
        @NonNull final List<Character> list = new ArrayList<>();

        for (char ch = 'a'; ch <= 'z'; ch++)
            list.add(ch);
        for (char ch = 'A'; ch <= 'Z'; ch++)
            list.add(ch);
        for (int i = 0; i <= 9; i++)
            list.add((char) i);

        return list;
    }

    @FXML
    private TextField loginField;
    @FXML
    private TextField channelField;

    @FXML
    private Button authButton;

    @FXML
    private Label errorLabel;

    @FXML
    private void authButtonClick(@NonNull final ActionEvent event) {
        @NonNull val login = this.loginField.getText();
        @NonNull val channel = this.channelField.getText();

        if (login.isEmpty()) {
            this.setErrorLoginField();
            this.setErrorLabel("Вы не ввели свой логин!");

            return;
        }
        if (channel.isEmpty()) {
            this.setErrorChannelField();
            this.setErrorLabel("Вы не ввели название канала!");

            return;
        }

        for (val character : login.toCharArray())
            if (!this.allowedCharacters.contains(character)) {
                this.setErrorLoginField();
                this.setErrorLabel("Присутсвуют лишние символы в логине!");

                return;
            }
        for (val character : channel.toCharArray())
            if (!this.allowedCharacters.contains(character)) {
                this.setErrorChannelField();
                this.setErrorLabel("Присутсвуют лишние символы в названии канала!");

                return;
            }

        this.resetErrorLoginField();
        this.resetErrorChannelField();

        if (!VivoxClient.getUsername().equals(login))
            if (VivoxClientAPI.isLogin()) {
                VivoxClient.getVivoxClientAPI().close();
                VivoxClient.getVivoxClientAPI().init();
            }

        VivoxClient.setUsername(login);
        VivoxClient.setChannel(channel);

        @NonNull val byteBuf = RequestPackets.REQUEST_LOGIN.createByteBufPacket();

        ByteBufUtil.writeString(byteBuf, VivoxClient.getUsername());

        VivoxClient.getNettyClientAPI().sendPacketToServer(byteBuf);
        //TODO Test
        /*val data = VivoxServer.getVivoxServerAPI().getServerVivoxConfig().getData();
        val secretKey = data.getSecretKey();
        val serverVivoxData = ServerVivoxData
                .builder()
                .player(login)
                .user(data.getUser())
                .channelName(channel)
                .domain(data.getDomain())
                .exp(System.currentTimeMillis() / 1000 + 5 * 60 * 60)
                .build();

        if (!VivoxClientAPI.isLogin())
            this.loginVivox(login, serverVivoxData, secretKey);

        this.joinVivoxChannel(channel, serverVivoxData, secretKey);*/
    }

    //TODO Test start
    /*private void loginVivox(
            @NonNull final String login,
            @NonNull final ServerVivoxData serverVivoxData,
            @NonNull final String secretKey
    ) {
        VivoxClientNative.login(
                login,
                serverVivoxData.getLoginToken(secretKey)
        );
    }

    private void joinVivoxChannel(
            @NonNull final String channel,
            @NonNull final ServerVivoxData serverVivoxData,
            @NonNull final String secretKey
    ) {
        @NonNull val vivoxChannel = new VivoxChannel(
                new SipData(
                        serverVivoxData.getUser(),
                        channel,
                        VivoxServer.getVivoxServerAPI().getServerVivoxConfig().getData().getDomain()
                ),
                new TokenData(serverVivoxData.getJoinToken(secretKey))
        );

        VivoxChannelsAPI.setVivoxChannel(vivoxChannel);
        VivoxChannelsAPI.joinToChannel(vivoxChannel);
    }*/
    //TODO Test end

    private void setErrorLoginField() {
        this.setErrorField(this.loginField);
    }

    private void resetErrorLoginField() {
        this.resetErrorField(this.loginField);
    }

    private void setErrorChannelField() {
        this.setErrorField(this.channelField);
    }

    private void resetErrorChannelField() {
        this.resetErrorField(this.channelField);
    }

    private void setErrorField(@NonNull final TextField textField) {
        @NonNull val styleClasses = textField.getStyleClass();

        if (!styleClasses.contains("text-field-error"))
            styleClasses.add("text-field-error");

        this.timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        resetErrorLoginField();
                        resetErrorChannelField();
                        resetErrorLabel();
                    }
                },
                TimeUnit.SECONDS.toMillis(5)
        );
    }

    private void setErrorLabel(@NonNull final String text) {
        Platform.runLater(() -> this.errorLabel.setText(text));
    }

    private void resetErrorLabel() {
        this.setErrorLabel("");
    }

    private void resetErrorField(@NonNull final TextField textField) {
        textField.getStyleClass().remove("text-field-error");
    }
}