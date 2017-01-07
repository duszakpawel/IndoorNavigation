package com.wut.indoornavigation.render.path.impl;

import android.graphics.Bitmap;

import com.wut.indoornavigation.data.exception.MapNotFoundException;
import com.wut.indoornavigation.data.graph.Graph;
import com.wut.indoornavigation.data.mesh.MeshProvider;
import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.graph.Vertex;
import com.wut.indoornavigation.data.model.mesh.MeshDetails;
import com.wut.indoornavigation.data.model.mesh.MeshResult;
import com.wut.indoornavigation.data.storage.BuildingStorage;
import com.wut.indoornavigation.render.RenderEngine;
import com.wut.indoornavigation.render.RenderEngineTest;
import com.wut.indoornavigation.render.map.MapEngine;
import com.wut.indoornavigation.render.path.PathFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PathFinderEngineImplTest extends RenderEngineTest {

    private static final Point MOCK_POINT = new Point(0, 0, 0);
    private static final Map<Integer, List<Vertex>> DESTINATION_VERTICES;
    private static final List<Vertex> VERTEX_LIST;
    private static final int DEST_VERTEX_INDEX = 0;
    private static final int ROOM_NUMBER = 0;
    private static final int NUMBER_NOT_EXISTS = -1;

    static {
        DESTINATION_VERTICES = new HashMap<>();
        VERTEX_LIST = new LinkedList<>();
        VERTEX_LIST.add(Vertex.builder().position(new Point(0, 0, 0)).build());
        DESTINATION_VERTICES.put(1, VERTEX_LIST);
        DESTINATION_VERTICES.put(0, VERTEX_LIST);
    }

    @Mock
    MeshProvider meshProvider;
    @Mock
    BuildingStorage storage;
    @Mock
    PathFactory pathFactory;
    @Mock
    MapEngine mapEngine;
    @Mock
    Graph graph;
    @Mock
    Bitmap bitmap;

    @InjectMocks
    PathFinderEngineImpl pathFinder;

    @Override
    protected RenderEngine getRenderEngine() {
        return pathFinder;
    }

    @Before
    public void setUp() {
        super.setUp();
        when(storage.getBuilding()).thenReturn(BUILDING);
        when(mapEngine.getMapForFloor(anyInt())).thenReturn(bitmap);
        when(bitmap.copy(any(), anyBoolean())).thenReturn(bitmap);
        when(pathFactory.getScaledSmoothPath(anyInt(), anyInt(), anyList(), any(), any())).thenReturn(new HashMap<>());
        when(meshProvider.create(any())).thenReturn(new MeshResult(graph, new MeshDetails(DESTINATION_VERTICES)));
        when(graph.aStar(any(), any())).thenReturn(new LinkedList<>());
    }

    @Test
    public void shouldCreateMeshWhilePreparingMesh() {
        //given
        //when
        pathFinder.prepareMesh(BUILDING);
        //then
        verify(meshProvider).create(any(Building.class));
    }

    @Test
    public void shouldBeNoErrorsWhenProperBuildingDelivered() {
        //given
        Exception ex = null;
        //when
        try {
            pathFinder.prepareMesh(BUILDING);
        } catch (Exception e) {
            ex = e;
        }
        //then
        Assert.assertNull(ex);
    }

    @Test
    public void shouldReturnMapNotFoundExceptionWhenThereIsNoFloorWithPathMap() {
        //given
        pathFinder.prepareMesh(BUILDING);
        pathFinder.renderPath(mapEngine, context, MOCK_POINT, FLOOR_NUMBER, DEST_VERTEX_INDEX);
        Exception ex = null;
        //when
        try {
            pathFinder.getMapWithPathForFloor(FLOOR_NUMBER);
        } catch (Exception e) {
            ex = e;
        }
        //then
        Assert.assertTrue(ex instanceof MapNotFoundException);
    }

    @Test
    public void shouldReturnRoomIndexWhenRoomExists() {
        //given
        Exception ex = null;
        int index = NUMBER_NOT_EXISTS;
        //when
        try {
            index = pathFinder.getRoomIndex(ROOM_NUMBER);
        } catch (Exception e) {
            ex = e;
        }
        //then
        Assert.assertNull(ex);
        Assert.assertTrue(index != NUMBER_NOT_EXISTS);
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenGettingRoomIndexRoomNotExists() {
        //given
        Exception ex = null;
        //when
        try {
            pathFinder.getRoomIndex(NUMBER_NOT_EXISTS);
        } catch (Exception e) {
            ex = e;
        }
        //then
        Assert.assertTrue(ex instanceof IllegalArgumentException);
    }

    @Test
    public void shouldGetFloorNumberWhenRoomExists() {
        //given
        Exception ex = null;
        int index = NUMBER_NOT_EXISTS;
        //when
        try {
            index = pathFinder.destinationFloorNumber(ROOM_NUMBER);
        } catch (Exception e) {
            ex = e;
        }
        //then
        Assert.assertNull(ex);
        Assert.assertTrue(index != NUMBER_NOT_EXISTS);
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenGettingFloorNumberAndRoomNotExists() {
        //given
        Exception ex = null;
        //when
        try {
            pathFinder.destinationFloorNumber(NUMBER_NOT_EXISTS);
        } catch (Exception e) {
            ex = e;
        }
        //then
        Assert.assertTrue(ex instanceof IllegalArgumentException);
    }
}