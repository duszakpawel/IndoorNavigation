package com.wut.indoornavigation.data.mesh.processingStrategy.impl;

import com.wut.indoornavigation.data.graph.Graph;
import com.wut.indoornavigation.data.mesh.processingStrategy.ProcessingStrategy;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.graph.Vertex;

import java.util.List;
import java.util.Map;

/**
 * Processing strategy for space sign
 */
public class SpaceProcessingStrategyImpl extends ProcessingStrategy {

    @Override
    public Vertex process(Point position, Map<Integer, List<Vertex>> elements, int floorNumber, Graph graph, int id) {
        return addVertexToGraph(position, graph, id);
    }
}