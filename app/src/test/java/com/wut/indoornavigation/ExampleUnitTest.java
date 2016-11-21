package com.wut.indoornavigation;

import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.logic.graph.Graph;
import com.wut.indoornavigation.logic.graph.impl.GraphImpl;
import com.wut.indoornavigation.logic.graph.impl.HeuristicFuctionImpl;
import com.wut.indoornavigation.logic.graph.models.Vertex;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void algTest() throws Exception {
        List<Vertex> vertices = new ArrayList<>();
        Vertex A = new Vertex(0, new Point(0,0));
        Vertex B = new Vertex(1, new Point(1, 0));
        Vertex C = new Vertex(2, new Point(1, 1));

        vertices.add(A);
        vertices.add(B);
        vertices.add(C);

        Graph g = new GraphImpl(vertices);
        g.AddEdge(A, B, 1);
        g.AddEdge(B, C, 1);

        List<Integer> result = g.AStar(0, 2, new HeuristicFuctionImpl());

        List<Integer> a = new ArrayList<>();
    }
}