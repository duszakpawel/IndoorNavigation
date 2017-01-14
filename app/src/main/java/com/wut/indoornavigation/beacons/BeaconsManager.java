package com.wut.indoornavigation.beacons;

import android.content.Context;
import android.support.annotation.NonNull;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.EstimoteSDK;
import com.estimote.sdk.Region;

import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Floor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class BeaconsManager {

    private static final String APP_ID = "appId";
    private static final String APP_TOKEN = "appToken";

    private final Context applicationContext;
    private final Building building;

    private BeaconManager beaconManager;
    private String scanId;
    private Region region;
    private List<com.wut.indoornavigation.data.model.Beacon> inRangeBuildingBeacons;
    private int floorNumber;

    @Inject
    public BeaconsManager(Context applicationContext, Building building) {
        this.applicationContext = applicationContext;
        this.building = building;
        region = new Region("Region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null );
        inRangeBuildingBeacons = new ArrayList<>();
    }

    public void initialize() {
        EstimoteSDK.initialize(applicationContext, APP_ID, APP_TOKEN);
        beaconManager = new BeaconManager(applicationContext);
        configureRangingListener();
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

    private void configureRangingListener(){
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    //  funkcja przypisania rssi do bikonuf buildingu
                    updateInRangeBuildingBeacons(list);
                }
            }
        });
    }

    private com.wut.indoornavigation.data.model.Beacon findBuildingBeacon(Beacon inRangeBeacon){
        for (Floor floor: building.getFloors()) {
            for (com.wut.indoornavigation.data.model.Beacon buildingBeacon: floor.getBeacons()) {
                if(buildingBeacon.getMajor() == inRangeBeacon.getMajor() && buildingBeacon.getMinor() == inRangeBeacon.getMinor())
                    return  buildingBeacon;
            }
        }
        return null;
    }

    private void updateInRangeBuildingBeacons(List<Beacon> inRangeBeacons) {
        int strongestSignal = 0;
        Floor floor;
        com.wut.indoornavigation.data.model.Beacon strongestBeacon = null, currentBeacon;

        List<com.wut.indoornavigation.data.model.Beacon> possibleBeacons = new ArrayList<>();
        for (Beacon inRangeBeacon : inRangeBeacons) {
            currentBeacon = findBuildingBeacon(inRangeBeacon);
            currentBeacon.setRssi(inRangeBeacon.getRssi());

            if (currentBeacon.getRssi() > strongestSignal) {
                strongestSignal = currentBeacon.getRssi();
                strongestBeacon = currentBeacon;
            }

            possibleBeacons.add(currentBeacon);
        }
        if (strongestBeacon != null) {
            floor = findBeaconsFloor(strongestBeacon);
            if (floor != null) {
                inRangeBuildingBeacons = new ArrayList<>();
                for (com.wut.indoornavigation.data.model.Beacon beacon : possibleBeacons) {
                    if ((floor.getBeacons().contains(beacon))) {
                        inRangeBuildingBeacons.add(beacon);
                    }
                }
                floorNumber = floor.getNumber();
            }
        }
    }
    private Floor findBeaconsFloor(com.wut.indoornavigation.data.model.Beacon buildingBeacon){
        for (Floor floor: building.getFloors()) {
            for (com.wut.indoornavigation.data.model.Beacon beacon: floor.getBeacons()) {
                if(beacon.getMajor() == buildingBeacon.getMajor() && beacon.getMinor() == buildingBeacon.getMinor())
                    return floor;
            }
        }
        return null;
    }
}
