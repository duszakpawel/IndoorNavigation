package com.wut.indoornavigation.data.graph;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.wut.indoornavigation.data.model.graph.Edge;
import com.wut.indoornavigation.data.model.graph.Vertex;

import java.util.List;
import java.util.Map;

/**
 * Graph interface
 */
public interface Graph extends PathFinder {

    /**
     * Sets vertices in graph
     * @param vertices vertices list
     */
    void setVertices(@NonNull List<Vertex> vertices);

    /**
     * Adds edge to graph
     * @param edge edge object
     * @return true if operation succeeded, otherwise false
     */
    boolean addEdge(Edge edge);

    /**
     * Counts vertices in graph
     * @return vertices count
     */
    int verticesCount();

    /**
     * Returns out edges for vertex with specified id
     * @param vertexId id of vertex
     * @return list of edges
     */
    List<Edge> outEdges(int vertexId);

    /**
     * Returns out vertices for vertex
     * @param vertex vertex object
     * @return list of vertices
     */
    List<Vertex> outVertices(Vertex vertex);

    /**
     * Only for testing purposes.
     * Computes shortest path between two vertices in graph
     * @param sourceId source vertex id
     * @param targetId target vertex id
     * @return (ordered list of vertices)
     */
    @VisibleForTesting
    List<Vertex> aStar(int sourceId, int targetId);

    /**
     * Adds vertex to graph
     * @param vertex vertex object
     * @return true if vertex was added, otherwise false
     */
    boolean addVertex(Vertex vertex);

    /**
     * Returns information whether graph contains edge between two vertices
     * @param vId id of first vertex
     * @param wId id of second vertex
     * @return true if yes, otherwise false
     */
    boolean containsEdge(int vId, int wId);

    /**
     * Only for testing purposes.
     * @return vertices list
     */
    @VisibleForTesting
    List<Vertex> getVertices();

    /**
     * Only for testing purposes.
     * @return edges map
     */
    @VisibleForTesting
    Map<Vertex, List<Edge>> getEdges();
}
