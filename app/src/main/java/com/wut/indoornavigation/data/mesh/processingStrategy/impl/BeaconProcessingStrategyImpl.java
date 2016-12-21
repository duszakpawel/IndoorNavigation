package com.wut.indoornavigation.data.mesh.processingStrategy.impl;

import com.wut.indoornavigation.data.graph.Graph;
import com.wut.indoornavigation.data.mesh.MeshDetails;
import com.wut.indoornavigation.data.mesh.processingStrategy.ProcessingStrategy;
import com.wut.indoornavigation.data.model.FloorObject;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.graph.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * Processing strategy for beacon sign
 */
public class BeaconProcessingStrategyImpl extends ProcessingStrategy {

    @Override
    public Vertex process(Point position, MeshDetails elements, int floorNumber, Graph graph, int id) {
            List<Point> floorBeacons = elements.getBeaconsDict().get(floorNumber);
            if (floorBeacons == null) {
                floorBeacons = new ArrayList<>();
                elements.getBeaconsDict().put(floorNumber, floorBeacons);
            }
            floorBeacons.add(position);

        return null;
    }
}