package com.wut.indoornavigation.positioning;

import com.wut.indoornavigation.beacons.BeaconsManager;
import com.wut.indoornavigation.data.model.Beacon;
import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.data.model.Point;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by sa on 14.01.2017.
 */

public class Positioner {

    private final BeaconsManager beaconsManager;
    private final Building building;

    @Inject
    Positioner(BeaconsManager beaconsManager, Building building){
        this.beaconsManager = beaconsManager;
        this.building = building;
    }

    private Point evaluatePosition(List<Beacon> beacons, Floor floor){
        float x =0, y =0, z=0, weightsum =0;

        for (Beacon beacon: beacons) {
            float w = 1/(float)(Math.sqrt((beacon.getRssi())));
            weightsum += w;
            x += beacon.getX() * w;
            y += beacon.getY() * w;
        }

        x /= weightsum;
        y /= weightsum;

        z = floor.getNumber(); // nie wykrywa pietra, wiec przypisane takie jakie jest

        return new Point(x,y,z);
    }
}
