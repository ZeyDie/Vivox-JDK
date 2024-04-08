package com.zeydie.vivox.client.application;

import com.zeydie.vivox.client.VivoxClient;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VivoxClientStart extends Application {
    public static void main(@Nullable String[] args) {
        launch(args);
    }

    @Getter
    private static Stage stage;
    @Getter
    private static final @NotNull VivoxClient vivoxClient = new VivoxClient();

    @SneakyThrows
    @Override
    public void start(@NonNull final Stage stage) {
        this.stage = stage;
        this.stage.initStyle(StageStyle.TRANSPARENT);

        this.toScene(Scenes.AUTH.getScene());

        vivoxClient.pre();
        vivoxClient.init();
        vivoxClient.post();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        vivoxClient.close();

        Runtime.getRuntime().exit(0);
    }

    public static void toScene(@NonNull final Scene scene) {
        Platform.runLater(
                () -> {
                    scene.setFill(Color.TRANSPARENT);

                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.setResizable(false);
                    stage.show();
                }
        );
    }

    @Log4j2
    public enum Scenes {
        AUTH("auth"),
        UPDATE("update"),
        START("start");

        private final @Nullable String category;
        private final @NotNull String fx;
        private @NotNull Scene scene;

        Scenes(@NonNull final String fx) {
            this(fx, fx);
        }

        Scenes(
                @Nullable final String category,
                @NonNull final String fx
        ) {
            this.category = category;
            this.fx = fx;
        }

        public @NotNull String getPath() {
            @NonNull val fxml = this.fx + ".fxml";

            if (this.category != null)
                return this.category + "/" + fxml;

            return fxml;
        }

        @SneakyThrows
        private @NotNull <T> T getFXMLLoader() {
            @NonNull val path = "/application/ui/" + this.getPath();

            log.debug("Loading fxml {}", path);

            return FXMLLoader.load(this.getClass().getResource(path));
        }

        @SneakyThrows
        public @NotNull Scene getScene() {
            if (this.scene != null) return this.scene;

            return this.scene = new Scene(this.getFXMLLoader());
        }
    }
}