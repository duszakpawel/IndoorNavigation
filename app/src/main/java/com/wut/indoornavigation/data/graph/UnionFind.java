package com.wut.indoornavigation.data.graph;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Union - Find structure
 */

@Singleton
public class UnionFind {

    private int[] id;
    private int[] sz;

    @Inject
    public UnionFind() {

    }

    /**
     * Initializes set for union find
     * @param n elements count
     */
    public void initialize(int n) {
        id = new int[n + 1];
        sz = new int[n + 1];
        for (int i = 0; i < n + 1; ++i) {
            id[i] = i;
            sz[i] = 0;
        }
    }

    /**
     * Links p value with q value
     * If one of them is not in set then they are linked but value(s) may not be actually in set
     * @param p value
     * @param q value
     */
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    /**
     * Adds p value to set
     * @param p value
     */
    public boolean connected(int p) {
        return root(p) == root(id.length - 1);
    }

    /**
     * Returns information whether p value belongs to the set
     * @param p value
     */
    public void union(int p) {
        int q = id.length - 1;
        union(p, q);
    }

    /**
     * Returns information whether p value is connected with q value
     * @param p value
     * @param q value
     */
    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (i == j) {
            return;
        }
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }

        int k = root(p);
        int l = root(id.length - 1);
        if (k == l) {
            return;
        }
        if (sz[k] < sz[l]) {
            id[k] = l;
            sz[l] += sz[k];
        } else {
            id[k] = l;
            sz[k] += sz[l];
        }
    }

    private int root(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }
}
