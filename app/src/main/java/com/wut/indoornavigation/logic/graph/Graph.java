package com.wut.indoornavigation.logic.graph;


import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.logic.graph.models.Edge;
import com.wut.indoornavigation.logic.graph.models.Vertex;

import java.util.List;

public interface Graph {
    boolean addVertex(Vertex vertex);
    boolean addVertex(Integer id, Point coordinates);
    boolean addEdge(Edge edge);
    boolean addEdge(Vertex start, Vertex end, double weight);

    int verticesCount();

    List<Edge> outEdges(int vertexId);
    List<Vertex> outVertices(Vertex vertex);
    List<Vertex> aStar(Vertex sourceId, Vertex targetId, HeuristicFunction heuristicFunction);
    List<Vertex> aStar(int sourceId, int targetId, HeuristicFunction heuristicFunction);

    Vertex getVertexByCoordinates(float x, float y);
}
