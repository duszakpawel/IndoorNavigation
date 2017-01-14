package com.wut.indoornavigation.positioning;

import com.wut.indoornavigation.beacons.BeaconsManager;
import com.wut.indoornavigation.data.model.Beacon;
import com.wut.indoornavigation.data.model.Point;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Positioner {

    private final BeaconsManager beaconsManager;

    @Inject
    Positioner(BeaconsManager beaconsManager){
        this.beaconsManager = beaconsManager;
    }

    private Point evaluatePosition(List<Beacon> beacons, int floornum){
        float x =0, y =0, z=0, weightsum =0;

        for (Beacon beacon: beacons) {
            float w = 1/(float)(Math.sqrt((beacon.getRssi())));
            weightsum += w;
            x += beacon.getX() * w;
            y += beacon.getY() * w;
        }

        x /= weightsum;
        y /= weightsum;

        z = floornum;

        return new Point(x,y,z);
    }

    public Point getUserPosition(){
        if(beaconsManager.inRangeBuildingBeacons.size() == 0)
            return new Point(0,0,0);
        else
            return evaluatePosition(beaconsManager.inRangeBuildingBeacons, beaconsManager.floorNumber);
    }
}
