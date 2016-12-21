package com.wut.indoornavigation.render.path;

import android.graphics.Path;
import android.support.annotation.NonNull;

import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.mesh.MeshResult;

import java.util.List;
import java.util.Map;

public interface PathFactory {
    /**
     * Returns path based on of provided points
     * @param points points list
     * @return curved path
     */
    @NonNull
    Path producePath(List<Point> points);

    @NonNull
    Map<Integer, List<Point>> getScaledSmoothPath(int stepWidth, int stepHeight, List<Point> points, Building building, MeshResult mesh);
}
