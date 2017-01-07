package com.wut.indoornavigation.render;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.data.model.FloorObject;
import com.wut.indoornavigation.data.model.Room;
import com.wut.indoornavigation.render.map.impl.MapEngineImpl;

import junit.framework.Assert;

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
public class RenderEngineTest {

    private static final int DISPLAY_SIZE = 100;
    private static final int BUILDING_WIDTH = 5;
    private static final int BUILDING_HEIGHT = 7;
    private static final float DIMENSION_MOCK = 10f;
    private static final int ROOM_COUNT = 10;
    private static final int FLOOR_SIZE = 10;
    protected static final int FLOOR_NUMBER = 0;
    protected static final List<Room> ROOM_LIST = new LinkedList<>();
    protected static final List<Floor> FLOOR_LIST = new LinkedList<>();
    protected static final Building BUILDING;

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
    protected Context context;
    @Mock
    protected Resources.Theme theme;
    @Mock
    protected Resources resources;
    @Mock
    protected DisplayMetrics displayMetrics;

    private RenderEngine renderEngine;

    protected RenderEngine getRenderEngine() {
        return new MapEngineImpl();
    }

    @Before
    public void setUp() {
        this.renderEngine = getRenderEngine();
        when(context.getResources()).thenReturn(resources);
        when(resources.getDisplayMetrics()).thenReturn(displayMetrics);
        when(resources.getDimension(anyInt())).thenReturn(DIMENSION_MOCK);
        when(context.getTheme()).thenReturn(theme);
        when(theme.resolveAttribute(anyInt(), any(), anyBoolean())).thenReturn(true);
        displayMetrics.heightPixels = DISPLAY_SIZE;
        displayMetrics.widthPixels = DISPLAY_SIZE;
    }

    @Test
    public void shouldProperCalculateMapWidth() {
        //given
        //when
        renderEngine.calculateMapWidth(context);
        //then
        Assert.assertEquals(renderEngine.mapWidth, (int) (DISPLAY_SIZE - 2 * DIMENSION_MOCK));
    }

    @Test
    public void shouldProperCalculateMapHeight() {
        //given
        //when
        renderEngine.calculateMapHeight(context);
        //then
        Assert.assertEquals(renderEngine.mapHeight, (int) (DISPLAY_SIZE - 3 * DIMENSION_MOCK));
    }

    @Test
    public void shouldProperCalculateStepWidth() {
        //given
        //when
        final int calculatedWidth = renderEngine.calculateStepWidth(BUILDING_WIDTH);
        //then
        Assert.assertEquals(calculatedWidth, renderEngine.mapWidth / BUILDING_WIDTH);
    }

    @Test
    public void shouldProperCalculateStepHeight() {
        //given
        //when
        final int calculatedHeight = renderEngine.calculateStepHeight(BUILDING_HEIGHT);
        //then
        Assert.assertEquals(calculatedHeight, renderEngine.mapWidth / BUILDING_HEIGHT);
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenCannotResolveToolbar() {
        //given
        when(theme.resolveAttribute(anyInt(), any(), anyBoolean())).thenReturn(false);
        Exception ex = null;
        //when
        try {
            renderEngine.calculateMapHeight(context);
        } catch (Exception e) {
            ex = e;
        }
        //then
        org.junit.Assert.assertNotNull(ex);
        org.junit.Assert.assertTrue(ex instanceof IllegalStateException);
    }
}