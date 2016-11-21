package com.wut.indoornavigation.logic.graph.models;

import lombok.Value;

@Value
public class Edge {
    private Vertex from;
    private Vertex to;
    private double weight;

    public Edge(Vertex from, Vertex to, double weight){
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Edge(int fromId, int toId, double weight){
        this.from = new Vertex(fromId);
        this.to = new Vertex(toId);
        this.weight = weight;
    }
}
