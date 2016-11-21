package com.wut.indoornavigation.logic.graph.impl;


import android.support.annotation.NonNull;

import com.wut.indoornavigation.logic.graph.Graph;
import com.wut.indoornavigation.logic.graph.HeuristicFuction;
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
    public List<Vertex> aStar(Integer s, Integer t, HeuristicFuction heuristicFunction) {
        return null;
    }
}
