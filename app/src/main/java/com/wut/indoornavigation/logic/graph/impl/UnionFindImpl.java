package com.wut.indoornavigation.logic.graph.impl;

import com.wut.indoornavigation.logic.graph.UnionFind;

/**
 * Union - Find structure
 */
public class UnionFindImpl implements UnionFind {
    private int[] id;
    private int[] sz;

    public UnionFindImpl(int N) {
        id = new int[N + 1];
        sz = new int[N + 1];
        for (int i = 0; i < N + 1; ++i) {
            id[i] = i;
            sz[i] = 0;
        }
    }

    private int root(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public boolean connected(int p) {
        return root(p) == root(id.length - 1);
    }

    public void union(int p) {
        int q = id.length - 1;
        union(p, q);
    }

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
}
