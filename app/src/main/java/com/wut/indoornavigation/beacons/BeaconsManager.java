package com.wut.indoornavigation.beacons;

import android.content.Context;
import android.support.annotation.NonNull;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.EstimoteSDK;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class BeaconsManager {

    private static final String APP_ID = "appId";
    private static final String APP_TOKEN = "appToken";

    private final Context applicationContext;

    private BeaconManager beaconManager;
    private String scanId;

    @Inject
    public BeaconsManager(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void initialize() {
        EstimoteSDK.initialize(applicationContext, APP_ID, APP_TOKEN);
        beaconManager = new BeaconManager(applicationContext);
    }

    public void startDiscoveringBeacons() {
        beaconManager.connect(() -> scanId = beaconManager.startEddystoneScanning());
    }

    public void stopDiscoveringBeacons() {
        beaconManager.stopEddystoneScanning(scanId);
    }

    public void setOnEddystoneFoundListener(@NonNull BeaconManager.EddystoneListener eddystoneListener) {
        beaconManager.setEddystoneListener(eddystoneListener);
    }

    public void disconnectBeaconsManager() {
        beaconManager.disconnect();
    }
}
