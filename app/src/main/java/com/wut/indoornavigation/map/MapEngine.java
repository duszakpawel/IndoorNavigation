package com.wut.indoornavigation.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Point;

import java.util.List;

/**
 * Engine which renders map
 */
public interface MapEngine {

    /**
     * Renders building map
     * @param context activity context
     * @param building building object
     */
    void renderMap(Context context, @NonNull Building building);

    /**
     * Sets {@link OnMapReadyListener}
     * @param onMapReadyListener listener
     */

    void setOnMapReadyListener(OnMapReadyListener onMapReadyListener);

    /**
     * Gets floor numbers
     * @return floor numbers
     */
    List<Integer> getFloorNumbers();

    /**
     * Gets room numbers
     * @return room numbers
     */
    List<Integer> getRoomNumbers();

    /**
     * Gets map for selected floor
     * @param floorNumber selected floor number
     * @return floor map
     */
    @NonNull
    Bitmap getMapForFloor(int floorNumber);

    /**
     * Renders path
     * @param context context object
     * @param path path object
     */
    void renderPath(Context context, List<Point> path);
}
