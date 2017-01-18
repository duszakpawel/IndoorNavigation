package com.wut.indoornavigation.beacons;

import android.content.Context;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.EstimoteSDK;
import com.estimote.sdk.Region;

import com.estimote.sdk.Utils;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.data.model.IndoorBeacon;
import com.wut.indoornavigation.data.storage.BuildingStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IndoorBeaconsManager {

    private static final String APP_ID = "appId";
    private static final String APP_TOKEN = "appToken";
    private static final String ProximityUUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    private static final String REGION = "Region";

    private static final int INFINITY = 10000;
    private static final int MINIMUM_BEACON_COUNT = 2;

    private final Context applicationContext;
    private final BuildingStorage buildingStorage;
    private final BeaconManager beaconManager;

    private Region region;
    private List<IndoorBeacon> inRangeBuildingIndoorBeacons;
    private int floorNumber;

    @Inject
    IndoorBeaconsManager(Context applicationContext, BuildingStorage buildingStorage, BeaconManager beaconManager) {
        this.applicationContext = applicationContext;
        this.buildingStorage = buildingStorage;
        this.beaconManager = beaconManager;
        region = new Region(REGION, UUID.fromString(ProximityUUID), null, null);
        inRangeBuildingIndoorBeacons = new ArrayList<>();
        initialize();
    }

    /**
     * Starts monitoring beacons and start updating the data of beacons found
     */
    public void startDiscoveringBeacons() {
        beaconManager.connect(() -> beaconManager.startRanging(region));
    }

    /**
     * Stops monitoring beacons and start updating the data of beacons found
     */
    public void stopDiscoveringBeacons() {
        beaconManager.disconnect();
    }

    /**
     * Gets in range beacons
     *
     * @return beacons which are in range
     */
    public List<IndoorBeacon> getInRangeBeacons() {
        return inRangeBuildingIndoorBeacons;
    }

    /**
     * Method for getting floor number
     *
     * @return user's floorNumber
     */
    public int getFloorNumber() {
        return floorNumber;
    }

    private void initialize() {
        EstimoteSDK.initialize(applicationContext, APP_ID, APP_TOKEN);
        configureRangingListener();
    }

    private void configureRangingListener() {
        beaconManager.setRangingListener((region1, list) -> {
            if (list.size() > MINIMUM_BEACON_COUNT) {
                updateInRangeBuildingBeacons(list);
            }
        });
    }

    private void updateInRangeBuildingBeacons(List<Beacon> inRangeBeacons) {
        double nearestDistance = INFINITY;
        Floor floor;
        IndoorBeacon strongestIndoorBeacon = null, currentIndoorBeacon;

        final List<IndoorBeacon> possibleIndoorBeacons = new ArrayList<>();
        for (final Beacon inRangeBeacon : inRangeBeacons) {
            currentIndoorBeacon = findBuildingBeacon(inRangeBeacon);
            if (currentIndoorBeacon == null) {
                continue;
            }
            currentIndoorBeacon.setDistance(Utils.computeAccuracy(inRangeBeacon));

            if (currentIndoorBeacon.getDistance() < nearestDistance) {
                nearestDistance = currentIndoorBeacon.getDistance();
                strongestIndoorBeacon = currentIndoorBeacon;
            }

            possibleIndoorBeacons.add(currentIndoorBeacon);
        }
        if (strongestIndoorBeacon != null) {
            floor = findBeaconsFloor(strongestIndoorBeacon);
            if (floor != null) {
                inRangeBuildingIndoorBeacons = new ArrayList<>();
                for (final IndoorBeacon indoorBeacon : possibleIndoorBeacons) {
                    for (final IndoorBeacon floorIndoorBeacon : floor.getIndoorBeacons()) {
                        if (indoorBeacon.getId() == floorIndoorBeacon.getId())
                            inRangeBuildingIndoorBeacons.add(indoorBeacon);
                    }
                }
                floorNumber = floor.getNumber();
            }
        }
    }

    private IndoorBeacon findBuildingBeacon(Beacon inRangeBeacon) {
        for (final Floor floor : buildingStorage.getBuilding().getFloors()) {
            for (final IndoorBeacon buildingIndoorBeacon : floor.getIndoorBeacons()) {
                if (buildingIndoorBeacon.getMajor() == inRangeBeacon.getMajor()
                        && buildingIndoorBeacon.getMinor() == inRangeBeacon.getMinor())
                    return buildingIndoorBeacon;
            }
        }
        return null;
    }

    private Floor findBeaconsFloor(IndoorBeacon buildingIndoorBeacon) {
        for (final Floor floor : buildingStorage.getBuilding().getFloors()) {
            for (final IndoorBeacon indoorBeacon : floor.getIndoorBeacons()) {
                if (indoorBeacon.getMajor() == buildingIndoorBeacon.getMajor() && indoorBeacon.getMinor() == buildingIndoorBeacon.getMinor())
                    return floor;
            }
        }
        return null;
    }
}
