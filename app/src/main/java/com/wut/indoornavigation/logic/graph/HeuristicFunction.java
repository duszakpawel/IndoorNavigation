package com.wut.indoornavigation.logic.graph;

import com.wut.indoornavigation.logic.graph.models.Vertex;

public interface HeuristicFunction {
    double Execute(Vertex source, Vertex destination);
}
