package com.wut.indoornavigation.logic.graph.impl;


import android.support.annotation.NonNull;

import com.wut.indoornavigation.logic.graph.Graph;
import com.wut.indoornavigation.logic.graph.HeuristicFunction;
import com.wut.indoornavigation.logic.graph.models.Edge;
import com.wut.indoornavigation.logic.graph.models.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GraphImpl implements Graph {
    private List<Vertex> vertices;
    private Map<Vertex, List<Edge>> edges;


    public GraphImpl(@NonNull List<Vertex> vertices) {
        this.vertices = new ArrayList<>();
        this.edges = new HashMap<>();

        int verticesCount = vertices.size();
        for(int i=0; i < verticesCount; i++){
            this.vertices.add(vertices.get(i));
        }
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
    public List<Vertex> outVertices(int vertexId) {
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

        List<Vertex> T = new ArrayList<>();
        for(int i=0; i < verticesCount; i++){
            T.add(vertices.get(i));
        }

        while (!T.isEmpty()) {
            Vertex u = T.get(0);
            for (int i = 1; i < T.size(); i++) {
                if (distance[vertices.indexOf(T.get(i))] + heuristicFunction.Execute(T.get(i), t) <= distance[vertices.indexOf(u)] + heuristicFunction.Execute(T.get(i), t)) {
                    u = T.get(i);
                }
            }
            T.remove(u);

            if (u == t) {
                break;
            }

            List<Vertex> outVertices = outVertices(u.getId());
            for (int w = 0; w < T.size(); ++w) {
                if (outVertices.contains(T.get(w))) {
                    double uwWeight = 0;
                    List<Edge> uOutEdges = edges.get(u);
                    for (Edge uOutEdge : uOutEdges) {
                        if(uOutEdge.getTo() == T.get(w)){
                            uwWeight = uOutEdge.getWeight();
                            break;
                        }
                    }
                    if (distance[vertices.indexOf(T.get(w))] > distance[vertices.indexOf(u)] + uwWeight){
                        distance[vertices.indexOf(T.get(w))] = distance[vertices.indexOf(u)] + uwWeight;
                        previous[vertices.indexOf(T.get(w))] = vertices.indexOf(u);
                    }
                }
            }
        }

        List<Vertex> result = new ArrayList<>();
        int i = t.getId();
        while(i != -1){
            result.add(0, vertices.get(i));
            i = previous[i];
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

    @Override
    public List<Vertex> aStar(int s, int t, HeuristicFunction heuristicFunction) throws Exception {
        Vertex sVertex = findVertex(s);
        Vertex tVertex = findVertex(t);

        if(sVertex == null || tVertex == null){
            throw new Exception("One ore more vertices does not exist in graph.");
        }

        return aStar(sVertex, tVertex, heuristicFunction);
    }
}
