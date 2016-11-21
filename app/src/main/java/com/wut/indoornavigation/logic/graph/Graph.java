package com.wut.indoornavigation.logic.graph;


import com.wut.indoornavigation.logic.graph.models.Edge;
import com.wut.indoornavigation.logic.graph.models.Vertex;

import java.util.List;

public interface Graph {
    void AddEdge(Edge edge);
    void AddEdge(Vertex start, Vertex end, double weight);

    List<Integer> OutVertex(Integer vertex);
    List<Integer> AStar(Integer s, Integer t, HeuristicFuction heuristicFunction);
}
