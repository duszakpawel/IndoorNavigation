package com.wut.indoornavigation.data.model.mesh;

import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.graph.Vertex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Value;

/**
 * Contains detailed information about mesh
 * destination points, elevators, stairs as vertices map,
 * also passages and beacons as points map).
 */
@Value
public class MeshDetails {
    Map<Integer, List<Vertex>> destinationVerticesDict;
    Map<Integer, List<Vertex>> elevatorsVerticesDict;
    Map<Integer, List<Vertex>> stairsVerticesDict;
    Map<Integer, List<Point>> passageVerticesDict;
    Map<Integer, List<Point>> beaconsDict;

    public MeshDetails() {
        destinationVerticesDict = new HashMap<>();
        elevatorsVerticesDict = new HashMap<>();
        stairsVerticesDict = new HashMap<>();
        passageVerticesDict = new HashMap<>();
        beaconsDict = new HashMap<>();
    }
}