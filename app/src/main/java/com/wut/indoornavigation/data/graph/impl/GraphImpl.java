package com.wut.indoornavigation.data.graph.impl;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.wut.indoornavigation.data.exception.VertexNotExistException;
import com.wut.indoornavigation.data.graph.Graph;
import com.wut.indoornavigation.data.graph.HeuristicFunction;
import com.wut.indoornavigation.data.graph.UnionFind;
import com.wut.indoornavigation.data.graph.VertexComparator;
import com.wut.indoornavigation.data.model.graph.Edge;
import com.wut.indoornavigation.data.model.graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Implementation of graph interface
 */
public class GraphImpl implements Graph {

    private static final int NO_PREVIOUS = -1;
    private static final int NOT_FOUND = -1;
    private static final double DIST_DEFAULT_VALUE = 0;
    private static final int INFINITY = 10000;

    private final HeuristicFunction heuristicFunction;
    private final UnionFind close;
    private final VertexComparator comparator;
    private final List<Vertex> vertices;
    private final Map<Float, Map<Float, Map<Float, Integer>>> spaceMap;
    private final Map<Vertex, List<Edge>> edges;

    public GraphImpl(HeuristicFunction heuristicFunction, UnionFind close, VertexComparator comparator) {
        this.heuristicFunction = heuristicFunction;
        this.close = close;
        this.comparator = comparator;
        this.vertices = new ArrayList<>();
        this.edges = new HashMap<>();
        this.spaceMap = new HashMap<>();
    }

    @Override
    public void setVertices(@NonNull List<Vertex> vertices) {
        this.vertices.clear();
        for (Vertex vertex : vertices) {
            if (!addVertex(vertex)) {
                throw new IllegalArgumentException("Attempt to add not unique vertex to graph.");
            }
        }
    }

    @Override
    public boolean addEdge(@NonNull Edge edge) {
        final Vertex from = edge.getFrom();
        final Vertex to = edge.getTo();

        if (vertices.contains(from) && vertices.contains(to)) {
            if (!edges.containsKey(from)) {
                final List<Edge> outEdges = new ArrayList<>();
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
        final List<Edge> outEdges = edges.get(vertex);
        final List<Vertex> outVertices = new ArrayList<>();

        if (outEdges == null || outEdges.isEmpty()) {
            return outVertices;
        }

        for (final Edge outEdge : outEdges) {
            outVertices.add(outEdge.getTo());
        }
        return outVertices;
    }

    @Override
    public List<Edge> outEdges(int vertexId) {
        for (int i = 0; i < vertices.size(); i++) {
            final Vertex v = vertices.get(i);

            if (v.getId() == vertexId) {
                return edges.get(v);
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

        if (sIndex == NOT_FOUND) {
            return new ArrayList<>();
        }

        distance[sIndex] = DIST_DEFAULT_VALUE;

        comparator.initialize(vertices, distance, t);
        close.initialize(verticesCount);

        final PriorityQueue<Integer> open = new PriorityQueue<>(verticesCount, comparator);
        open.add(sIndex);

        while (!open.isEmpty()) {
            int uIndex = open.peek();
            Vertex u = vertices.get(uIndex);

            for (final int iIndex : open) {
                if (distance[iIndex] + heuristicFunction.execute(vertices.get(iIndex), t) <= distance[uIndex] + heuristicFunction.execute(vertices.get(uIndex), t)) {
                    u = vertices.get(iIndex);
                    uIndex = iIndex;
                }
            }

            open.remove(uIndex);
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
                    if (!open.contains(wIndex)) {
                        open.add(wIndex);
                        distance[wIndex] = INFINITY;
                    }
                    if (distance[wIndex] > distance[uIndex] + uwWeight) {
                        distance[wIndex] = distance[uIndex] + uwWeight;
                        previous[wIndex] = uIndex;
                    }
                }
            }
        }
        previous[sIndex] = NO_PREVIOUS;
        return reproducePath(previous, vertices.indexOf(t));
    }

    @VisibleForTesting
    @Override
    public List<Vertex> aStar(int s, int t) {
        final Vertex sVertex = findVertex(s);
        final Vertex tVertex = findVertex(t);

        if (sVertex == null || tVertex == null) {
            throw new VertexNotExistException("One ore more vertices does not exist in graph.");
        }

        return aStar(sVertex, tVertex);
    }

    @Override
    public Vertex getVertexByCoordinates(float x, float y, int floorNumber) {
        if (!spaceMap.containsKey(x)) {
            return null;
        }
        final Map<Float, Map<Float, Integer>> mapTwo = spaceMap.get(x);
        if (!mapTwo.containsKey(y)) {
            return null;
        }
        final Map<Float, Integer> mapOne = mapTwo.get(y);
        if (!mapOne.containsKey((float) floorNumber)) {
            return null;
        }
        return vertices.get(mapOne.get((float) floorNumber));
    }

    @Override
    public boolean addVertex(Vertex vertex) {
        if (vertex.getPosition() == null) {
            for (Vertex v : vertices) {
                if (vertex.getId() == v.getId()) {
                    return false;
                }
            }
            vertices.add(vertex);

            return true;
        }
        if (!spaceMap.containsKey(vertex.getPosition().getX())) {
            final Map<Float, Map<Float, Integer>> twoMap = new HashMap<>();
            spaceMap.put(vertex.getPosition().getX(), twoMap);
            final Map<Float, Integer> oneMap = new HashMap<>();
            twoMap.put(vertex.getPosition().getY(), oneMap);
            oneMap.put(vertex.getPosition().getZ(), vertices.size());
            vertices.add(vertex);
            return true;
        }
        final Map<Float, Map<Float, Integer>> mapThree = spaceMap.get(vertex.getPosition().getX());
        if (!mapThree.containsKey(vertex.getPosition().getY())) {
            final Map<Float, Integer> twoMap = new HashMap<>();
            mapThree.put(vertex.getPosition().getY(), twoMap);
            twoMap.put(vertex.getPosition().getZ(), vertices.size());
            vertices.add(vertex);
            return true;
        }
        final Map<Float, Integer> mapTwo = mapThree.get(vertex.getPosition().getY());
        if (!mapTwo.containsKey(vertex.getPosition().getZ())) {
            mapTwo.put(vertex.getPosition().getZ(), vertices.size());
            vertices.add(vertex);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean containsEdge(int vId, int wId) {
        List<Edge> vOutEdges = outEdges(vId);
        if (vOutEdges == null) {
            return false;
        }

        for (Edge vOutEdge : vOutEdges) {
            if (vOutEdge.getTo().getId() == wId) {
                return true;
            }
        }

        return false;
    }

    @VisibleForTesting
    @Override
    public List<Vertex> getVertices() {
        return vertices;
    }

    @VisibleForTesting
    @Override
    public Map<Vertex, List<Edge>> getEdges() {
        return edges;
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

        throw new IllegalStateException("There is no vertex with id: " + id);
    }
}
