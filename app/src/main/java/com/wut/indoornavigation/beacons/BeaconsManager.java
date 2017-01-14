package com.wut.indoornavigation.beacons;

import android.content.Context;
import android.support.annotation.NonNull;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.EstimoteSDK;
import com.estimote.sdk.Region;
import com.estimote.sdk.connection.DeviceConnection;
import com.estimote.sdk.connection.DeviceConnectionProvider;
import com.estimote.sdk.connection.internal.DeviceConnectionProviderService;
import com.estimote.sdk.connection.scanner.ConfigurableDevicesScanner;
import com.estimote.sdk.connection.scanner.DeviceType;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class BeaconsManager {

    private static final String APP_ID = "appId";
    private static final String APP_TOKEN = "appToken";

    private final Context applicationContext;

    private BeaconManager beaconManager;
    private String scanId;
    private Region region;

    @Inject
    public BeaconsManager(Context applicationContext) {
        this.applicationContext = applicationContext;
        region = new Region("Region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null );
    }

    public void initialize() {
        EstimoteSDK.initialize(applicationContext, APP_ID, APP_TOKEN);
        beaconManager = new BeaconManager(applicationContext);
    }

    public void startDiscoveringBeacons() {
        //beaconManager.connect(() -> scanId = beaconManager.startEddystoneScanning());
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }

    private void configureRangingListener(){
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    // TODO: funkcja przypisania rssi do bikonuf buildingu
                }
            }
        });
    }


    public void stopDiscoveringBeacons() {
        //beaconManager.stopEddystoneScanning(scanId);
        beaconManager.stopRanging(region);
    }

    public void setOnEddystoneFoundListener(@NonNull BeaconManager.EddystoneListener eddystoneListener) {
        beaconManager.setEddystoneListener(eddystoneListener);
    }

    public void disconnectBeaconsManager() {
        beaconManager.disconnect();
    }

}
