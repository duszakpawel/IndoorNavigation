package com.wut.indoornavigation.render.map;

public interface OnMapReadyListener {

    OnMapReadyListener NULL = () -> {/* Do nothing */};

    void onMapReady();
}
