package com.wut.indoornavigation.map;

import android.support.annotation.NonNull;

import com.wut.indoornavigation.data.model.Building;

public interface MapEngine {
    void renderMap(@NonNull Building building);
    void setOnMapReadyListener(OnMapReadyListener onMapReadyListener);
}
