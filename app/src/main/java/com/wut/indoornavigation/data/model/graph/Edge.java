package com.wut.indoornavigation.data.model.graph;

import lombok.Value;

@Value
public class Edge {
    Vertex from;
    Vertex to;
    double weight;
}
