package com.wut.indoornavigation.map;

public interface OnMapReadyListener {

    OnMapReadyListener NULL = () -> {/* Do nothing */};

    void onMapReady();
}
