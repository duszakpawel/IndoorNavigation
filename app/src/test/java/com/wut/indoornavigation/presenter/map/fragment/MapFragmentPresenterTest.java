package com.wut.indoornavigation.presenter.map.fragment;

import android.content.Context;
import android.graphics.Bitmap;

import com.wut.indoornavigation.RxSyncTestRule;
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

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MapFragmentPresenterTest {

    private static final int FLOOR_NUMBER = 0;
    private static final int ROOM_NUMBER = 0;
    private static final Bitmap MOCK_BITMAP = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
    private static final List<Integer> FLOOR_LIST;

    static {
        FLOOR_LIST = new LinkedList<>();
        FLOOR_LIST.add(1);
    }

    @Rule
    public TestRule rule = new RxSyncTestRule();

    @Mock
    MapEngine mapEngine;
    @Mock
    PathFinderEngine pathFinderEngine;
    @Mock
    MapFragmentContract.View view;
    @Mock
    Context context;

    @InjectMocks
    MapFragmentPresenter presenter;


    @Before
    public void setUp() throws Exception {
        presenter.attachView(view);
    }

    @Test
    public void shouldGetFloorsFromMapEngineWhenGettingFloorSpinnerData() {
        //given
        when(mapEngine.getFloorNumbers()).thenReturn(new LinkedList<>());
        //when
        presenter.getFloorSpinnerData();
        //then
        verify(mapEngine).getFloorNumbers();
    }

    @Test
    public void shouldGetRoomsFromMapEngineWhenGettingRoomSpinnerData() {
        //given
        when(mapEngine.getRoomNumbers()).thenReturn(new LinkedList<>());
        //when
        presenter.getRoomSpinnerData();
        //then
        verify(mapEngine).getRoomNumbers();
    }

    @Test
    public void shouldGetFloorNumberFromMapEngineWhenFloorSelected() {
        //given
        when(mapEngine.getFloorNumbers()).thenReturn(FLOOR_LIST);
        when(mapEngine.getMapForFloor(anyInt())).thenReturn(MOCK_BITMAP);
        //when
        presenter.floorSelected(FLOOR_NUMBER);
        //then
        verify(mapEngine).getFloorNumbers();
    }

    @Test
    public void shouldShowMapWhenFloorSelected() {
        //given
        when(mapEngine.getFloorNumbers()).thenReturn(FLOOR_LIST);
        when(mapEngine.getMapForFloor(anyInt())).thenReturn(MOCK_BITMAP);
        //when
        presenter.floorSelected(FLOOR_NUMBER);
        //then
        verify(view).showMap(any());
    }

    @Test
    public void shouldShowProgressDialogWhenUserStartsNavigation() {
        //given
        //when
        presenter.startNavigation(context, ROOM_NUMBER, FLOOR_NUMBER);
        //then
        verify(view).showProgressDialog();
    }

    @Test
    public void shouldRenderPathWhenUserStartsNavigation() {
        //given
        //when
        presenter.startNavigation(context, ROOM_NUMBER, FLOOR_NUMBER);
        //then
        verify(pathFinderEngine).renderPath(any(), any(), any(), anyInt(), anyInt());
    }

    @Test
    public void shouldGetMapWithPathUserStartsNavigation() {
        //given
        when(mapEngine.getFloorNumbers()).thenReturn(FLOOR_LIST);
        //when
        presenter.startNavigation(context, ROOM_NUMBER, FLOOR_NUMBER);
        //then
        verify(pathFinderEngine).getMapWithPathForFloor(anyInt());
    }

    @Test
    public void shouldShowMapWhenUserStartsNavigation() {
        //given
        when(mapEngine.getFloorNumbers()).thenReturn(FLOOR_LIST);
        //when
        presenter.startNavigation(context, ROOM_NUMBER, FLOOR_NUMBER);
        //then
        verify(view).showMap(any());
    }

    @Test
    public void shouldHideProgressDialogWhenUserStartsNavigation() {
        //given
        //when
        presenter.startNavigation(context, ROOM_NUMBER, FLOOR_NUMBER);
        //then
        verify(view).hideProgressDialog();
    }

    @Test
    public void shouldGetRoomIndexFromPathFinderEngineWhenEmptyRoomSelected() {
        //given
        when(mapEngine.getMapForFloor(anyInt())).thenReturn(MOCK_BITMAP);
        //when
        presenter.emptyRoomSelected(ROOM_NUMBER);
        //then
        verify(pathFinderEngine).destinationFloorNumber(anyInt());
    }

    @Test
    public void shouldShowMapWhenEmptyRoomSelected() {
        //given
        when(mapEngine.getMapForFloor(anyInt())).thenReturn(MOCK_BITMAP);
        //when
        presenter.emptyRoomSelected(ROOM_NUMBER);
        //then
        verify(view).showMap(any());
    }
}