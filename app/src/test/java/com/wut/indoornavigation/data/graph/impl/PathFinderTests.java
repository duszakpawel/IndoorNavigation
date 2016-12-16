package com.wut.indoornavigation.data.graph.impl;

import com.wut.indoornavigation.data.graph.Graph;
import com.wut.indoornavigation.data.model.graph.Vertex;

import junit.framework.Assert;

import org.junit.Test;

import java.util.List;

public class PathFinderTests {


    @Test
    public void aStarTestForSmallDirectedGraph() {
        Graph g = GraphMocks.getMockGraph();
        List<Vertex> result = g.aStar(0, 4);

        Assert.assertEquals(result.size(), 3);
    }

    @Test
    public void aStarTestForBigDirectedGraph() {
        Graph g = GraphMocks.getBigMockGraph();
        List<Vertex> result = g.aStar(8, 3);

        Assert.assertEquals(result.size(), 6);
    }

    @Test
    public void aStarTestForBigDirectedGraphModified() {
        Graph g = GraphMocks.getBigMockGraphModified();
        List<Vertex> result = g.aStar(8, 3);

        Assert.assertEquals(result.size(), 4);
    }

    @Test
    public void aStarTestForNotConsistentGraph() {
        Graph g = GraphMocks.getMocNotConsistentGraph();
        List<Vertex> result = g.aStar(0, 1);

        Assert.assertEquals(result.size(), 1);
    }

    @Test
    public void aStarTestForSourceEqualToTarget() {
        Graph g = GraphMocks.getMocNotConsistentGraph();
        List<Vertex> result = g.aStar(0, 1);

        Assert.assertEquals(result.size(), 1);
    }

    @Test
    public void getVertexByCoordinatesTestForExistingVertexShouldReturnVertex() {
        Graph g = GraphMocks.getMockGraph();

        Vertex v = g.getVertexByCoordinates(0, 0, 0);
        Assert.assertNotNull(v);
        Assert.assertEquals(v.getId(), 0);
    }

    @Test
    public void getVertexByCoordinatesTestForNotExistingVertexShouldReturnNull() {
        Graph g = GraphMocks.getMockGraph();

        Vertex v = g.getVertexByCoordinates(-1, -1, 0);
        Assert.assertEquals(v, null);
    }
}