package com.wut.indoornavigation.map;

import android.support.annotation.NonNull;

import com.wut.indoornavigation.data.model.BuildingData;

public interface MapEngine {
    void renderMap(@NonNull BuildingData someMapInfo);
    void setOnMapReadyListener(OnMapReadyListener onMapReadyListener);
}
