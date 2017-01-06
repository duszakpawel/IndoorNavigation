package com.wut.indoornavigation.data.mesh.processingStrategy.impl;

import com.wut.indoornavigation.data.graph.Graph;
import com.wut.indoornavigation.data.model.mesh.MeshDetails;
import com.wut.indoornavigation.data.mesh.processingStrategy.ProcessingStrategy;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.graph.Vertex;

/**
 * Processing strategy for stairs sign
 */
public class StairsProcessingStrategyImpl extends ProcessingStrategy {

    @Override
    public Vertex process(Point position, MeshDetails elements, int floorNumber, Graph graph, int id) {
        final Vertex vertex = addVertexToGraph(position, graph, id);

        addVertexToCorrespondingSet(elements.getStairsVerticesDict(), floorNumber, vertex);
        addVertexToCorrespondingSet(elements.getPassageVerticesDict(), floorNumber, vertex.getPosition());

        return vertex;
    }
}
