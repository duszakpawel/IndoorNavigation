package com.wut.indoornavigation.path;

import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Point;

import java.util.List;

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
     * Computes path between point and destination point on map
     * @param source source point
     * @param destinationFloorIndex destination floor index in floors list
     * @param destinationVertexIndex destination vertex index in vertices list
     * @return list of points (path)
     */
    List<Point> computePath(Point source, int destinationFloorIndex, int destinationVertexIndex);
}
