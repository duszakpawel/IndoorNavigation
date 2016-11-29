package com.wut.indoornavigation.data.graph;


import android.support.annotation.NonNull;

import com.wut.indoornavigation.data.model.graph.Edge;
import com.wut.indoornavigation.data.model.graph.Vertex;

import java.util.List;

public interface Graph {

    void setVertices(@NonNull List<Vertex> vertices);

    boolean addEdge(Edge edge);
    @Deprecated
    boolean addEdge(Vertex start, Vertex end, double weight);

    int verticesCount();

    List<Edge> outEdges(int vertexId);
    List<Vertex> outVertices(Vertex vertex);
    List<Vertex> aStar(Vertex sourceId, Vertex targetId);
    @Deprecated
    List<Vertex> aStar(int sourceId, int targetId);

}
