package com.zeydie.vivox.client.application;

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
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VivoxClientStart extends Application {
    public static void main(@Nullable String[] args) {
        launch(args);
    }

    @Getter
    private static Stage stage;

    @SneakyThrows
    @Override
    public void start(@NonNull final Stage stage) {
        this.stage = stage;
        this.stage.initStyle(StageStyle.TRANSPARENT);

        this.toScene(Scenes.AUTH.getScene());
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

    public enum Scenes {
        AUTH("auth"),
        UPDATE("update"),
        START("start");

        private final @Nullable String category;
        private final @NotNull String fx;

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
        public @NotNull <T> T getFXMLLoader() {
            return FXMLLoader.load(this.getClass().getResource("/application/ui/" + this.getPath()));
        }

        @SneakyThrows
        public @NotNull Scene getScene() {
            return new Scene(this.getFXMLLoader());
        }
    }
}