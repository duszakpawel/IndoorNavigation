package com.wut.indoornavigation.data.mesh.processingStrategy.impl;

import com.wut.indoornavigation.data.graph.Graph;
import com.wut.indoornavigation.data.mesh.MeshDetails;
import com.wut.indoornavigation.data.mesh.processingStrategy.ProcessingStrategy;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.graph.Vertex;

/**
 * Processing strategy for door sign
 */
public class DoorProcessingStrategyImpl extends ProcessingStrategy {

    @Override
    public Vertex process(Point position, MeshDetails elements, int floorNumber, Graph graph, int id) {
        Vertex v = addVertexToGraph(position, graph, id);
        addVertexToCorrespondingSet(elements.getPassageVerticesDict(), floorNumber, v.getPosition());

        return v;
    }
}