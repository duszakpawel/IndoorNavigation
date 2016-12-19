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
        boolean first = true;
        for(Point point : points){
            if(first){
                first = false;
                path.moveTo(point.getX(), point.getY());
            }
            else{
                path.lineTo(point.getX(), point.getY());
            }
        }
//        List<SplinePoint> extendedPoints = new ArrayList<>();
//        for (Point point : points) {
//            SplinePoint extendedPoint = SplinePoint.builder().dx(DEFAULT_DX_VALUE).dy(DEFAULT_DY_VALUE).x(point.getX()).y(point.getY()).build();
//            extendedPoints.add(extendedPoint);
//        }
//
//        if (extendedPoints.size() > 1) {
//            for (int i = 0; i < extendedPoints.size(); i++) {
//                if (i >= 0) {
//                    SplinePoint point = extendedPoints.get(i);
//
//                    if (i == 0) {
//                        SplinePoint next = extendedPoints.get(i + 1);
//                        point.dx = ((next.x - point.x) / 3);
//                        point.dy = ((next.y - point.y) / 3);
//                    } else if (i == extendedPoints.size() - 1) {
//                        SplinePoint prev = extendedPoints.get(i - 1);
//                        point.dx = ((point.x - prev.x) / 3);
//                        point.dy = ((point.y - prev.y) / 3);
//                    } else {
//                        SplinePoint next = extendedPoints.get(i + 1);
//                        SplinePoint prev = extendedPoints.get(i - 1);
//                        point.dx = ((next.x - prev.x) / 3);
//                        point.dy = ((next.y - prev.y) / 3);
//                    }
//                }
//            }
//        }
//
//        boolean first = true;
//        for (int i = 0; i < extendedPoints.size(); i++) {
//            SplinePoint point = extendedPoints.get(i);
//            if (first) {
//                first = false;
//                path.moveTo(point.x, point.y);
//            } else {
//                SplinePoint prev = extendedPoints.get(i - 1);
//                path.cubicTo(prev.x + prev.dx, prev.y + prev.dy, point.x - point.dx, point.y - point.dy, point.x, point.y);
//            }
//        }

        return path;
    }

    private void simpleBezier(List<Point> points, Path path) {
        boolean first = true;
        for(int i = 0; i < points.size(); i += 2){
            Point point = points.get(i);
            if(first){
                first = false;
                path.moveTo(point.getX(), point.getY());
            }

            else if(i < points.size() - 1){
                Point next = points.get(i + 1);
                path.quadTo(point.getX(), point.getY(), next.getX(), next.getY());
            }
            else{
                path.lineTo(point.getX(), point.getY());
            }
        }
    }
}
