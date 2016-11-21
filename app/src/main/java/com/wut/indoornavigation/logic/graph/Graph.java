package com.wut.indoornavigation.logic.graph;


import com.wut.indoornavigation.logic.graph.models.Edge;
import com.wut.indoornavigation.logic.graph.models.Vertex;

import java.util.List;

public interface Graph {
    boolean addEdge(Edge edge);
    boolean addEdge(Vertex start, Vertex end, double weight);

    int verticesCount();

    List<Edge> outEdges(int vertexId);
    List<Vertex> outVertices(int vertexId);
    List<Vertex> aStar(Vertex s, Vertex t, HeuristicFuction heuristicFunction);
}
