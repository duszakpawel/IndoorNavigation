package com.wut.indoornavigation.data.model.mesh;

import com.wut.indoornavigation.data.graph.Graph;
import com.wut.indoornavigation.data.model.graph.Vertex;

import java.util.List;
import java.util.Map;

import lombok.Value;

@Value
public class MeshResult {
    Graph graph;
    Map<Integer, List<Vertex>> destinationPoints;
}
