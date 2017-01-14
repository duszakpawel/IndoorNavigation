package com.wut.indoornavigation.positioning;

import com.wut.indoornavigation.beacons.BeaconsManager;
import com.wut.indoornavigation.data.model.Beacon;
import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.data.model.Point;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by sa on 14.01.2017.
 */

public class Positioner {

    private final BeaconsManager beaconsManager;

    List<Beacon> inRangeBeacons;

    @Inject
    Positioner(BeaconsManager beaconsManager){
        this.beaconsManager = beaconsManager;
        inRangeBeacons = new ArrayList<>();
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

        z = floornum; // nie wykrywa pietra, wiec przypisane takie jakie jest

        return new Point(x,y,z);
    }

    public Point getUserPosition(int floorNum){
        if(inRangeBeacons.size() == 0)
            return new Point(0,0,0);
        else
            return evaluatePosition(inRangeBeacons, floorNum);
    }
}
