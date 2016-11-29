package com.wut.indoornavigation.data.graph;

import com.wut.indoornavigation.data.model.graph.Vertex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class VertexComparator implements Comparator<Vertex> {

    private final HeuristicFunction heuristicFunction;
    private final List<Vertex> vertices;
    private double[] distance;
    private Vertex target;

    @Inject
    public VertexComparator(HeuristicFunction heuristicFunction) {
        this.heuristicFunction = heuristicFunction;
        vertices = new ArrayList<>();
    }

    public void initialize(List<Vertex> vertices, double[] distance, Vertex target) {
        this.target = target;
        this.distance = distance;
        this.vertices.clear();
        this.vertices.addAll(vertices);
    }

    @Override
    public int compare(Vertex x, Vertex y) {
        final double xSum = heuristicFunction.execute(x, target) + distance[vertices.indexOf(x)];
        final double ySum = heuristicFunction.execute(y, target) + distance[vertices.indexOf(y)];

        return (int)(xSum - ySum);
    }
}
