package com.wut.indoornavigation.render.map;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.wut.indoornavigation.data.exception.MapNotFoundException;
import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.data.model.FloorObject;
import com.wut.indoornavigation.data.model.Room;
import com.wut.indoornavigation.render.map.impl.MapEngineImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MapEngineTest {

    private static final int FLOOR_NUMBER = 0;
    private static final int ROOM_COUNT = 10;
    private static final int FLOOR_SIZE = 10;
    private static final List<Room> ROOM_LIST = new LinkedList<>();
    private static final List<Floor> FLOOR_LIST = new LinkedList<>();
    private static final Building BUILDING;

    static {
        for (int i = 0; i < ROOM_COUNT; i++) {
            ROOM_LIST.add(Room.builder()
                    .number(i)
                    .build());
        }
        FLOOR_LIST.add(Floor.builder()
                .number(FLOOR_NUMBER)
                .enumMap(new FloorObject[FLOOR_SIZE][FLOOR_SIZE])
                .rooms(ROOM_LIST)
                .build());
        BUILDING = Building.builder()
                .floors(FLOOR_LIST)
                .build();
    }

    @Mock
    Context context;
    @Mock
    Resources.Theme theme;
    @Mock
    Resources resources;
    @Mock
    DisplayMetrics displayMetrics;

    private MapEngine mapEngine = new MapEngineImpl();

    @Before
    public void setUp() {
        when(context.getResources()).thenReturn(resources);
        when(context.getTheme()).thenReturn(theme);
        when(theme.resolveAttribute(anyInt(), any(), anyBoolean())).thenReturn(true);
        when(resources.getDisplayMetrics()).thenReturn(displayMetrics);
        displayMetrics.heightPixels = 100;
        displayMetrics.widthPixels = 100;
    }


    @Test
    public void shouldBeNoErrorsWhenProperBuildingDelivered() {
        //given
        Exception ex = null;
        //when
        try {
            mapEngine.renderMap(context, BUILDING);
        } catch (Exception e) {
            ex = e;
        }
        //then
        Assert.assertNull(ex);
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenCannotResolveToolbar() {
        //given
        when(theme.resolveAttribute(anyInt(), any(), anyBoolean())).thenReturn(false);
        Exception ex = null;
        //when
        try {
            mapEngine.renderMap(context, BUILDING);
        } catch (Exception e) {
            ex = e;
        }
        //then
        Assert.assertNotNull(ex);
        Assert.assertTrue(ex instanceof IllegalStateException);
    }

    @Test
    public void shouldReturnProperFloorNumbersWhenMapRendered() {
        //given
        mapEngine.renderMap(context, BUILDING);
        //when
        final List<Integer> floorNumbers = mapEngine.getFloorNumbers();
        //then
        Assert.assertTrue(floorNumbers.size() == FLOOR_LIST.size());
        for (final Floor floor : FLOOR_LIST) {
            Assert.assertTrue(floorNumbers.contains(floor.getNumber()));
        }
    }

    @Test
    public void shouldReturnProperRoomNumbersWhenMapRendered() {
        //given
        mapEngine.renderMap(context, BUILDING);
        //when
        final List<Integer> roomNumbers = mapEngine.getRoomNumbers();
        //then
        Assert.assertTrue(roomNumbers.size() == ROOM_LIST.size());
        for (final Room room : ROOM_LIST) {
            Assert.assertTrue(roomNumbers.contains(room.getNumber()));
        }
    }

    @Test
    public void shouldReturnMapNotFoundExceptionWhenThereIsNoFloorMap() {
        //given
        mapEngine.renderMap(context, BUILDING);
        Exception ex = null;
        //when
        try {
            mapEngine.getMapForFloor(FLOOR_NUMBER);
        } catch (Exception e) {
            ex = e;
        }
        //then
        Assert.assertTrue(ex instanceof MapNotFoundException);
    }
}