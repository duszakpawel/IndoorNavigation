package com.wut.indoornavigation.data.mesh;

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
    private final Map<Integer, List<Vertex>> destinationVerticesDict;
    private final Map<Integer, List<Vertex>> elevatorsVerticesDict;
    private final Map<Integer, List<Vertex>> stairsVerticesDict;
    private final Map<Integer, List<Point>> passageVerticesDict;
    private final Map<Integer, List<Point>> beaconsDict;

    public MeshDetails() {
        this.destinationVerticesDict = new HashMap<>();
        this.elevatorsVerticesDict = new HashMap<>();
        this.stairsVerticesDict = new HashMap<>();
        this.passageVerticesDict = new HashMap<>();
        this.beaconsDict = new HashMap<>();
    }
}