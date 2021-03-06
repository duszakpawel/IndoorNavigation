package com.wut.indoornavigation.presenter.map.fragment;

import android.content.Context;
import android.graphics.Bitmap;

import com.wut.indoornavigation.RxSyncTestRule;
import com.wut.indoornavigation.beacons.IndoorBeaconsManager;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.positioning.Positioner;
import com.wut.indoornavigation.render.map.MapEngine;
import com.wut.indoornavigation.render.path.PathFinderEngine;
import com.wut.indoornavigation.render.position.PositionEngine;

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
import static org.mockito.Matchers.anyString;
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
    PositionEngine positionEngine;
    @Mock
    Positioner positioner;
    @Mock
    PathFinderEngine pathFinderEngine;
    @Mock
    MapFragmentContract.View view;
    @Mock
    IndoorBeaconsManager beaconsManager;
    @Mock
    Context context;
    @Mock
    Bitmap bitmap;

    @InjectMocks
    MapFragmentPresenter presenter;


    @Before
    public void setUp() throws Exception {
        when(positioner.getBeaconsManager()).thenReturn(beaconsManager);
        when(positioner.getUserPosition()).thenReturn(new Point(0, 0, 0));
        presenter.attachView(view);
    }

    @Test
    public void shouldStartBeaconsRangingWhenAttachingView() {
        //given
        //when
        //then
        verify(beaconsManager).startDiscoveringBeacons();
    }

    @Test
    public void shouldStopBeaconsRangingWhenAttachingView() {
        //given
        //when
        presenter.detachView(false);
        //then
        verify(beaconsManager).stopDiscoveringBeacons();
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
    public void shouldGetMapWithPathWhenIsNavigationMode() {
        //given
        when(mapEngine.getFloorNumbers()).thenReturn(FLOOR_LIST);
        when(pathFinderEngine.getMapWithPathForFloor(anyInt())).thenReturn(MOCK_BITMAP);
        presenter.isNavigating = true;
        //when
        presenter.floorSelected(FLOOR_NUMBER);
        //then
        verify(pathFinderEngine).getMapWithPathForFloor(anyInt());
    }

    @Test
    public void shouldGetMapFromMapEngineWhenNoNavigationMode() {
        //given
        when(mapEngine.getFloorNumbers()).thenReturn(FLOOR_LIST);
        when(mapEngine.getMapForFloor(anyInt())).thenReturn(MOCK_BITMAP);
        presenter.isNavigating = false;
        //when
        presenter.floorSelected(FLOOR_NUMBER);
        //then
        verify(mapEngine).getMapForFloor(anyInt());
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
    public void shouldCallStartNavigationWhenUserStartsNavigation() {
        //given
        when(mapEngine.getFloorNumbers()).thenReturn(FLOOR_LIST);
        //when
        presenter.startNavigation(context, ROOM_NUMBER, FLOOR_NUMBER);
        //then
        verify(view).startNavigation();
    }

    @Test
    public void shouldHideProgressDialogWhenNavigationStartedCompleted() {
        //given
        //when
        presenter.startNavigation(context, ROOM_NUMBER, FLOOR_NUMBER);
        //then
        verify(view).hideProgressDialog();
    }

    @Test
    public void shouldGetUserPositionWhenStartingUserPositioning() {
        //given
        //when
        presenter.startUserPositioning();
        //then
        verify(positioner).getUserPosition();
    }

    @Test
    public void shouldInitPositioningEngineWhenInitUserPositioningEngineCalled() {
        //given
        //when
        presenter.initUserPositioningEngine(context);
        //then
        verify(positionEngine).init(any());
    }

    @Test
    public void shouldGetRoomIndexFromMapEngineWhenEmptyRoomSelected() {
        //given
        when(mapEngine.getMapForFloor(anyInt())).thenReturn(MOCK_BITMAP);
        //when
        presenter.emptyRoomSelected(ROOM_NUMBER);
        //then
        verify(mapEngine).getMapForFloor(anyInt());
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