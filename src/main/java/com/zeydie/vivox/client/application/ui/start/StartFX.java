package com.zeydie.vivox.client.application.ui.start;

import com.zeydie.vivox.client.VivoxClient;
import com.zeydie.vivox.client.application.VivoxClientStart;
import javafx.fxml.Initializable;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.ResourceBundle;

//TODO Create preview on JFrame
public final class StartFX implements Initializable {
    private final @NotNull VivoxClient vivoxClient = new VivoxClient();

    @Override
    public void initialize(
            @NonNull final URL location,
            @Nullable final ResourceBundle resources
    ) {
        this.vivoxClient.pre();
        this.vivoxClient.init();
        this.vivoxClient.post();

        VivoxClientStart.toScene(VivoxClientStart.Scenes.AUTH.getScene());
    }
}