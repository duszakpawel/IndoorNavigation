package com.wut.indoornavigation.logic.graph.models;


public class Edge {
    public final Integer from;
    public final Integer to;
    public double weight;

    public Edge(Integer from, Integer to, double weight){
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int) (prime * result + from * weight - to * weight);

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Edge other = (Edge) obj;

        return this.from == other.from && this.to == other.to;

    }
}
