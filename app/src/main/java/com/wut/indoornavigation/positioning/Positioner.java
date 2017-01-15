package com.wut.indoornavigation.positioning;

import com.wut.indoornavigation.beacons.BeaconsManager;
import com.wut.indoornavigation.data.model.Beacon;
import com.wut.indoornavigation.data.model.FloorObject;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.storage.BuildingStorage;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Positioner {

    public final BeaconsManager beaconsManager;
    private final BuildingStorage buildingStorage;

    @Inject
    Positioner(BeaconsManager beaconsManager, BuildingStorage buildingStorage) {
        this.beaconsManager = beaconsManager;
        this.buildingStorage = buildingStorage;
    }

    private Point evaluatePosition(List<Beacon> beacons, int floornum) {
        float x = 0, y = 0, z = 0, weightsum = 0;

        for (Beacon beacon : beacons) {
            float w = 1 / (float) (beacon.getDistance());
            weightsum += w;
            x += beacon.getX() * w;
            y += beacon.getY() * w;
        }

        x /= weightsum;
        y /= weightsum;

        z = floornum;
        x = (int) x;
        y = (int) y;
        return pointToNearestSpace(new Point(x, y, z));
    }

    public Point getUserPosition() {
        if (beaconsManager.inRangeBuildingBeacons.size() == 0)
            return new Point(0, 0, beaconsManager.floorNumber);
        else
            return evaluatePosition(beaconsManager.inRangeBuildingBeacons, beaconsManager.floorNumber);
    }

    private Point pointToNearestSpace(Point point) {
        int x = (int) point.getX();
        int y = (int) point.getY();
        FloorObject[][] map = buildingStorage.getBuilding().getFloors().get(beaconsManager.floorNumber).getEnumMap();
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
