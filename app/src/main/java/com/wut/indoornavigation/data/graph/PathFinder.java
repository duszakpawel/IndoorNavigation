package com.wut.indoornavigation.data.graph;

import com.wut.indoornavigation.data.model.graph.Vertex;

import java.util.List;

/**
 * Path finder interface
 */
public interface PathFinder {

    /**
     * Computes shortest path between two vertices in graph
     * @param source vertex
     * @param target vertex
     * @return path (ordered list of vertices)
     */
    List<Vertex> aStar(Vertex source, Vertex target);

    /**
     * Returns vertex with specified coordinates
     * @param x x-coordinate
     * @param y y-coordinate
     * @param floorNumber floor number
     * @return desired vertex
     */
    Vertex getVertexByCoordinates(float x, float y, int floorNumber);
}
