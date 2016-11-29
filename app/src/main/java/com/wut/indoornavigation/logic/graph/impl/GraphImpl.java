package com.wut.indoornavigation.logic.graph.impl;


import android.support.annotation.NonNull;

import com.wut.indoornavigation.logic.graph.Graph;
import com.wut.indoornavigation.logic.graph.UnionFind;
import com.wut.indoornavigation.logic.graph.models.Edge;
import com.wut.indoornavigation.logic.graph.models.Vertex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import javax.inject.Inject;


public class GraphImpl implements Graph {
    private static final int NO_PREVIOUS = -1;
    private static final double DIST_DEFAULT_VALUE = 0;
    private static final int INFINITY = 10000;

    @Inject
    HeuristicFunction heuristicFunction;

    private final List<Vertex> vertices;
    private final Map<Vertex, List<Edge>> edges;

    public GraphImpl(@NonNull List<Vertex> vertices) {
        this.vertices = new ArrayList<>();
        this.edges = new HashMap<>();
        this.vertices.addAll(vertices);
    }

    @Override
    public boolean addEdge(@NonNull Edge edge) {
        Vertex from = edge.getFrom();
        Vertex to = edge.getTo();

        if (vertices.contains(from) && vertices.contains(to)) {
            if (!edges.containsKey(from)) {
                List<Edge> outEdges = new ArrayList<>();
                outEdges.add(edge);
                edges.put(from, outEdges);

                return true;
            } else {
                List<Edge> fromOutEdges = edges.get(from);
                if (fromOutEdges.contains(edge)) {
                    return false;
                } else {
                    fromOutEdges.add(edge);

                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean addEdge(@NonNull Vertex start, @NonNull Vertex end, double weight) {
        Edge e = new Edge(start, end, weight);

        return addEdge(e);
    }

    @Override
    public int verticesCount() {
        return vertices.size();
    }

    @Override
    public List<Vertex> outVertices(@NonNull Vertex vertex) {
            List<Edge> outEdges = edges.get(vertex);
            List<Vertex> outVertices = new ArrayList<>();
            if (outEdges == null || outEdges.isEmpty()) {
                return outVertices;
            }

            for (Edge outEdge : outEdges) {
                outVertices.add(outEdge.getTo());
            }
            return outVertices;
    }

    @Override
    public List<Edge> outEdges(int vertexId) {
        Vertex vertex;
        int verticesCount = vertices.size();
        for (int i = 0; i < verticesCount; i++) {
            Vertex v = vertices.get(i);
            if (v.getId() == vertexId) {
                vertex = v;
                return edges.get(vertex);
            }
        }

            throw new IllegalStateException("Vertex does not belong to graph.");
    }

    @Override
    public List<Vertex> aStar(Vertex s, Vertex t) {
        int verticesCount = verticesCount();
        double[] distance = new double[verticesCount];
        int[] previous = new int[verticesCount];

        for (int i = 0; i < verticesCount; i++) {
            distance[i] = INFINITY;
            previous[i] = NO_PREVIOUS;
        }

        int sIndex = vertices.indexOf(s);

        distance[sIndex] = DIST_DEFAULT_VALUE;

        UnionFind Close = new UnionFindImpl(verticesCount);
        Comparator<Vertex> comparator = new VertexComparator(heuristicFunction, vertices, distance, t);
        PriorityQueue<Vertex> Open = new PriorityQueue<>(verticesCount, comparator);
        Open.add(s);

        while (!Open.isEmpty()) {
            Vertex u = Open.peek();
            int uIndex = vertices.indexOf(u);
            for (Vertex iVertex : Open) {
                int iIndex = vertices.indexOf(iVertex);

                if (distance[iIndex] + heuristicFunction.execute(vertices.get(iIndex), t) <= distance[uIndex] + heuristicFunction.execute(vertices.get(uIndex), t)) {
                    u = iVertex;
                    uIndex = vertices.indexOf(u);
                }
            }

            Open.remove(u);
            Close.union(uIndex);
            if (vertices.get(uIndex).equals(t)) {
                break;
            }

            List<Vertex> outVertices = outVertices(u);
            for (Vertex wVertex : outVertices) {
                int wIndex = vertices.indexOf(wVertex);
                double uwWeight = 0;

                List<Edge> uOutEdges = edges.get(vertices.get(uIndex));
                for (Edge uOutEdge : uOutEdges) {
                    if (uOutEdge.getTo() == wVertex) {
                        uwWeight = uOutEdge.getWeight();
                        break;
                    }
                }
                if (!Close.connected(wIndex)) {
                    if (!Open.contains(wVertex)) {
                        Open.add(wVertex);
                        distance[wIndex] = INFINITY;
                    }
                    if (distance[wIndex] > distance[uIndex] + uwWeight) {
                        distance[wIndex] = distance[uIndex] + uwWeight;
                        previous[wIndex] = uIndex;
                    }
                }
            }
        }

        return reproducePath(previous, t.getId());
    }

    private List<Vertex> reproducePath(int[] previous, int targetIndex) {
        final List<Vertex> result = new ArrayList<>();
        while (targetIndex != NO_PREVIOUS) {
            result.add(0, vertices.get(targetIndex));
            targetIndex = previous[targetIndex];
        }

        return result;
    }

    private Vertex findVertex(int id) {
        Vertex v;
        for (Vertex vertex : vertices) {
            if (vertex.getId() == id) {
                v = vertex;
                return v;
            }
        }

        throw new IllegalStateException("There is no vertex with id" + id);
    }

    @Override
    public List<Vertex> aStar(int s, int t) {
        Vertex sVertex = findVertex(s);
        Vertex tVertex = findVertex(t);

        if (sVertex == null || tVertex == null) {
            throw new RuntimeException("One ore more vertices does not exist in graph.");
        }

        return aStar(sVertex, tVertex);
    }
}
