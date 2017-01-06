package com.wut.indoornavigation.data.graph.impl;

import com.wut.indoornavigation.data.graph.Graph;
import com.wut.indoornavigation.data.graph.HeuristicFunction;
import com.wut.indoornavigation.data.graph.UnionFind;
import com.wut.indoornavigation.data.graph.VertexComparator;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.graph.Edge;
import com.wut.indoornavigation.data.model.graph.Vertex;

import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

public class GraphMocks {

    @Mock
    static HeuristicFunction heuristicFunction;
    @Mock
    static UnionFind unionFind;
    @Mock
    private static VertexComparator vertexComparator = new VertexComparator(new HeuristicFunction());
    private static final int DEFAULT_EDGE_WEIGHT = 1;

    public static Graph getMockGraph() {
        List<Vertex> vertices = new ArrayList<>();
        Vertex A = Vertex.builder().id(0).position(new Point(0, 0, 0)).build();
        Vertex B = Vertex.builder().id(1).position(new Point(1, 0, 0)).build();
        Vertex C = Vertex.builder().id(2).position(new Point(1, 1, 0)).build();
        Vertex D = Vertex.builder().id(3).position(new Point(0, 2, 0)).build();
        Vertex E = Vertex.builder().id(4).position(new Point(1, 2, 0)).build();

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

    public static Graph getBigMockGraph() {
        List<Vertex> vertices = new ArrayList<>();
        Vertex A = Vertex.builder().id(0).position(new Point(0, 0, 0)).build();
        Vertex B = Vertex.builder().id(1).position(new Point(1, 0, 0)).build();
        Vertex C = Vertex.builder().id(2).position(new Point(2, 0, 0)).build();
        Vertex D = Vertex.builder().id(3).position(new Point(3, 0, 0)).build();
        Vertex E = Vertex.builder().id(4).position(new Point(0, 1, 0)).build();
        Vertex F = Vertex.builder().id(5).position(new Point(1, 1, 0)).build();
        Vertex G = Vertex.builder().id(6).position(new Point(2, 1, 0)).build();
        Vertex H = Vertex.builder().id(7).position(new Point(3, 1, 0)).build();
        Vertex I = Vertex.builder().id(8).position(new Point(0, 2, 0)).build();
        Vertex J = Vertex.builder().id(9).position(new Point(1, 2, 0)).build();
        Vertex K = Vertex.builder().id(10).position(new Point(2, 2, 0)).build();

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
        g.addEdge(new Edge(A, B, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(B, A, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(A, E, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(E, A, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(F, B, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(B, F, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(E, F, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(F, E, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(E, I, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(I, E, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(I, J, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(J, I, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(J, F, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(F, J, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(J, K, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(K, J, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(K, G, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(G, K, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(F, G, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(G, F, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(G, H, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(H, G, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(C, G, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(G, C, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(H, D, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(D, H, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(C, D, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(D, C, DEFAULT_EDGE_WEIGHT));

        return g;
    }

    public static Graph getMocNotConsistentGraph() {
        List<Vertex> vertices = new ArrayList<>();
        Vertex A = Vertex.builder().id(0).position(new Point(0, 0, 0)).build();
        Vertex B = Vertex.builder().id(1).position(new Point(0, 1, 0)).build();
        Vertex C = Vertex.builder().id(2).position(new Point(1, 1, 0)).build();

        vertices.add(A);
        vertices.add(B);
        vertices.add(C);

        Graph g = new GraphImpl(heuristicFunction, unionFind, vertexComparator);
        g.setVertices(vertices);
        g.addEdge(new Edge(A, C, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(B, C, DEFAULT_EDGE_WEIGHT));

        return g;
    }

    public static Graph getBigMockGraphModified() {
        List<Vertex> vertices = new ArrayList<>();
        Vertex A = Vertex.builder().id(0).position(new Point(0, 0, 0)).build();
        Vertex B = Vertex.builder().id(1).position(new Point(1, 0, 0)).build();
        Vertex C = Vertex.builder().id(2).position(new Point(2, 0, 0)).build();
        Vertex D = Vertex.builder().id(3).position(new Point(3, 0, 0)).build();
        Vertex E = Vertex.builder().id(4).position(new Point(0, 1, 0)).build();
        Vertex F = Vertex.builder().id(5).position(new Point(1, 1, 0)).build();
        Vertex G = Vertex.builder().id(6).position(new Point(2, 1, 0)).build();
        Vertex H = Vertex.builder().id(7).position(new Point(3, 1, 0)).build();
        Vertex I = Vertex.builder().id(8).position(new Point(0, 2, 0)).build();
        Vertex J = Vertex.builder().id(9).position(new Point(1, 2, 0)).build();
        Vertex K = Vertex.builder().id(10).position(new Point(2, 2, 0)).build();

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
        g.addEdge(new Edge(A, B, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(B, A, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(A, E, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(E, A, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(F, B, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(B, F, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(E, F, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(F, E, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(E, I, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(I, E, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(I, J, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(J, I, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(J, F, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(F, J, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(J, K, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(K, J, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(K, G, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(G, K, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(F, G, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(G, F, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(G, H, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(H, G, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(C, G, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(G, C, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(H, D, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(D, H, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(C, D, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(D, C, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(J, G, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(G, J, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(D, G, DEFAULT_EDGE_WEIGHT));
        g.addEdge(new Edge(G, D, DEFAULT_EDGE_WEIGHT));

        return g;
    }
}
