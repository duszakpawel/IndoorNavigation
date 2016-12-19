package com.wut.indoornavigation.render.path;

import android.graphics.Path;
import android.support.annotation.NonNull;

import com.wut.indoornavigation.data.model.Point;

import java.util.List;

public interface PathSmoothingTool {
    /**
     * Returns smoothed path which is interpolation of provided points
     * @param points points list
     * @return curved path
     */
    @NonNull
    Path produceSmoothPath(List<Point> points);
}
