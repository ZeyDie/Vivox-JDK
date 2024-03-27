package com.zeydie.vivox.client.api;

import com.google.common.collect.Lists;
import com.zeydie.vivox.client.VivoxClient;
import com.zeydie.vivox.client.api.natives.VivoxDevicesNative;
import com.zeydie.vivox.client.configs.ClientDevicesConfig;
import com.zeydie.vivox.client.devices.VivoxDevice;
import com.zeydie.vivox.client.devices.VivoxInputDevice;
import com.zeydie.vivox.client.devices.VivoxOutputDevice;
import com.zeydie.vivox.common.IService;
import com.zeydie.vivox.common.Vivox;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public final class VivoxDevicesAPI implements IService {
    @Getter
    private static final @NotNull VivoxInputDevice inputDevice = new VivoxInputDevice();
    @Getter
    private static final @NotNull VivoxOutputDevice outputDevice = new VivoxOutputDevice();

    @Getter
    private static final int defaultInputVolume = 50;
    @Getter
    private static final int defaultOutputVolume = 50;
    @Getter
    private static final int maxInputVolume = 100;
    @Getter
    private static final int maxOutputVolume = 100;

    @Getter
    private static final @NotNull ClientDevicesConfig clientDevicesConfig = new ClientDevicesConfig(VivoxClient.getConfigsPath());

    @Override
    public void pre() {
        VivoxDevicesNative.getInputDevices(
                (names, ids, currentDevice) -> {
                    Vivox.debug("Input Devices {}", Arrays.toString(names));
                    Vivox.debug("IDs {}", Arrays.toString(ids));
                    Vivox.debug("Current input device: {}", currentDevice);
                    clientDevicesConfig.getData().setInputs(this.getSortedDevices(names));
                }
        );
        VivoxDevicesNative.getOutputDevices(
                (names, ids, currentDevice) -> {
                    Vivox.debug("Output Devices {}", Arrays.toString(names));
                    Vivox.debug("IDs {}", Arrays.toString(ids));
                    Vivox.debug("Current output device: {}", currentDevice);
                    clientDevicesConfig.getData().setOutputs(this.getSortedDevices(names));
                }
        );
    }

    @SneakyThrows
    @Override
    public void init() {
        while (!clientDevicesConfig.getData().isDevicesInitialized())
            TimeUnit.MILLISECONDS.sleep(250);

        initInputDevice();
        initOutputDevice();
    }

    @Override
    public void post() {
        clientDevicesConfig.save();
    }

    private @NotNull List<String> getSortedDevices(@NonNull final String[] names) {
        return Lists.newArrayList(names)
                .stream()
                .sorted((o1, o2) -> o1.equalsIgnoreCase("Default System Device") ? -1 : 0)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    /***
     * Input device
     * **/
    public static void initInputDevice() {
        @NonNull val data = clientDevicesConfig.getData();

        inputDevice.setDevice(data.getInputDevice());
        inputDevice.setVolume(data.getInputVolume());
        inputDevice.init();
    }

    public static void setTalking(final boolean value) {
        inputDevice.setTalking(value);
    }

    public static int getInputVolume() {
        return inputDevice.getVolume();
    }

    public static @NotNull VivoxDevice setInputVolume(final int volume) {
        Vivox.debug("Input volume {}", volume);
        return inputDevice.setVolume(volume);
    }

    /***
     * Output device
     * **/
    public static void initOutputDevice() {
        @NonNull val data = clientDevicesConfig.getData();

        outputDevice.setDevice(data.getOutputDevice());
        outputDevice.setVolume(data.getOutputVolume());
        outputDevice.init();
    }

    public static int getOutputVolume() {
        return outputDevice.getVolume();
    }

    public static @NotNull VivoxDevice setOutputVolume(final int volume) {
        Vivox.debug("Output volume {}", volume);
        return outputDevice.setVolume(volume);
    }
}