package com.wut.indoornavigation.data.model.mesh;

import com.wut.indoornavigation.data.graph.Graph;

import lombok.Value;

/**
 * MeshProvider result for create method
 */
@Value
public class MeshResult {
    Graph graph;
    MeshDetails meshDetails;
}
