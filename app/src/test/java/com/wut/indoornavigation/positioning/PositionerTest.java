package com.wut.indoornavigation.positioning;

import com.wut.indoornavigation.beacons.IndoorBeaconsManager;
import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.data.model.FloorObject;
import com.wut.indoornavigation.data.model.IndoorBeacon;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.storage.BuildingStorage;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PositionerTest {

    private static final int FLOOR_SIZE = 10;
    private static final int FLOOR_NUMBER = 0;

    private static final List<Floor> FLOOR_LIST;
    private static final Building BUILDING;

    static {
        FLOOR_LIST = new LinkedList<>();
        FLOOR_LIST.add(Floor.builder()
                .number(FLOOR_NUMBER)
                .enumMap(new FloorObject[FLOOR_SIZE][FLOOR_SIZE])
                .build());
        BUILDING = Building.builder()
                .floors(FLOOR_LIST)
                .build();
    }

    @Mock
    BuildingStorage storage;
    @Mock
    IndoorBeaconsManager beaconsManager;

    @InjectMocks
    Positioner positioner;

    @Before
    public void setUp() throws Exception {
        when(storage.getBuilding()).thenReturn(BUILDING);
    }

    @Test
    public void shouldGetInRangeBeaconsWhenGettingUserPosition() {
        //given
        when(beaconsManager.getInRangeBeacons()).thenReturn(new LinkedList<>());
        //when
        positioner.getUserPosition();
        //then
        verify(beaconsManager).getInRangeBeacons();
    }

    @Test
    public void shouldReturnDefaultPointWhenThereIsNoBeaconsInRange() {
        //given
        when(beaconsManager.getInRangeBeacons()).thenReturn(new LinkedList<>());
        //when
        final Point point = positioner.getUserPosition();
        //then
        Assert.assertEquals(point.getX(), Positioner.DEFAULT_X);
        Assert.assertEquals(point.getY(), Positioner.DEFAULT_Y);
    }

    @Test
    public void shouldGetInRangeBeaconsTwiceWhenBeaconsInRange() {
        //given
        final List<IndoorBeacon> beaconList = new LinkedList<>();
        beaconList.add(IndoorBeacon.builder().x(1).y(1).build());
        when(beaconsManager.getInRangeBeacons()).thenReturn(beaconList);
        //when
        positioner.getUserPosition();
        //then
        verify(beaconsManager, times(2)).getInRangeBeacons();
    }

    @Test
    public void shouldGetBuildingFromStorageWhenBeaconsInRange() {
        //given
        final List<IndoorBeacon> beaconList = new LinkedList<>();
        beaconList.add(IndoorBeacon.builder().x(1).y(1).build());
        when(beaconsManager.getInRangeBeacons()).thenReturn(beaconList);
        //when
        positioner.getUserPosition();
        //then
        verify(storage).getBuilding();
    }

    @Test
    public void shouldGetPositionDifferentThanDefaultWhenBeaconsInRange() {
        //given
        final List<IndoorBeacon> beaconList = new LinkedList<>();
        beaconList.add(IndoorBeacon.builder().x(1).y(1).distance(1).build());
        when(beaconsManager.getInRangeBeacons()).thenReturn(beaconList);
        //when
        final Point point = positioner.getUserPosition();
        //then
        Assert.assertFalse(point.getX() == Positioner.DEFAULT_X);
        Assert.assertFalse(point.getY() == Positioner.DEFAULT_Y);
    }
}