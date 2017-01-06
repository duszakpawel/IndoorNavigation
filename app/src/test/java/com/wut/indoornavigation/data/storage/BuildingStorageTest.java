package com.wut.indoornavigation.data.storage;

import android.content.SharedPreferences;

import com.squareup.moshi.JsonAdapter;
import com.wut.indoornavigation.data.model.Building;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BuildingStorageTest {

    @Mock
    SharedPreferences preferences;
    @Mock
    JsonAdapter<Building> jsonAdapter;
    @Mock
    SharedPreferences.Editor editor;

    @InjectMocks
    private BuildingStorage storage;

    @Before
    public void setUp() throws Exception {
        when(preferences.getString(anyString(), anyString())).thenReturn("");
        when(preferences.edit()).thenReturn(editor);
        when(jsonAdapter.fromJson(anyString())).thenReturn(Building.builder().build());
    }

    @Test
    public void shouldGetStringFromPreferencesWhenGettingBuilding() {
        //given
        //when
        storage.getBuilding();
        //then
        verify(preferences).getString(anyString(), anyString());
    }

    @Test
    public void shouldConvertFromJsonWhenGettingBuilding() throws Exception {
        //given
        //when
        storage.getBuilding();
        //then
        verify(jsonAdapter).fromJson(anyString());
    }

    @Test
    public void shouldPutStringToPreferencesWhenStoringBuilding() {
        //given
        when(editor.putString(anyString(), anyString())).thenReturn(editor);
        //when
        storage.storeBuilding(Building.builder().build());
        //then
        verify(editor).putString(anyString(), anyString());
        verify(editor).apply();
    }
}