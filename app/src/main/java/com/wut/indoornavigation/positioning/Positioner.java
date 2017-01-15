package com.wut.indoornavigation.positioning;

import com.wut.indoornavigation.beacons.BeaconsManager;
import com.wut.indoornavigation.data.model.Beacon;
import com.wut.indoornavigation.data.model.FloorObject;
import com.wut.indoornavigation.data.model.Point;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

public class Positioner {

    public final BeaconsManager beaconsManager;

    @Inject
    Positioner(BeaconsManager beaconsManager){
        this.beaconsManager = beaconsManager;
    }

    private Point evaluatePosition(List<Beacon> beacons, int floornum){
        float x =0, y =0, z=0, weightsum =0;

        for (Beacon beacon: beacons) {
            float w = 1/(float)(beacon.getDistance());
            weightsum += w;
            x += beacon.getX() * w;
            y += beacon.getY() * w;
        }

        x /= weightsum;
        y /= weightsum;

        z = floornum;
        x = (int)x;
        y = (int)y;
        return pointToNearestSpace(new Point(x,y,z));
    }

    public Point getUserPosition(){
        if(beaconsManager.inRangeBuildingBeacons.size() == 0)
            return new Point(0,0,beaconsManager.floorNumber);
        else
            return evaluatePosition(beaconsManager.inRangeBuildingBeacons, beaconsManager.floorNumber);
    }

    private Point pointToNearestSpace(Point point){
        int x = (int)point.getX();
        int y = (int)point.getY();
        FloorObject[][] map = beaconsManager.building.getFloors().get(beaconsManager.floorNumber).getEnumMap();
        if(map[x][y] == FloorObject.SPACE)
            return point;

        if(map[x+1][y] == FloorObject.SPACE)
            return new Point(x+1, y, point.getZ());
        if(map[x-1][y] == FloorObject.SPACE)
            return new Point(x-1, y, point.getZ());
        if(map[x][y+1] == FloorObject.SPACE)
            return new Point(x, y+1, point.getZ());
        if(map[x][y-1] == FloorObject.SPACE)
            return new Point(x, y-1, point.getZ());

        return point;
    }
}
