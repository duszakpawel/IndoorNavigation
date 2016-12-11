package com.wut.indoornavigation.data.mesh.processingStrategy;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wut.indoornavigation.data.graph.Graph;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.graph.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Processing strategy class
 */
public abstract class ProcessingStrategy {

    /**
     * Process method invoked to process cell in FloorObject array
     * @param position point position in 3D space (provided by algorithm)
     * @param elements dictionary of elements of type corresponding to current sign
     * @param floorNumber floor number
     * @param graph mesh
     * @param id id for new vertex
     * @return added vertex to graph, otherwise null
     */
    @Nullable
    public abstract Vertex process(Point position, Map<Integer, List<Vertex>> elements, int floorNumber, Graph graph, int id);

    @NonNull
    protected Vertex addVertexToGraph(Point position, Graph graph, int id) {
        Vertex vertex = new Vertex(id, position);
        graph.addVertex(vertex);
        return vertex;
    }

    protected void addVertexToCorrespondingSet(Map<Integer, List<Vertex>> elements, int floorNumber, Vertex vertex) {
        List<Vertex> vertices;
        if (elements.containsKey(floorNumber)) {
            vertices = elements.get(floorNumber);
        } else {
            vertices = new ArrayList<>();
            elements.put(floorNumber, vertices);
        }

        vertices.add(vertex);
    }
}
