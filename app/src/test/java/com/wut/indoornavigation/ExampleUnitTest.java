package com.wut.indoornavigation;

import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.logic.graph.Graph;
import com.wut.indoornavigation.logic.graph.impl.GraphImpl;
import com.wut.indoornavigation.logic.graph.impl.HeuristicFuctionImpl;
import com.wut.indoornavigation.logic.graph.models.Vertex;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ExampleUnitTest {

    @Test
    public void graphConstructionTest() throws Exception {
        List<Vertex> vertices = new ArrayList<>();
        Vertex A = new Vertex(0, new Point(0,0));
        Vertex B = new Vertex(1, new Point(1, 0));
        Vertex C = new Vertex(2, new Point(1, 1));
        Vertex D = new Vertex(3, new Point(0, 2));
        Vertex E = new Vertex(4, new Point(1, 2));

        vertices.add(A);
        vertices.add(B);
        vertices.add(C);
        vertices.add(D);
        vertices.add(E);

        Graph g = new GraphImpl(vertices);
        g.addEdge(A, B, 1);
        g.addEdge(B, C, 1);
        g.addEdge(A, C, 1.41);
        g.addEdge(C, D, 1.41);
        g.addEdge(C, E, 1);
        g.addEdge(D, E, 1);

        List<Vertex> result = g.aStar(A, E, new HeuristicFuctionImpl());
        Assert.assertEquals(result.size(), 3);

    }
}