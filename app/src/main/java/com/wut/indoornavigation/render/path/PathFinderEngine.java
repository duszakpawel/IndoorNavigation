package com.wut.indoornavigation.render.path;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.render.map.MapEngine;

/**
 * Path finder engine interface which computes path
 */
public interface PathFinderEngine {

    /**
     * Computes mesh and other components
     *
     * @param building building object
     */
    void prepareMesh(Building building);

    /**
     * Renders path
     *
     * @param mapEngine              map engine
     * @param context                context object
     * @param source                 source point
     * @param destinationFloorIndex  destination floor index
     * @param destinationVertexIndex destination vertex index
     */
    void renderPath(MapEngine mapEngine, Context context, Point source, int destinationFloorIndex, int destinationVertexIndex);

    /**
     * Gets map for with path selected floor
     *
     * @param floorNumber selected floor number
     * @return floor map
     */
    @NonNull
    Bitmap getMapWithPathForFloor(int floorNumber);

    /**
     * Gets room index
     *
     * @param roomNumber room number
     * @return room index
     */
    int getRoomIndex(int roomNumber);

    /**
     * Gets destination floor number
     *
     * @param roomIndex room index
     * @return destination floor number
     */
    int destinationFloorNumber(int roomIndex);
}
