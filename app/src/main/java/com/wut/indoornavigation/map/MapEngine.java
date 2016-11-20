package com.wut.indoornavigation.map;

import android.support.annotation.NonNull;

import com.wut.indoornavigation.data.model.BuildingData;

public interface MapEngine {

    void renderMap(@NonNull BuildingData someMapInfo); // TODO: 17.11.2016 Need to be changed

    void setOnMapReadyListener(OnMapReadyListener onMapReadyListener);
}
