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
        VERTICES.add(Vertex.builder().id(0).position(new Point(1, 2, 0)).build());
        VERTICES.add(Vertex.builder().id(1).position(new Point(2, 3, 0)).build());
        VERTICES.add(Vertex.builder().id(2).position(new Point(3, 4, 0)).build());
        VERTICES.add(Vertex.builder().id(3).position(new Point(4, 1, 0)).build());

        TWO_ITEMS_LIST = new LinkedList<>();
        TWO_ITEMS_LIST.add(Vertex.builder().id(0).position(new Point(1, 2, 0)).build());
        TWO_ITEMS_LIST.add(Vertex.builder().id(1).position(new Point(2, 3, 0)).build());
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
        graph.aStar(Vertex.builder().id(0).position(new Point(1, 2, 0)).build(), Vertex.builder().id(1).position(new Point(3, 4, 0)).build());
        //then
        verify(unionFind, times(1)).initialize(anyInt());
        verify(vertexComparator, times(1)).initialize(any(), any(), any());
    }

    @Test
    public void shouldCheckDistanceTwoTimesWhenThereAreTwoVertices() {
        //given
        graph.setVertices(TWO_ITEMS_LIST);
        //when
        graph.aStar(Vertex.builder().id(0).position(new Point(1, 2, 0)).build(), Vertex.builder().id(1).position(new Point(2, 3, 0)).build());
        //then
        verify(heuristicFunction, times(2)).execute(any(), any());
    }

    @Test
    public void shouldUnionOneTimeWhenThereAreTwoVertices() {
        //given
        graph.setVertices(TWO_ITEMS_LIST);
        //when
        graph.aStar(Vertex.builder().id(0).position(new Point(1, 2, 0)).build(), Vertex.builder().id(1).position(new Point(2, 3, 0)).build());
        //then
        verify(unionFind, times(1)).union(anyInt());
    }
}