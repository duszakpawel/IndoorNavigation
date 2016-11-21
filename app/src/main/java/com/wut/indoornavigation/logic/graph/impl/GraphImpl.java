package com.wut.indoornavigation.logic.graph.impl;


import android.support.annotation.NonNull;

import com.wut.indoornavigation.logic.graph.Graph;
import com.wut.indoornavigation.logic.graph.HeuristicFuction;
import com.wut.indoornavigation.logic.graph.models.Edge;
import com.wut.indoornavigation.logic.graph.models.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class GraphImpl implements Graph {
    private List<Vertex> sourceVertexInformation;
    //private List<Edge>
    private Integer[] vertices;
    private HashMap<Integer, List<Integer>> edges;

    public GraphImpl(@NonNull List<Vertex> vertices) {
        this.vertices = new Integer[vertices.size()];
        for(int i=0; i < vertices.size(); i++){
            this.vertices[i] = i;
        }
        this.edges = new HashMap<>();

        sourceVertexInformation = vertices;
    }

    public void AddEdge(Edge edge) {
        Integer vertexIndex = -1;

        for(int i = 0; i < sourceVertexInformation.size(); i++){
            if(sourceVertexInformation.get(i).id.equals(edge.from)){
                vertexIndex = i;
                break;
            }
        }

        if(vertexIndex == -1){
            return;
        }

        if(edges.containsKey(vertexIndex)){
            edges.get(vertexIndex).add(edge.to);
        }else{
            List<Integer> set = new ArrayList<>();
            set.add(edge.to);
            edges.put(vertexIndex, set);
        }
    }

    @Override
    public void AddEdge(Vertex start, Vertex end, double weight) {
        Edge e = new Edge(start.id, end.id, weight);
        AddEdge(e);
    }

    @Override
    public List<Integer> OutVertex(Integer vertex) {
        return edges.get(vertex);
    }

    public void AddEdge(Integer start, Integer end, double weight) {
        Edge edge = new Edge(start, end, weight);
        AddEdge(edge);
    }

    @Override
    public List<Integer> AStar(Integer s, Integer t, HeuristicFuction heuristicFunction) {
        int verticesCount = vertices.length;
        double[] distance = new double[verticesCount];
        int[] previous = new int[verticesCount];

        for (int i = 0; i < verticesCount; i++) {
            distance[i] = Integer.MAX_VALUE;
            previous[i] = -1;
        }

        for(int i=0; i< sourceVertexInformation.size(); i++){
            if(sourceVertexInformation.get(i).id.equals(s)){
                distance[i] = 0;
                break;
            }
        }

        List<Integer> T = new ArrayList<>();
        Collections.addAll(T, vertices);

        while (!T.isEmpty()) {
            int u = 0;
            for (int i = 1; i < T.size(); i++) {
                if (distance[i] + heuristicFunction.Execute(sourceVertexInformation.get(T.get(i)), sourceVertexInformation.get(t)) <= distance[u] + heuristicFunction.Execute(sourceVertexInformation.get(u), sourceVertexInformation.get(t))) {
                    u = T.get(i);
                }
            }
            T.remove(T.indexOf(u));

            if (u == t) {
                break;
            }

            List<Integer> outVertices = OutVertex(u);
            for (int w = 0; w < T.size(); w++) {
                if (outVertices.contains(T.get(w))) {
                    int j = -1;
                    for(int i=0; i< sourceVertexInformation.size(); i++){
                        if(sourceVertexInformation.get(i).id.equals(T.get(w))){
                            j=i;
                            break;
                        }
                    }

                    if (distance[j] > distance[u] + edges.get(u).get(T.get(w))){
                        distance[j] = distance[u] + edges.get(u).get(T.get(w));
                        previous[j] = u;
                    }
                }
            }
            
        }

        List<Integer> result = new ArrayList<>();
        int i = t;
        while(i != -1){
            result.add(0, i);
            i = previous[i];
        }

        return result;
    }
}
