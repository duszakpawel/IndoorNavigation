package com.wut.indoornavigation.logic.graph.models;

import lombok.Value;

@Value
public class Edge {
    private Vertex from;
    private Vertex to;
    private double weight;
}
