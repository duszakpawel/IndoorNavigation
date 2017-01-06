package com.wut.indoornavigation.data.graph.impl;

import com.wut.indoornavigation.data.graph.Graph;
import com.wut.indoornavigation.data.graph.HeuristicFunction;
import com.wut.indoornavigation.data.graph.UnionFind;
import com.wut.indoornavigation.data.graph.VertexComparator;
import com.wut.indoornavigation.data.model.graph.Edge;
import com.wut.indoornavigation.data.model.graph.Vertex;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GraphImplBasicOperationsTests {

    private static final int DEFAULT_EDGE_WEIGHT = 1;

    private HeuristicFunction heuristicFunction = new HeuristicFunction();
    private UnionFind unionFind = new UnionFind();
    private VertexComparator vertexComparator = new VertexComparator(heuristicFunction);

    @Test
    public void graphAddVertexTestForNotUniqueVertexShouldRejectVertex() {
        Graph g = new GraphImpl(heuristicFunction, unionFind, vertexComparator);

        Vertex from = Vertex.builder().id(0).build();
        g.addVertex(from);

        Assert.assertEquals(g.addVertex(from), false);
    }

    @Test
    public void graphAddVertexTestForUniqueVertexShouldAddVertex() {
        Graph g = new GraphImpl(heuristicFunction, unionFind, vertexComparator);

        Vertex from = Vertex.builder().id(0).build();

        Assert.assertEquals(g.addVertex(from), true);
    }

    @Test
    public void graphAddEdgeTestForCorrectEdgeShouldAddEdge() {
        Graph g = new GraphImpl(heuristicFunction, unionFind, vertexComparator);

        Vertex from = Vertex.builder().id(0).build();
        Vertex to = Vertex.builder().id(1).build();

        g.addVertex(from);
        g.addVertex(to);

        Assert.assertEquals(g.addEdge(new Edge(from, to, DEFAULT_EDGE_WEIGHT)), true);
    }

    @Test
    public void graphAddEdgeTestForCorrectEdgeShouldAddLoopEdge() {
        Graph g = new GraphImpl(heuristicFunction, unionFind, vertexComparator);

        Vertex from = Vertex.builder().id(0).build();

        g.addVertex(from);

        Assert.assertEquals(g.addEdge(new Edge(from, from, DEFAULT_EDGE_WEIGHT)), true);
    }

    @Test
    public void graphAddEdgeTestForNotUniqueEdgeShouldRejectEdge() {
        Graph g = new GraphImpl(heuristicFunction, unionFind, vertexComparator);

        Vertex from = Vertex.builder().id(0).build();
        Vertex to = Vertex.builder().id(1).build();

        g.addVertex(from);
        g.addVertex(to);
        g.addEdge(new Edge(from, to, DEFAULT_EDGE_WEIGHT));
        Assert.assertEquals(g.addEdge(new Edge(from, to, DEFAULT_EDGE_WEIGHT)), false);
    }

    @Test
    public void graphSetVerticesTestForCorrectVerticesSetShouldSetCorrectVertices() {
        Graph g = new GraphImpl(heuristicFunction, unionFind, vertexComparator);

        Vertex from = Vertex.builder().id(0).build();
        Vertex to = Vertex.builder().id(1).build();

        List<Vertex> vertices = new ArrayList<>();
        vertices.add(from);
        vertices.add(to);

        g.setVertices(vertices);

        List<Vertex> graphVertices = g.getVertices();


        Assert.assertEquals(graphVertices.size(), 2);

        Assert.assertEquals(graphVertices.get(0).getId(), 0);
        Assert.assertEquals(graphVertices.get(1).getId(), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void graphSetVerticesTestForNotUniqueVerticesInListShouldThrowAnException() {
        Graph g = new GraphImpl(heuristicFunction, unionFind, vertexComparator);

        Vertex from = Vertex.builder().id(0).build();
        Vertex to = Vertex.builder().id(0).build();

        List<Vertex> vertices = new ArrayList<>();
        vertices.add(from);
        vertices.add(to);

        g.setVertices(vertices);
    }

    @Test
    public void graphConstructionTestVerticesCountForSmallCorrectGraphShouldReturnCorrectValue() {
        Graph g = GraphMocks.getMockGraph();

        Assert.assertEquals(g.verticesCount(), 5);
    }

    @Test
    public void graphConstructionTestVerticesCountForBigCorrectGraphShouldReturnCorrectValue() {
        Graph g = GraphMocks.getBigMockGraph();

        Assert.assertEquals(g.verticesCount(), 11);
    }

    @Test
    public void graphConstructionTestOutEdgesForSmallCorrectGraphShouldReturnCorrectValue() {
        Graph g = GraphMocks.getMockGraph();

        Assert.assertEquals(g.outEdges(2).size(), 2);
    }

    @Test(expected = IllegalStateException.class)
    public void graphConstructionTestOutEdgesForVertexNotInGraphShouldReturnCorrectValue() {
        Graph g = GraphMocks.getMockGraph();

        Assert.assertEquals(g.outEdges(100).size(), 0);
    }

    @Test
    public void graphConstructionTestContainsEdgeForConnectedVerticesShouldReturnTrue() {
        Graph g = GraphMocks.getMockGraph();

        Assert.assertEquals(g.containsEdge(0, 1), true);
    }

    @Test
    public void graphConstructionTestContainsEdgeForNotExistingLoopInVertexShouldReturnFalse() {
        Graph g = GraphMocks.getMockGraph();

        Assert.assertEquals(g.containsEdge(0, 0), false);
    }

    @Test
    public void graphConstructionTestContainsEdgeForNotConnectedVerticesShouldReturnFalse() {
        Graph g = GraphMocks.getMockGraph();

        Assert.assertEquals(g.containsEdge(0, 100), false);
    }

    @Test
    public void graphConstructionTestGetVerticesForCorrectGraphShouldReturnCorrectListOfVertices() {
        Graph g = GraphMocks.getMockGraph();

        List<Vertex> vertices = g.getVertices();

        for (int i = 0; i <= 4; i++) {
            Assert.assertEquals(vertices.get(i).getId(), i);
        }
    }

    @Test
    public void graphConstructionTestGetEdgesForCorrectGraphShouldReturnCorrectSets() {
        Graph g = new GraphImpl(heuristicFunction, unionFind, vertexComparator);

        Vertex from = Vertex.builder().id(0).build();
        Vertex to = Vertex.builder().id(1).build();

        List<Vertex> vertices = new ArrayList<>();
        vertices.add(from);
        vertices.add(to);

        g.setVertices(vertices);


        g.addEdge(new Edge(from, to, DEFAULT_EDGE_WEIGHT));

        Assert.assertEquals(g.getEdges().size(), 1);
        Assert.assertEquals(g.getEdges().get(from).size(), 1);
        Assert.assertEquals(g.getEdges().get(to), null);
    }
}