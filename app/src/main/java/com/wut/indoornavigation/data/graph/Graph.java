package com.wut.indoornavigation.data.graph;


import android.support.annotation.NonNull;

import com.wut.indoornavigation.data.model.graph.Edge;
import com.wut.indoornavigation.data.model.graph.Vertex;

import java.util.List;

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
}
