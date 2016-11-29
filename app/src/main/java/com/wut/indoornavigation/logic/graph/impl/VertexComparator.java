package com.wut.indoornavigation.logic.graph.impl;

import com.wut.indoornavigation.logic.graph.models.Vertex;

import java.util.Comparator;
import java.util.List;

class VertexComparator implements Comparator<Vertex> {
    private final HeuristicFunction heuristicFunction;
    private final List<Vertex> vertices;
    private final double[] distance;
    private final Vertex target;

    VertexComparator(HeuristicFunction heuristicFunction, List<Vertex> vertices, double[] distance, Vertex target)
    {
        this.heuristicFunction = heuristicFunction;
        this.vertices = vertices;
        this.distance = distance;
        this.target = target;
    }

    @Override
    public int compare(Vertex x, Vertex y)
    {
        double xSum = heuristicFunction.execute(x, target) + distance[vertices.indexOf(x)];
        double ySum = heuristicFunction.execute(y, target) + distance[vertices.indexOf(y)];
        return (int)(xSum - ySum);
    }
}
