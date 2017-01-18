package com.wut.indoornavigation.beacons;

import android.content.Context;
import android.support.annotation.NonNull;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.EstimoteSDK;
import com.estimote.sdk.Region;

import com.estimote.sdk.Utils;
import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.data.storage.BuildingStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class BeaconsManager {

    private static final String APP_ID = "appId";
    private static final String APP_TOKEN = "appToken";
    private static final int INFINITY = 10000;
    private static final String ProximityUUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    private  static  final int MINIMUM_BEACON_COUNT = 2;

    private final Context applicationContext;
    private final BuildingStorage buildingStorage;

    private BeaconManager beaconManager;
    private Region region;
    private List<com.wut.indoornavigation.data.model.Beacon> inRangeBuildingBeacons;
    private int floorNumber;

    @Inject
    BeaconsManager(Context applicationContext, BuildingStorage buildingStorage) {
        this.applicationContext = applicationContext;
        this.buildingStorage = buildingStorage;
        region = new Region("Region", UUID.fromString(ProximityUUID), null, null);
        inRangeBuildingBeacons = new ArrayList<>();
        initialize();
    }

    private void initialize() {
        EstimoteSDK.initialize(applicationContext, APP_ID, APP_TOKEN);
        beaconManager = new BeaconManager(applicationContext);
        configureRangingListener();
    }

    /**
     * starts monitoring beacons and start updating the data of beacons found
     */
    public void startDiscoveringBeacons() {
        beaconManager.connect(() -> beaconManager.startRanging(region));
    }

    private void configureRangingListener() {
        beaconManager.setRangingListener((region1, list) -> {
            if (list.size() > MINIMUM_BEACON_COUNT) {
                updateInRangeBuildingBeacons(list);
            }
        });
    }

    private com.wut.indoornavigation.data.model.Beacon findBuildingBeacon(Beacon inRangeBeacon) {
        for (Floor floor : buildingStorage.getBuilding().getFloors()) {
            for (com.wut.indoornavigation.data.model.Beacon buildingBeacon : floor.getBeacons()) {
                if (buildingBeacon.getMajor() == inRangeBeacon.getMajor() && buildingBeacon.getMinor() == inRangeBeacon.getMinor())
                    return buildingBeacon;
            }
        }
        return null;
    }

    private void updateInRangeBuildingBeacons(List<Beacon> inRangeBeacons) {
        double nearestDistance = INFINITY;
        Floor floor;
        com.wut.indoornavigation.data.model.Beacon strongestBeacon = null, currentBeacon;

        List<com.wut.indoornavigation.data.model.Beacon> possibleBeacons = new ArrayList<>();
        for (Beacon inRangeBeacon : inRangeBeacons) {
            currentBeacon = findBuildingBeacon(inRangeBeacon);
            if (currentBeacon == null) {
                continue;
            }
            currentBeacon.setDistance(Utils.computeAccuracy(inRangeBeacon));

            if (currentBeacon.getDistance() < nearestDistance) {
                nearestDistance = currentBeacon.getDistance();
                strongestBeacon = currentBeacon;
            }

            possibleBeacons.add(currentBeacon);
        }
        if (strongestBeacon != null) {
            floor = findBeaconsFloor(strongestBeacon);
            if (floor != null) {
                inRangeBuildingBeacons = new ArrayList<>();
                for (com.wut.indoornavigation.data.model.Beacon beacon : possibleBeacons) {
                    for (com.wut.indoornavigation.data.model.Beacon floorBeacon:floor.getBeacons()) {
                        if(beacon.getId() == floorBeacon.getId())
                            inRangeBuildingBeacons.add(beacon);
                    }

                }
                floorNumber = floor.getNumber();
            }
        }
    }

    private Floor findBeaconsFloor(com.wut.indoornavigation.data.model.Beacon buildingBeacon) {
        for (Floor floor : buildingStorage.getBuilding().getFloors()) {
            for (com.wut.indoornavigation.data.model.Beacon beacon : floor.getBeacons()) {
                if (beacon.getMajor() == buildingBeacon.getMajor() && beacon.getMinor() == buildingBeacon.getMinor())
                    return floor;
            }
        }
        return null;
    }

    /**
     *
     * @return beacons in range
     */
    public List<com.wut.indoornavigation.data.model.Beacon> getInRangeBeacons(){
        return inRangeBuildingBeacons;
    }

    /**
     *
     * @return user's floorNumber
     */
    public int getFloorNumber(){
        return floorNumber;
    }
}
