package com.wut.indoornavigation.data.graph.impl;


import com.wut.indoornavigation.data.graph.Graph;
import com.wut.indoornavigation.data.graph.HeuristicFunction;
import com.wut.indoornavigation.data.graph.UnionFind;
import com.wut.indoornavigation.data.graph.VertexComparator;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.graph.Vertex;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class GraphImplTest {

    //this should be mocks @Mock
    private HeuristicFunction heuristicFunction = new HeuristicFunction();
    private UnionFind unionFind = new UnionFind();
    private VertexComparator vertexComparator = new VertexComparator(heuristicFunction);

    //uncomment to set up mocks
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }

    @Test
    public void graphConstructionTest() {
        Graph g = getMockGraph();

        Assert.assertEquals(g.verticesCount(), 5);
        Assert.assertEquals(g.outEdges(2).size(), 2);
    }

    @Test
    public void aStarTestForSmallDirectedGraph() {
        Graph g = getMockGraph();
        List<Vertex> result = g.aStar(0, 4);

        Assert.assertEquals(result.size(), 3);
    }

    @Test
    public void aStarTestForBigDirectedGraph() {
        Graph g = getBigMockGraph();
        List<Vertex> result = g.aStar(8, 3);

        Assert.assertEquals(result.size(), 6);
    }

    @Test
    public void aStarTestForBigDirectedGraphModified() {
        Graph g = getBigMockGraphModified();
        List<Vertex> result = g.aStar(8, 3);

        Assert.assertEquals(result.size(), 4);
    }

    @Test
    public void aStarTestForNotConsistentGraph() {
        Graph g = getMocNotConsistentGraph();
        List<Vertex> result = g.aStar(0, 1);

        Assert.assertEquals(result.size(), 1);
    }

    @Test
    public void aStarTestForSourceEqualToTarget() {
        Graph g = getMocNotConsistentGraph();
        List<Vertex> result = g.aStar(0, 1);

        Assert.assertEquals(result.size(), 1);
    }

    private Graph getMockGraph() {
        List<Vertex> vertices = new ArrayList<>();
        Vertex A = new Vertex(0, new Point(0,0,0));
        Vertex B = new Vertex(1, new Point(1, 0,0));
        Vertex C = new Vertex(2, new Point(1, 1,0));
        Vertex D = new Vertex(3, new Point(0, 2,0));
        Vertex E = new Vertex(4, new Point(1, 2,0));

        vertices.add(A);
        vertices.add(B);
        vertices.add(C);
        vertices.add(D);
        vertices.add(E);

        Graph g = new GraphImpl(heuristicFunction, unionFind, vertexComparator);
        g.setVertices(vertices);
        g.addEdge(A, B, 1);
        g.addEdge(B, C, 1);
        g.addEdge(A, C, 1.41);
        g.addEdge(C, D, 1.41);
        g.addEdge(C, E, 1);
        g.addEdge(D, E, 1);

        return g;
    }

    private Graph getBigMockGraph(){
        List<Vertex> vertices = new ArrayList<>();
        Vertex A = new Vertex(0, new Point(0,0,0));
        Vertex B = new Vertex(1, new Point(1, 0,0));
        Vertex C = new Vertex(2, new Point(2, 0,0));
        Vertex D = new Vertex(3, new Point(3, 0,0));
        Vertex E = new Vertex(4, new Point(0, 1,0));
        Vertex F = new Vertex(5, new Point(1, 1,0));
        Vertex G = new Vertex(6, new Point(2, 1,0));
        Vertex H = new Vertex(7, new Point(3, 1,0));
        Vertex I = new Vertex(8, new Point(0, 2,0));
        Vertex J = new Vertex(9, new Point(1, 2,0));
        Vertex K = new Vertex(10, new Point(2, 2,0));

        vertices.add(A);
        vertices.add(B);
        vertices.add(C);
        vertices.add(D);
        vertices.add(E);
        vertices.add(F);
        vertices.add(G);
        vertices.add(H);
        vertices.add(I);
        vertices.add(J);
        vertices.add(K);

        Graph g = new GraphImpl(heuristicFunction, unionFind, vertexComparator);
        g.setVertices(vertices);
        g.addEdge(A, B, 1);
        g.addEdge(B, A, 1);
        g.addEdge(A, E, 1);
        g.addEdge(E, A, 1);
        g.addEdge(F, B, 1);
        g.addEdge(B, F, 1);
        g.addEdge(E, F, 1);
        g.addEdge(F, E, 1);
        g.addEdge(E, I, 1);
        g.addEdge(I, E, 1);
        g.addEdge(I, J, 1);
        g.addEdge(J, I, 1);
        g.addEdge(J, F, 1);
        g.addEdge(F, J, 1);
        g.addEdge(J, K, 1);
        g.addEdge(K, J, 1);
        g.addEdge(K, G, 1);
        g.addEdge(G, K, 1);
        g.addEdge(F, G, 1);
        g.addEdge(G, F, 1);
        g.addEdge(G, H, 1);
        g.addEdge(H, G, 1);
        g.addEdge(C, G, 1);
        g.addEdge(G, C, 1);
        g.addEdge(H, D, 1);
        g.addEdge(D, H, 1);
        g.addEdge(C, D, 1);
        g.addEdge(D, C, 1);

        return g;
    }

    private Graph getMocNotConsistentGraph(){
        List<Vertex> vertices = new ArrayList<>();
        Vertex A = new Vertex(0, new Point(0, 0,0));
        Vertex B = new Vertex(1, new Point(0, 1,0));
        Vertex C = new Vertex(2, new Point(1, 1,0));

        vertices.add(A);
        vertices.add(B);
        vertices.add(C);

        Graph g = new GraphImpl(heuristicFunction, unionFind, vertexComparator);
        g.setVertices(vertices);
        g.addEdge(A, C, 1);
        g.addEdge(B, C, 1);

        return g;
    }

    private Graph getBigMockGraphModified(){
        List<Vertex> vertices = new ArrayList<>();
        Vertex A = new Vertex(0, new Point(0,0,0));
        Vertex B = new Vertex(1, new Point(1, 0,0));
        Vertex C = new Vertex(2, new Point(2, 0,0));
        Vertex D = new Vertex(3, new Point(3, 0,0));
        Vertex E = new Vertex(4, new Point(0, 1,0));
        Vertex F = new Vertex(5, new Point(1, 1,0));
        Vertex G = new Vertex(6, new Point(2, 1,0));
        Vertex H = new Vertex(7, new Point(3, 1,0));
        Vertex I = new Vertex(8, new Point(0, 2,0));
        Vertex J = new Vertex(9, new Point(1, 2,0));
        Vertex K = new Vertex(10, new Point(2, 2,0));

        vertices.add(A);
        vertices.add(B);
        vertices.add(C);
        vertices.add(D);
        vertices.add(E);
        vertices.add(F);
        vertices.add(G);
        vertices.add(H);
        vertices.add(I);
        vertices.add(J);
        vertices.add(K);

        Graph g = new GraphImpl(heuristicFunction, unionFind, vertexComparator);
        g.setVertices(vertices);
        g.addEdge(A, B, 1);
        g.addEdge(B, A, 1);
        g.addEdge(A, E, 1);
        g.addEdge(E, A, 1);
        g.addEdge(F, B, 1);
        g.addEdge(B, F, 1);
        g.addEdge(E, F, 1);
        g.addEdge(F, E, 1);
        g.addEdge(E, I, 1);
        g.addEdge(I, E, 1);
        g.addEdge(I, J, 1);
        g.addEdge(J, I, 1);
        g.addEdge(J, F, 1);
        g.addEdge(F, J, 1);
        g.addEdge(J, K, 1);
        g.addEdge(K, J, 1);
        g.addEdge(K, G, 1);
        g.addEdge(G, K, 1);
        g.addEdge(F, G, 1);
        g.addEdge(G, F, 1);
        g.addEdge(G, H, 1);
        g.addEdge(H, G, 1);
        g.addEdge(C, G, 1);
        g.addEdge(G, C, 1);
        g.addEdge(H, D, 1);
        g.addEdge(D, H, 1);
        g.addEdge(C, D, 1);
        g.addEdge(D, C, 1);
        g.addEdge(J, G, 1);
        g.addEdge(G, J, 1);
        g.addEdge(D, G, 1);
        g.addEdge(G, D, 1);

        return g;
    }
}