package com.wut.indoornavigation.logic.graph;

public interface UnionFind {
    void union(Integer p);
    void union(Integer p, Integer q);
    boolean connected(Integer p);
    boolean connected(Integer p, Integer q);
}
