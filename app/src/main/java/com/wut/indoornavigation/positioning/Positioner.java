package com.wut.indoornavigation.positioning;

import com.wut.indoornavigation.beacons.BeaconsManager;
import com.wut.indoornavigation.data.model.Beacon;
import com.wut.indoornavigation.data.model.FloorObject;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.storage.BuildingStorage;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class for evaluating user's current position
 */
@Singleton
public class Positioner {

    private final BeaconsManager beaconsManager;
    private final BuildingStorage buildingStorage;

    @Inject
    Positioner(BeaconsManager beaconsManager, BuildingStorage buildingStorage) {
        this.beaconsManager = beaconsManager;
        this.buildingStorage = buildingStorage;
    }

    /**
     * Method for getting user position
     *
     * @return user's current position based on information from beacons in range; if no beacons in range, returns point (0, 0, floorNumber)
     */
    public Point getUserPosition() {
        if (beaconsManager.getInRangeBeacons().isEmpty()) {
            return new Point(0, 0, beaconsManager.getFloorNumber());
        } else {
            return evaluatePosition(beaconsManager.getInRangeBeacons(), beaconsManager.getFloorNumber());
        }
    }

    private Point evaluatePosition(List<Beacon> beacons, int floorNumber) {
        float x = 0, y = 0, weightSum = 0;

        for (final Beacon beacon : beacons) {
            final float w = 1 / (float) (beacon.getDistance());
            weightSum += w;
            x += beacon.getX() * w;
            y += beacon.getY() * w;
        }

        x /= weightSum;
        y /= weightSum;
        return pointToNearestSpace(new Point((int) x, (int) y, floorNumber));
    }

    private Point pointToNearestSpace(Point point) {
        final int x = (int) point.getX();
        final int y = (int) point.getY();
        final FloorObject[][] map = buildingStorage.getBuilding().getFloors().get(beaconsManager.getFloorNumber()).getEnumMap();
        if (map[x][y] == FloorObject.SPACE)
            return point;

        if (map[Math.min(x + 1, map.length)][y] == FloorObject.SPACE)
            return new Point(Math.min(x + 1, map.length), y, point.getZ());
        if (map[Math.max(x - 1, 0)][y] == FloorObject.SPACE)
            return new Point(Math.max(x - 1, 0), y, point.getZ());
        if (map[x][Math.min(y + 1, map[x].length)] == FloorObject.SPACE)
            return new Point(x, Math.min(y + 1, map[x].length), point.getZ());
        if (map[x][Math.max(y - 1, 0)] == FloorObject.SPACE)
            return new Point(x, Math.max(y - 1, 0), point.getZ());

        return point;
    }
}
