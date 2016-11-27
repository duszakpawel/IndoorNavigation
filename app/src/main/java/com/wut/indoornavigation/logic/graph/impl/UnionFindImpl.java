package com.wut.indoornavigation.logic.graph.impl;

import com.wut.indoornavigation.logic.graph.UnionFind;

/**
 * Union - Find structure
 */
public class UnionFindImpl implements UnionFind {
    private Integer[] id;
    private Integer[] sz;

    public UnionFindImpl(Integer N) {
        id = new Integer[N+1];
        sz = new Integer[N+1];
        for (int i = 0; i < N+1; ++i)
        {
            id[i] = i;
            sz[i] = 0;
        }
    }

    private Integer root(Integer i) {
        while (!i.equals(id[i])) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    public boolean connected(Integer p, Integer q) {
        return root(p).equals(root(q));
    }

    public boolean connected(Integer p) {
        return root(p).equals(root(id.length-1));
    }

    public void union(Integer p) {
        Integer q = id.length - 1;
        union(p,q);
    }

    public void union(Integer p, Integer q) {
        Integer i = root(p);
        Integer j = root(q);
        if (i.equals(j)) {
            return;
        }
        if (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; }
        else               { id[j] = i; sz[i] += sz[j]; }

        Integer k = root(p);
        Integer l = root(id.length-1);
        if (k.equals(l)) {
            return;
        }
        if (sz[k] < sz[l]) { id[k] = l; sz[l] += sz[k]; }
        else               { id[k] = l; sz[k] += sz[l]; }
    }
}
