package com.wut.indoornavigation.data.mesh.processingStrategy.impl;

import com.wut.indoornavigation.data.graph.Graph;
import com.wut.indoornavigation.data.mesh.processingStrategy.ProcessingStrategy;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.graph.Vertex;

import java.util.List;
import java.util.Map;

/**
 * Processing strategy for stairs sign
 */
public class StairsProcessingStrategyImpl extends ProcessingStrategy {

    @Override
    public Vertex process(Point position, Map<Integer, List<Vertex>> elements, int floorNumber, Graph graph, int id) {
        Vertex vertex = addVertexToGraph(position, graph, id);

        addVertexToCorrespondingSet(elements, floorNumber, vertex);

        return vertex;
    }
}
