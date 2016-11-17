package com.wut.indoornavigation.map.impl;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.wut.indoornavigation.map.MapEngine;
import com.wut.indoornavigation.map.OnMapReadyListener;

public final class MapEngineImpl implements MapEngine {

    private static final int DEFAULT_MAP_WIDTH = 100;
    private static final int DEFAULT_MAP_HEIGHT = 100;

    private final Bitmap mapBitmap;

    private OnMapReadyListener onMapReadyListener = OnMapReadyListener.NULL;

    public MapEngineImpl() {
        mapBitmap = Bitmap.createBitmap(DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT, Bitmap.Config.ARGB_8888);
    }

    @Override
    public void renderMap(@NonNull Object someMapInfo) {
        //// TODO: 17.11.2016

        onMapReadyListener.onMapReady();
    }

    @Override
    public void setOnMapReadyListener(OnMapReadyListener onMapReadyListener) {
        this.onMapReadyListener = onMapReadyListener == null ? OnMapReadyListener.NULL : onMapReadyListener;
    }
}
