package com.wut.indoornavigation.render.path;

import android.graphics.Path;
import android.support.annotation.NonNull;

import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.mesh.MeshResult;

import java.util.List;
import java.util.Map;

/**
 * Path factory which produce path and scale and smooth path
 */
public interface PathFactory {

    /**
     * Returns path based on of provided points
     *
     * @param points points list
     * @return curved path
     */
    @NonNull
    Path producePath(List<Point> points);

    /**
     * Returns scaled and smooth path of points
     *
     * @param stepWidth  x-scale coefficient
     * @param stepHeight y-scale coefficient
     * @param points     points list
     * @param building   building object
     * @param mesh       mesh object
     * @return scaled and smooth path of points
     */
    @NonNull
    Map<Integer, List<Point>> getScaledSmoothPath(int stepWidth, int stepHeight, List<Point> points,
                                                  Building building, MeshResult mesh);
}
