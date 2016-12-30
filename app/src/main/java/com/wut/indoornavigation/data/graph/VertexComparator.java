package com.wut.indoornavigation.data.graph;

import com.wut.indoornavigation.data.model.graph.Vertex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Vertex comparator class for aStar implementation
 */
@Singleton
public class VertexComparator implements Comparator<Integer> {

    private final HeuristicFunction heuristicFunction;
    private final List<Vertex> vertices;
    private double[] distance;
    private Vertex target;

    /**
     * Vertex comparator constructor
     * @param heuristicFunction heuristic function object
     */
    @Inject
    public VertexComparator(HeuristicFunction heuristicFunction) {
        this.heuristicFunction = heuristicFunction;
        vertices = new ArrayList<>();
    }

    /**
     * Initializes vertex comparator components
     * @param vertices vertices list
     * @param distance distance array
     * @param target target vertex (for heuristic purposes)
     */
    public void initialize(List<Vertex> vertices, double[] distance, Vertex target) {
        this.target = target;
        this.distance = distance;
        this.vertices.clear();
        this.vertices.addAll(vertices);
    }

    /**
     * Compares two vertices
     * @param x first vertex
     * @param y second vertex
     * @return number less than 0 if x < y, 0 if x == y, otherwise number greater than 0
     */
    @Override
    public int compare(Integer x, Integer y) {
        final double xSum = heuristicFunction.execute(vertices.get(x), target) + distance[x];
        final double ySum = heuristicFunction.execute(vertices.get(y), target) + distance[y];

        return (int)(xSum - ySum);
    }
}
