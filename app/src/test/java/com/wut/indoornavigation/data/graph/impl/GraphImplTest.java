package com.wut.indoornavigation.data.graph.impl;

import com.wut.indoornavigation.data.graph.HeuristicFunction;
import com.wut.indoornavigation.data.graph.UnionFind;
import com.wut.indoornavigation.data.graph.VertexComparator;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.graph.Vertex;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GraphImplTest {

    private static final List<Vertex> VERTICES;
    private static final List<Vertex> TWO_ITEMS_LIST;

    static {
        VERTICES = new LinkedList<>();
        VERTICES.add(new Vertex(0, new Point(1, 2)));
        VERTICES.add(new Vertex(1, new Point(2, 3)));
        VERTICES.add(new Vertex(2, new Point(3, 4)));
        VERTICES.add(new Vertex(3, new Point(4, 1)));

        TWO_ITEMS_LIST = new LinkedList<>();
        TWO_ITEMS_LIST.add(new Vertex(0, new Point(1, 2)));
        TWO_ITEMS_LIST.add(new Vertex(1, new Point(2, 3)));
    }

    @Mock
    HeuristicFunction heuristicFunction;
    @Mock
    UnionFind unionFind;
    @Mock
    VertexComparator vertexComparator;

    @InjectMocks
    GraphImpl graph;

    @Before
    public void setUp() {
        when(heuristicFunction.execute(anyObject(), anyObject())).thenReturn(0D);
        when(vertexComparator.compare(any(), any())).thenReturn(1);
    }

    @Test
    public void shouldInitializeObjects() {
        //given
        graph.setVertices(VERTICES);
        //when
        graph.aStar(new Vertex(0, new Point(1, 2)), new Vertex(1, new Point(3, 4)));
        //then
        verify(unionFind, times(1)).initialize(anyInt());
        verify(vertexComparator, times(1)).initialize(any(), any(), any());
    }

    @Test
    public void shouldCheckDistanceTwoTimesWhenThereAreTwoVertices() {
        //given
        graph.setVertices(TWO_ITEMS_LIST);
        //when
        graph.aStar(new Vertex(0, new Point(1, 2)), new Vertex(1, new Point(2, 3)));
        //then
        verify(heuristicFunction, times(2)).execute(any(), any());
    }

    @Test
    public void shouldUnionOneTimeWhenThereAreTwoVertices() {
        //given
        graph.setVertices(TWO_ITEMS_LIST);
        //when
        graph.aStar(new Vertex(0, new Point(1, 2)), new Vertex(1, new Point(2, 3)));
        //then
        verify(unionFind, times(1)).union(anyInt());
    }
}