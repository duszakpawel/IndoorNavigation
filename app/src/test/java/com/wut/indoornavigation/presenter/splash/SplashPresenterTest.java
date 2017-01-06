package com.wut.indoornavigation.presenter.splash;

import android.content.Context;

import com.wut.indoornavigation.RxSyncTestRule;
import com.wut.indoornavigation.data.parser.Parser;
import com.wut.indoornavigation.data.storage.BuildingStorage;
import com.wut.indoornavigation.render.map.MapEngine;
import com.wut.indoornavigation.render.path.PathFinderEngine;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SplashPresenterTest {

    private static final String FILE_NAME = "filename";

    @Rule
    public TestRule rule = new RxSyncTestRule();

    @Mock
    MapEngine mapEngine;
    @Mock
    PathFinderEngine pathFinderEngine;
    @Mock
    Parser parser;
    @Mock
    BuildingStorage storage;
    @Mock
    SplashContract.View view;
    @Mock
    Context context;

    @InjectMocks
    SplashPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter.attachView(view);
    }

    @Test
    public void shouldShowMapWhenMapReady() {
        //given
        //when
        presenter.onMapReady();
        //then
        verify(view).showMap();
    }

    @Test
    public void shouldParseMapFileWhilePreparingMap() {
        //given
        //when
        presenter.prepareMap(FILE_NAME, context);
        //then
        verify(parser).parse(anyString());
    }

    @Test
    public void shouldStoreBuildingWhilePreparingMap() {
        //given
        //when
        presenter.prepareMap(FILE_NAME, context);
        //then
        verify(storage).storeBuilding(any());
    }

    @Test
    public void shouldRenderMapWhilePreparingMap() {
        //given
        //when
        presenter.prepareMap(FILE_NAME, context);
        //then
        verify(mapEngine).renderMap(any(), any());
    }

    @Test
    public void shouldHideLoadingViewWhenMapPrepared() {
        //given
        //when
        presenter.prepareMap(FILE_NAME, context);
        //then
        verify(view).hideLoadingView();
    }
}