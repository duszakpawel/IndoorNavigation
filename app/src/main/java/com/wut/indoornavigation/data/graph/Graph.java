package com.wut.indoornavigation.data.graph;


import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.wut.indoornavigation.data.model.graph.Edge;
import com.wut.indoornavigation.data.model.graph.Vertex;

import java.util.List;
import java.util.Map;

public interface Graph {

    void setVertices(@NonNull List<Vertex> vertices);

    boolean addEdge(Edge edge);
    int verticesCount();

    List<Edge> outEdges(int vertexId);
    List<Vertex> outVertices(Vertex vertex);
    List<Vertex> aStar(Vertex sourceId, Vertex targetId);
    List<Vertex> aStar(int sourceId, int targetId);
    Vertex getVertexByCoordinates(float x, float y, int floorNumber);
    boolean addVertex(Vertex vertex);

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
