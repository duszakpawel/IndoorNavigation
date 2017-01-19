package com.wut.indoornavigation.render.position;

import android.graphics.Bitmap;

import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.storage.BuildingStorage;
import com.wut.indoornavigation.render.RenderEngine;
import com.wut.indoornavigation.render.RenderEngineTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PositionEngineTest extends RenderEngineTest {

    @Mock
    BuildingStorage buildingStorage;
    @Mock
    Bitmap bitmap;

    @InjectMocks
    PositionEngine positionEngine;

    @Override
    protected RenderEngine getRenderEngine() {
        return positionEngine;
    }

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldGetBuildingFromStorageWhenRenderingMapPosition() {
        //given
        when(buildingStorage.getBuilding()).thenReturn(BUILDING);
        //when
        positionEngine.renderMapWithUserPosition(bitmap, new Point(0, 0, 0));
        //then
        verify(buildingStorage).getBuilding();
    }
}