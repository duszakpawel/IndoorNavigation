package com.wut.indoornavigation.render;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.wut.indoornavigation.render.map.impl.MapEngineImpl;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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

    @Mock
    protected Context context;
    @Mock
    protected Resources.Theme theme;
    @Mock
    protected Resources resources;
    @Mock
    protected DisplayMetrics displayMetrics;

    private RenderEngine renderEngine = new MapEngineImpl();

    @Before
    public void setUp() {
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