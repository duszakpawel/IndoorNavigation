package com.wut.indoornavigation.render.path;

import android.content.Context;

import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Point;

/**
 * Path finder engine interface which computes path
 */
public interface PathFinderEngine {
    /**
     * Computes mesh and other components
     * @param building building object
     */
    void prepareMesh(Building building);

    /**
     * Renders path
     * @param context context object
     * @param source source point
     * @param destinationFloorIndex destination floor index
     * @param destinationVertexIndex destination vertex index
     */
    void renderPath(Context context, Point source, int destinationFloorIndex, int destinationVertexIndex);
}
