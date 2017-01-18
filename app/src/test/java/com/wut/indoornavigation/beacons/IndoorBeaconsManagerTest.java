package com.wut.indoornavigation.beacons;

import android.content.Context;

import com.estimote.sdk.BeaconManager;
import com.wut.indoornavigation.data.storage.BuildingStorage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class IndoorBeaconsManagerTest {

    @Mock
    Context context;
    @Mock
    BuildingStorage buildingStorage;
    @Mock
    BeaconManager beaconManager;

    @InjectMocks
    IndoorBeaconsManager indoorBeaconsManager;

    @Test
    public void shouldConnectToBeaconManagerWhenStartingDiscoveringBeacons() {
        //given
        //when
        indoorBeaconsManager.startDiscoveringBeacons();
        //then
        verify(beaconManager).connect(any());
    }

    @Test
    public void shouldDisconnectFromBeaconManagerWhenStartingDiscoveringBeacons() {
        //given
        //when
        indoorBeaconsManager.stopDiscoveringBeacons();
        //then
        verify(beaconManager).disconnect();
    }
}