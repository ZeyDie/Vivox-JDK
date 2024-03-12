package com.zeydie.vivox.client.api;

import com.google.common.collect.Lists;
import com.zeydie.vivox.client.VivoxClient;
import com.zeydie.vivox.client.api.natives.VivoxDevicesNative;
import com.zeydie.vivox.client.configs.ClientDevicesConfig;
import com.zeydie.vivox.client.devices.VivoxDevice;
import com.zeydie.vivox.client.devices.VivoxInputDevice;
import com.zeydie.vivox.client.devices.VivoxOutputDevice;
import com.zeydie.vivox.common.IInitialization;
import com.zeydie.vivox.common.Vivox;
import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class VivoxDevicesAPI implements IInitialization {
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
    private static final ClientDevicesConfig clientDevicesConfig = new ClientDevicesConfig(VivoxClient.getConfigsPath());

    @Override
    public void pre() {
        VivoxDevicesNative.getCaptureDevices(
                (names, ids, currentDevice) -> {
                    Vivox.info("Current input device " + currentDevice);

                    this.getSortedDevices(names);
                    //TODO Set devices to config
                }
        );
        VivoxDevicesNative.getRenderDevices(
                (names, ids, currentDevice) -> {
                    Vivox.info("Current output device " + currentDevice);

                    this.getSortedDevices(names);
                    //TODO Set devices to config
                }
        );
    }

    @Override
    public void init() {
        initInputDevice();
        initOutputDevice();
    }

    @Override
    public void post() {

    }

    private @NotNull List<String> getSortedDevices(@NonNull final String[] names) {
        return Lists.newArrayList(names)
                .stream()
                .sorted((o1, o2) -> o1.equalsIgnoreCase("Default System Device") ? -1 : 0)
                .filter(s -> !s.isEmpty())
                .toList();
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
        Vivox.debug("Input volume %d", volume);
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
        Vivox.debug("Output volume %d", volume);
        return outputDevice.setVolume(volume);
    }
}