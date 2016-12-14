package com.wut.indoornavigation.render.map;

/**
 * Listener which reacts on map ready callback
 */
public interface OnMapReadyListener {

    /**
     * Null instance
     */
    OnMapReadyListener NULL = () -> {/* Do nothing */};

    /**
     * Invoked when map is ready
     */
    void onMapReady();
}
