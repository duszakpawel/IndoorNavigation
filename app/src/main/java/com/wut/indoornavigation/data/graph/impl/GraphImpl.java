package com.wut.indoornavigation.data.graph.impl;

import android.support.annotation.NonNull;

import com.wut.indoornavigation.data.graph.Graph;
import com.wut.indoornavigation.data.graph.HeuristicFunction;
import com.wut.indoornavigation.data.graph.UnionFind;
import com.wut.indoornavigation.data.graph.VertexComparator;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.graph.Edge;
import com.wut.indoornavigation.data.model.graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class GraphImpl implements Graph {
    private static final int NO_PREVIOUS = -1;
    private static final double DIST_DEFAULT_VALUE = 0;
    private static final int INFINITY = 10000;

    private final HeuristicFunction heuristicFunction;
    private final UnionFind close;
    private final VertexComparator comparator;
    private final List<Vertex> vertices;
    private final Map<Vertex, List<Edge>> edges;

    public GraphImpl(HeuristicFunction heuristicFunction, UnionFind close, VertexComparator comparator) {
        this.heuristicFunction = heuristicFunction;
        this.close = close;
        this.comparator = comparator;
        this.vertices = new ArrayList<>();
        this.edges = new HashMap<>();
    }

    @Override
    public void setVertices(@NonNull List<Vertex> vertices) {
        this.vertices.clear();
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
        final int verticesCount = verticesCount();
        final double[] distance = new double[verticesCount];
        final int[] previous = new int[verticesCount];

        for (int i = 0; i < verticesCount; i++) {
            distance[i] = INFINITY;
            previous[i] = NO_PREVIOUS;
        }

        final int sIndex = vertices.indexOf(s);

        distance[sIndex] = DIST_DEFAULT_VALUE;

        comparator.initialize(vertices, distance, t);
        close.initialize(verticesCount);

        final PriorityQueue<Vertex> open = new PriorityQueue<>(verticesCount, comparator);
        open.add(s);

        while (!open.isEmpty()) {
            Vertex u = open.peek();
            int uIndex = vertices.indexOf(u);
            for (final Vertex iVertex : open) {
                final int iIndex = vertices.indexOf(iVertex);

                if (distance[iIndex] + heuristicFunction.execute(vertices.get(iIndex), t) <= distance[uIndex] + heuristicFunction.execute(vertices.get(uIndex), t)) {
                    u = iVertex;
                    uIndex = vertices.indexOf(u);
                }
            }

            open.remove(u);
            close.union(uIndex);
            if (vertices.get(uIndex).equals(t)) {
                break;
            }

            final List<Vertex> outVertices = outVertices(u);
            for (final Vertex wVertex : outVertices) {
                final int wIndex = vertices.indexOf(wVertex);
                double uwWeight = 0;

                final List<Edge> uOutEdges = edges.get(vertices.get(uIndex));
                for (final Edge uOutEdge : uOutEdges) {
                    if (uOutEdge.getTo() == wVertex) {
                        uwWeight = uOutEdge.getWeight();
                        break;
                    }
                }
                if (!close.connected(wIndex)) {
                    if (!open.contains(wVertex)) {
                        open.add(wVertex);
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
        for (final Vertex vertex : vertices) {
            if (vertex.getId() == id) {
                return vertex;
            }
        }

        throw new IllegalStateException("There is no vertex with id" + id);
    }

    @Override
    public List<Vertex> aStar(int s, int t) {
        final Vertex sVertex = findVertex(s);
        final Vertex tVertex = findVertex(t);

        if (sVertex == null || tVertex == null) {
            throw new RuntimeException("One ore more vertices does not exist in graph.");
        }

        return aStar(sVertex, tVertex);
    }

    @Override
    public Vertex getVertexByCoordinates(float x, float y, int floorNumber) {
        for (Vertex vertex : vertices) {
            Point coordinates = vertex.getPosition();
            if (coordinates.getX() == x && coordinates.getY() == y && coordinates.getZ() == floorNumber) {
                return vertex;
            }
        }

        return null;
    }

    @Override
    public boolean addVertex(Vertex vertex) {
        for (Vertex v : vertices) {
            if (v.getId() == vertex.getId() || v.getPosition().equals(vertex.getPosition())) {
                return false;
            }
        }
        vertices.add(vertex);

        return true;
    }

    @Override
    public boolean containsEdge(int vId, int wId) {
        List<Edge> vOutEdges = outEdges(vId);
        if(vOutEdges == null){
            return false;
        }

        for (Edge vOutEdge : vOutEdges) {
            if(vOutEdge.getTo().getId() == wId){
                return true;
            }
        }

        return false;
    }
}
