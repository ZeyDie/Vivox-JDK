package com.zeydie.vivox.client.configs;

import com.google.common.collect.Lists;
import com.zeydie.vivox.client.api.VivoxDevicesAPI;
import com.zeydie.vivox.common.configs.FileConfig;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ClientDevicesConfig extends FileConfig {
    private final @NotNull Data data;

    public ClientDevicesConfig(@NonNull final Path directory) {
        super(directory.resolve("devices.json").toFile());

        this.data = super.readData(
                Data
                        .builder()
                        .inputs(Lists.newArrayList())
                        .outputs(Lists.newArrayList())
                        .build()
        );
        this.save();
    }

    public void save() {
        this.save(this.data);
    }

    @lombok.Data
    @Builder
    public static class Data {
        private int inputId;
        private int outputId;
        private int inputVolume;
        private int outputVolume;
        private boolean buttonSpeaking;

        private @NotNull List<String> inputs;
        private @NotNull List<String> outputs;

        public @NotNull Data nextInputId() {
            this.inputId += 1;

            if (this.inputId >= this.inputs.size())
                this.inputId = 0;

            return this;
        }

        public @NotNull Data nextOutputId() {
            this.outputId += 1;

            if (this.outputId >= this.outputs.size())
                this.outputId = 0;

            return this;
        }

        public @NotNull Data switchButtonSpeaking() {
            this.buttonSpeaking = !this.buttonSpeaking;

            return this;
        }

        public @NotNull Data setInputVolume(final int volume) {
            this.inputVolume = volume;

            return this;
        }

        public @NotNull Data setOutputVolume(final int volume) {
            this.outputVolume = volume;

            return this;
        }

        public @NotNull Data setInputs(@NonNull final List<String> inputs) {
            this.inputs = inputs;

            return this;
        }

        public @NotNull Data setOutputs(@NonNull final List<String> outputs) {
            this.outputs = outputs;

            return this;
        }

        public @NotNull String getInputDevice() {
            if (this.inputId > this.inputs.size())
                this.inputId = 0;

            return this.inputs.get(this.inputId);
        }

        public @NotNull String getOutputDevice() {
            if (this.outputId > this.outputs.size())
                this.outputId = 0;

            return this.outputs.get(this.outputId);
        }

        public void save() {
            VivoxDevicesAPI.getClientDevicesConfig().save();
        }
    }
}