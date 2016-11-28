package com.wut.indoornavigation.logic.graph.impl;


import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.logic.graph.Graph;
import com.wut.indoornavigation.logic.graph.HeuristicFunction;
import com.wut.indoornavigation.logic.graph.UnionFind;
import com.wut.indoornavigation.logic.graph.models.Edge;
import com.wut.indoornavigation.logic.graph.models.Vertex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;


public class GraphImpl implements Graph {
    private List<Vertex> vertices;
    private Map<Vertex, List<Edge>> edges;

    public GraphImpl() {
        this.vertices = new ArrayList<>();
        this.edges = new HashMap<>();
    }

    public GraphImpl(@NonNull List<Vertex> vertices) {
        this.vertices = new ArrayList<>();
        this.edges = new HashMap<>();

        int verticesCount = vertices.size();
        for(int i=0; i < verticesCount; i++){
            this.vertices.add(vertices.get(i));
        }
    }

    @Override
    public boolean addVertex(Vertex vertex) {
        for (Vertex v : vertices) {
            if(v.getId() == vertex.getId()){
                return false;
            }
        }
        vertices.add(vertex);

        return true;
    }

    @Override
    public boolean addVertex(Integer id, Point coordinates) {
        for (Vertex vertex : vertices) {
            if(vertex.getId() == id){
                return false;
            }
        }
        Vertex v = new Vertex(id, coordinates);
        vertices.add(v);

        return true;
    }

    @Override
    public boolean addEdge(@NonNull Edge edge) {
        Vertex from = edge.getFrom();
        Vertex to = edge.getTo();

        if(vertices.contains(from) && vertices.contains(to)){
            if(!edges.containsKey(from)){
                List<Edge> outEdges = new ArrayList<>();
                outEdges.add(edge);
                edges.put(from, outEdges);

                return true;
            } else{
                List<Edge> fromOutEdges = edges.get(from);
                if(fromOutEdges.contains(edge)){
                    return false;
                }else{
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
    public List<Vertex> outVertices(Vertex vertex) {
        if(vertex != null) {
            List<Edge> outEdges = edges.get(vertex);
            List<Vertex> outVertices=  new ArrayList<>();
            if(outEdges == null || outEdges.size() == 0)
            {
                return outVertices;
            }

            for (Edge outEdge : outEdges) {
                outVertices.add(outEdge.getTo());
            }
            return outVertices;
        } else{
            return new ArrayList<>();
        }
    }

    @Override
    public List<Edge> outEdges(int vertexId) {
        Vertex vertex = null;
        int verticesCount = vertices.size();
        for(int i=0; i < verticesCount; i++){
            Vertex v = vertices.get(i);
            if(v.getId() == vertexId){
                vertex = v;
                break;
            }
        }

        if(vertex != null) {
            return edges.get(vertex);
        } else{
            return new ArrayList<>();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Vertex> aStar(Vertex s, Vertex t, HeuristicFunction heuristicFunction) {
        int verticesCount = verticesCount();
        double[] distance = new double[verticesCount];
        int[] previous = new int[verticesCount];

        for (int i = 0; i < verticesCount; i++) {
            final int infinity = 100000;
            distance[i] = infinity;
            previous[i] = -1;
        }

        int sIndex = vertices.indexOf(s);

        distance[sIndex] = 0;

        UnionFind Close = new UnionFindImpl(verticesCount);
        Comparator<Vertex> comparator = new VertexComparator(heuristicFunction,vertices,distance,t);
        PriorityQueue<Vertex> Open = new PriorityQueue<>(comparator);
        Open.add(s);

        while (!Open.isEmpty()) {
            Vertex u = Open.peek();
            Integer uIndex = vertices.indexOf(u);
            for (Vertex iVertex : Open) {
                Integer iIndex = vertices.indexOf(iVertex);

                if (distance[iIndex] + heuristicFunction.Execute(vertices.get(iIndex), t) <= distance[uIndex] + heuristicFunction.Execute(vertices.get(uIndex), t)) {
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
                    if(uOutEdge.getTo() == wVertex){
                        uwWeight = uOutEdge.getWeight();
                        break;
                    }
                }
                if(!Close.connected(wIndex)){
                    if(!Open.contains(wVertex)){
                        Open.add(wVertex);
                        distance[wIndex] = 100000;
                    }
                    if (distance[wIndex] > distance[uIndex] + uwWeight){
                        distance[wIndex] = distance[uIndex] + uwWeight;
                        previous[wIndex] = uIndex;
                    }
                }
            }
        }

        int i = t.getId();

        return reproducePath(previous, i);
    }

    private List<Vertex> reproducePath(int[] previous, int targetIndex) {
        List<Vertex> result = new ArrayList<>();
        while(targetIndex != -1){
            result.add(0, vertices.get(targetIndex));
            targetIndex = previous[targetIndex];
        }

        return result;
    }

    private Vertex findVertex(int id){
        Vertex v = null;
        for (Vertex vertex : vertices) {
            if(vertex.getId() == id){
                v = vertex;
                break;
            }
        }

        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Vertex> aStar(int s, int t, HeuristicFunction heuristicFunction) {
        Vertex sVertex = findVertex(s);
        Vertex tVertex = findVertex(t);

        if(sVertex == null || tVertex == null){
            throw new RuntimeException("One ore more vertices does not exist in graph.");
        }

        return aStar(sVertex, tVertex, heuristicFunction);
    }

    @Override
    public Vertex getVertexByCoordinates(float x, float y) {
        for (Vertex vertex : vertices) {
            Point coordinates = vertex.getPosition();
            if(coordinates.getX() == x && coordinates.getY() == y){
                return vertex;
            }
        }

        return null;
    }

    private class VertexComparator implements Comparator<Vertex> {
        private HeuristicFunction heuristicFunction;
        private List<Vertex> vertices;
        private double[] distance;
        private Vertex target;

        VertexComparator(HeuristicFunction heuristicFunction, List<Vertex> vertices, double[] distance, Vertex target)
        {
            this.heuristicFunction = heuristicFunction;
            this.vertices = vertices;
            this.distance = distance;
            this.target = target;
        }
        
        @Override
        public int compare(Vertex x, Vertex y)
        {
            double xSum = heuristicFunction.Execute(x, target) + distance[vertices.indexOf(x)];
            double ySum = heuristicFunction.Execute(y, target) + distance[vertices.indexOf(y)];
            return (int)(xSum - ySum);
        }
    }
}
