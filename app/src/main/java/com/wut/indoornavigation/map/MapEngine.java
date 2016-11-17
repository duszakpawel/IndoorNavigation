package com.wut.indoornavigation.map;

import android.support.annotation.NonNull;

public interface MapEngine {

    void renderMap(@NonNull Object someMapInfo); // TODO: 17.11.2016 Need to be changed

    void setonMapReadyListener(OnMapReadyListener onMapReadyListener);
}
