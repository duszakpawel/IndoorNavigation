package com.wut.indoornavigation;


import com.wut.indoornavigation.data.graph.UnionFind;

import junit.framework.Assert;

import org.junit.Test;

public class UnionFindTests {
    @Test
    public void unionFindTest_Success(){
        UnionFind unionFind = new UnionFind();
        unionFind.initialize(3);
        unionFind.union(0, 1);
        Assert.assertEquals(unionFind.connected(0, 1), true);
        Assert.assertEquals(unionFind.connected(0, 2), false);
    }

    @Test
    public void unionFindTest2_Success(){
        UnionFind unionFind = new UnionFind();
        unionFind.initialize(3);
        unionFind.union(0, 1);
        unionFind.union(0, 2);
        Assert.assertEquals(unionFind.connected(0, 1), true);
        Assert.assertEquals(unionFind.connected(0, 2), true);
    }
}
