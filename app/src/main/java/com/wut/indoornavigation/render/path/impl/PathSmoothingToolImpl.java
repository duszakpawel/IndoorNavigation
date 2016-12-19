package com.wut.indoornavigation.render.path.impl;

import android.graphics.Path;
import android.support.annotation.NonNull;

import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.render.path.PathSmoothingTool;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PathSmoothingToolImpl implements PathSmoothingTool{
    private static final float DEFAULT_DX_VALUE = 0;
    private static final float DEFAULT_DY_VALUE = 0;

    @Inject
    public PathSmoothingToolImpl(){

    }

    @NonNull
    @Override
    public Path produceSmoothPath(List<Point> points) {
        Path path = new Path();

        if (points == null) {
            return path;
        }

        List<SplinePoint> splinePoints = new ArrayList<>();
        prepareSplinePoints(points, splinePoints);
        calculateParameters(splinePoints);

        reproducePath(path, splinePoints);

        return path;
    }

    private void reproducePath(Path path, List<SplinePoint> splinePoints) {
        boolean first = true;
        for (int i = 0; i < splinePoints.size(); i++) {
            SplinePoint point = splinePoints.get(i);
            if (first) {
                first = false;
                path.moveTo(point.x, point.y);
            } else {
                SplinePoint prev = splinePoints.get(i - 1);
                path.cubicTo(prev.x + prev.dx, prev.y + prev.dy, point.x - point.dx, point.y - point.dy, point.x, point.y);
            }
        }
    }

    private void calculateParameters(List<SplinePoint> splinePoints) {
        if (splinePoints.size() > 1) {
            for (int i = 0; i < splinePoints.size(); i++) {
                if (i >= 0) {
                    SplinePoint point = splinePoints.get(i);

                    if (i == 0) {
                        SplinePoint next = splinePoints.get(i + 1);
                        point.dx = ((next.x - point.x) / 3);
                        point.dy = ((next.y - point.y) / 3);
                    } else if (i == splinePoints.size() - 1) {
                        SplinePoint prev = splinePoints.get(i - 1);
                        point.dx = ((point.x - prev.x) / 3);
                        point.dy = ((point.y - prev.y) / 3);
                    } else {
                        SplinePoint next = splinePoints.get(i + 1);
                        SplinePoint prev = splinePoints.get(i - 1);
                        point.dx = ((next.x - prev.x) / 3);
                        point.dy = ((next.y - prev.y) / 3);
                    }
                }
            }
        }
    }

    private void prepareSplinePoints(List<Point> points, List<SplinePoint> extendedPoints) {
        for (Point point : points) {
            SplinePoint extendedPoint = SplinePoint.builder().dx(DEFAULT_DX_VALUE).dy(DEFAULT_DY_VALUE).x(point.getX()).y(point.getY()).build();
            extendedPoints.add(extendedPoint);
        }
    }
}
