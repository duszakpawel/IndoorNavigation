package com.wut.indoornavigation.data.graph.impl;

import com.wut.indoornavigation.data.graph.Graph;
import com.wut.indoornavigation.data.graph.HeuristicFunction;
import com.wut.indoornavigation.data.graph.UnionFind;
import com.wut.indoornavigation.data.graph.VertexComparator;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.graph.Edge;
import com.wut.indoornavigation.data.model.graph.Vertex;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GraphImplLogicTest {

    private HeuristicFunction heuristicFunction = new HeuristicFunction();
    private UnionFind unionFind = new UnionFind();
    private VertexComparator vertexComparator = new VertexComparator(heuristicFunction);

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
        Vertex A = Vertex.builder().id(0).position(new Point(0,0,0)).build();
        Vertex B = Vertex.builder().id(1).position(new Point(1,0,0)).build();
        Vertex C = Vertex.builder().id(2).position(new Point(1,1,0)).build();
        Vertex D = Vertex.builder().id(3).position(new Point(0,2,0)).build();
        Vertex E = Vertex.builder().id(4).position(new Point(1,2,0)).build();

        vertices.add(A);
        vertices.add(B);
        vertices.add(C);
        vertices.add(D);
        vertices.add(E);

        Graph g = new GraphImpl(heuristicFunction, unionFind, vertexComparator);
        g.setVertices(vertices);
        g.addEdge(new Edge(A, B, 1));
        g.addEdge(new Edge(B, C, 1));
        g.addEdge(new Edge(A, C, 1.41));
        g.addEdge(new Edge(C, D, 1.41));
        g.addEdge(new Edge(C, E, 1));
        g.addEdge(new Edge(D, E, 1));

        return g;
    }

    private Graph getBigMockGraph(){
        List<Vertex> vertices = new ArrayList<>();
        Vertex A = Vertex.builder().id(0).position(new Point(0,0,0)).build();
        Vertex B = Vertex.builder().id(1).position(new Point(1,0,0)).build();
        Vertex C = Vertex.builder().id(2).position(new Point(2,0,0)).build();
        Vertex D = Vertex.builder().id(3).position(new Point(3,0,0)).build();
        Vertex E = Vertex.builder().id(4).position(new Point(0,1,0)).build();
        Vertex F = Vertex.builder().id(5).position(new Point(1,1,0)).build();
        Vertex G = Vertex.builder().id(6).position(new Point(2,1,0)).build();
        Vertex H = Vertex.builder().id(7).position(new Point(3,1,0)).build();
        Vertex I = Vertex.builder().id(8).position(new Point(0,2,0)).build();
        Vertex J = Vertex.builder().id(9).position(new Point(1,2,0)).build();
        Vertex K = Vertex.builder().id(10).position(new Point(2,2,0)).build();

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
        g.addEdge(new Edge(A, B, 1));
        g.addEdge(new Edge(B, A, 1));
        g.addEdge(new Edge(A, E, 1));
        g.addEdge(new Edge(E, A, 1));
        g.addEdge(new Edge(F, B, 1));
        g.addEdge(new Edge(B, F, 1));
        g.addEdge(new Edge(E, F, 1));
        g.addEdge(new Edge(F, E, 1));
        g.addEdge(new Edge(E, I, 1));
        g.addEdge(new Edge(I, E, 1));
        g.addEdge(new Edge(I, J, 1));
        g.addEdge(new Edge(J, I, 1));
        g.addEdge(new Edge(J, F, 1));
        g.addEdge(new Edge(F, J, 1));
        g.addEdge(new Edge(J, K, 1));
        g.addEdge(new Edge(K, J, 1));
        g.addEdge(new Edge(K, G, 1));
        g.addEdge(new Edge(G, K, 1));
        g.addEdge(new Edge(F, G, 1));
        g.addEdge(new Edge(G, F, 1));
        g.addEdge(new Edge(G, H, 1));
        g.addEdge(new Edge(H, G, 1));
        g.addEdge(new Edge(C, G, 1));
        g.addEdge(new Edge(G, C, 1));
        g.addEdge(new Edge(H, D, 1));
        g.addEdge(new Edge(D, H, 1));
        g.addEdge(new Edge(C, D, 1));
        g.addEdge(new Edge(D, C, 1));

        return g;
    }

    private Graph getMocNotConsistentGraph(){
        List<Vertex> vertices = new ArrayList<>();
        Vertex A = Vertex.builder().id(0).position(new Point(0,0,0)).build();
        Vertex B = Vertex.builder().id(1).position(new Point(0,1,0)).build();
        Vertex C = Vertex.builder().id(2).position(new Point(1,1,0)).build();

        vertices.add(A);
        vertices.add(B);
        vertices.add(C);

        Graph g = new GraphImpl(heuristicFunction, unionFind, vertexComparator);
        g.setVertices(vertices);
        g.addEdge(new Edge(A, C, 1));
        g.addEdge(new Edge(B, C, 1));

        return g;
    }

    private Graph getBigMockGraphModified(){
        List<Vertex> vertices = new ArrayList<>();
        Vertex A = Vertex.builder().id(0).position(new Point(0,0,0)).build();
        Vertex B = Vertex.builder().id(1).position(new Point(1,0,0)).build();
        Vertex C = Vertex.builder().id(2).position(new Point(2,0,0)).build();
        Vertex D = Vertex.builder().id(3).position(new Point(3,0,0)).build();
        Vertex E = Vertex.builder().id(4).position(new Point(0,1,0)).build();
        Vertex F = Vertex.builder().id(5).position(new Point(1,1,0)).build();
        Vertex G = Vertex.builder().id(6).position(new Point(2,1,0)).build();
        Vertex H = Vertex.builder().id(7).position(new Point(3,1,0)).build();
        Vertex I = Vertex.builder().id(8).position(new Point(0,2,0)).build();
        Vertex J = Vertex.builder().id(9).position(new Point(1,2,0)).build();
        Vertex K = Vertex.builder().id(10).position(new Point(2,2,0)).build();

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
        g.addEdge(new Edge(A, B, 1));
        g.addEdge(new Edge(B, A, 1));
        g.addEdge(new Edge(A, E, 1));
        g.addEdge(new Edge(E, A, 1));
        g.addEdge(new Edge(F, B, 1));
        g.addEdge(new Edge(B, F, 1));
        g.addEdge(new Edge(E, F, 1));
        g.addEdge(new Edge(F, E, 1));
        g.addEdge(new Edge(E, I, 1));
        g.addEdge(new Edge(I, E, 1));
        g.addEdge(new Edge(I, J, 1));
        g.addEdge(new Edge(J, I, 1));
        g.addEdge(new Edge(J, F, 1));
        g.addEdge(new Edge(F, J, 1));
        g.addEdge(new Edge(J, K, 1));
        g.addEdge(new Edge(K, J, 1));
        g.addEdge(new Edge(K, G, 1));
        g.addEdge(new Edge(G, K, 1));
        g.addEdge(new Edge(F, G, 1));
        g.addEdge(new Edge(G, F, 1));
        g.addEdge(new Edge(G, H, 1));
        g.addEdge(new Edge(H, G, 1));
        g.addEdge(new Edge(C, G, 1));
        g.addEdge(new Edge(G, C, 1));
        g.addEdge(new Edge(H, D, 1));
        g.addEdge(new Edge(D, H, 1));
        g.addEdge(new Edge(C, D, 1));
        g.addEdge(new Edge(D, C, 1));
        g.addEdge(new Edge(J, G, 1));
        g.addEdge(new Edge(G, J, 1));
        g.addEdge(new Edge(D, G, 1));
        g.addEdge(new Edge(G, D, 1));

        return g;
    }
}