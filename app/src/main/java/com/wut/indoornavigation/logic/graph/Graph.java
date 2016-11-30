package com.wut.indoornavigation.logic.graph;


import com.wut.indoornavigation.logic.graph.models.Edge;
import com.wut.indoornavigation.logic.graph.models.Vertex;

import java.util.List;

public interface Graph {
    boolean addVertex(Vertex vertex);
    boolean addEdge(Edge edge);
    boolean addEdge(Vertex start, Vertex end, double weight);

    int verticesCount();

    List<Edge> outEdges(int vertexId);
    List<Vertex> outVertices(Vertex vertex);
    List<Vertex> aStar(Vertex sourceId, Vertex targetId);
    @Deprecated
    List<Vertex> aStar(int sourceId, int targetId);

    Vertex getVertexByCoordinates(float x, float y);
}