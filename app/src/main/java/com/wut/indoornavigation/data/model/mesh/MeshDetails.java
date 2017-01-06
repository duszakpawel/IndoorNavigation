package com.wut.indoornavigation.data.model.mesh;

import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.graph.Vertex;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * Contains detailed information about mesh
 * destination points, elevators, stairs as vertices map,
 * also passages and beacons as points map).
 */
@Value
@NoArgsConstructor
@AllArgsConstructor
public class MeshDetails {
    Map<Integer, List<Vertex>> destinationVerticesDict;
    Map<Integer, List<Vertex>> elevatorsVerticesDict;
    Map<Integer, List<Vertex>> stairsVerticesDict;
    Map<Integer, List<Point>> passageVerticesDict;
    Map<Integer, List<Point>> beaconsDict;
}