package com.wut.indoornavigation.render.map.impl;

import com.wut.indoornavigation.data.exception.MapNotFoundException;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.data.model.Room;
import com.wut.indoornavigation.render.RenderEngine;
import com.wut.indoornavigation.render.RenderEngineTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MapEngineImplTest extends RenderEngineTest {

    private final MapEngineImpl mapEngine = new MapEngineImpl();

    @Override
    protected RenderEngine getRenderEngine() {
        return mapEngine;
    }

    @Before
    public void setUp() {
        super.setUp();
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