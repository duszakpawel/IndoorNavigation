package com.wut.indoornavigation.logic.graph;

public interface UnionFind {
    /**
     * Adds p value to set
     * @param p value
     */
    void union(int p);
    /**
     * Links p value with q value
     * If one of them is not in set then they are linked but value(s) may not be actually in set
     * @param p value
     * @param q value
     */
    void union(int p, int q);
    /**
     * Returns information whether p value belongs to the set
     * @param p value
     */
    boolean connected(int p);
    /**
     * Returns information whether p value is connected with q value
     * @param p value
     * @param q value
     */
    boolean connected(int p, int q);
}
