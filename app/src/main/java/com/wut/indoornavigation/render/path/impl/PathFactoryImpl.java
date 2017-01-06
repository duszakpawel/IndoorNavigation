package com.wut.indoornavigation.render.path.impl;

import android.graphics.Path;
import android.support.annotation.NonNull;

import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.mesh.MeshResult;
import com.wut.indoornavigation.render.path.PathFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PathFactoryImpl implements PathFactory {

    private static final float Y_MESH_STEP = 0.5f;
    private static final float X_MESH_STEP = 0.5f;

    @NonNull
    @Override
    public Path producePath(List<Point> points) {
        final Path path = new Path();

        if (points == null) {
            return path;
        }
        boolean first = true;
        for (Point point : points) {
            if (first) {
                first = false;
                path.moveTo(point.getX(), point.getY());
            } else {
                path.lineTo(point.getX(), point.getY());
            }
        }

        return path;
    }

    @NonNull
    @Override
    public Map<Integer, List<Point>> getScaledSmoothPath(int stepWidth, int stepHeight, List<Point> points, Building building, MeshResult mesh) {
        final Map<Integer, List<Point>> paths = splitPointsDueToFloorNumber(points);
        Map<Integer, List<Point>> smoothedPaths = new HashMap<>();

        for (Floor floor : building.getFloors()) {
            int floorNumber = floor.getNumber();
            final List<Point> floorPoints = paths.get(floorNumber);

            if (floorPoints == null || floorPoints.isEmpty()) {
                continue;
            }

            final List<Point> passagePoints = mesh.getMeshDetails().getPassageVerticesDict().get(floorNumber);
            smoothedPaths = smoothFloorPoints(mesh, floorNumber, floorPoints, passagePoints);

            final List<Point> pointsToScale = smoothedPaths.get(floorNumber);

            if (pointsToScale == null || pointsToScale.isEmpty()) {
                continue;
            }

            for (int i = 0; i < pointsToScale.size(); i++) {
                final Point scaledPoint = calculateScaledPoint(stepWidth, stepHeight, pointsToScale.get(i));
                pointsToScale.set(i, scaledPoint);
            }
        }

        return smoothedPaths;
    }

    private Map<Integer, List<Point>> smoothFloorPoints(MeshResult mesh, int floorNumber, List<Point> floorPoints, List<Point> passagePoints) {
        final Map<Integer, List<Point>> smoothedPaths = new HashMap<>();

        for (int i = 0; i < floorPoints.size(); ) {
            final List<Point> bufor = new ArrayList<>();
            Point iPoint = floorPoints.get(i);
            bufor.add(iPoint);
            i++;

            if (i >= floorPoints.size()) {
                break;
            }
            iPoint = floorPoints.get(i);
            if (i > 0 && i < floorPoints.size()) {
                bufor.add(iPoint);
            }

            int j = i + 1;
            Point nextPassage = null;
            while (j < floorPoints.size()) {
                if (passagePoints.contains(floorPoints.get(j))) {
                    nextPassage = floorPoints.get(j - 1);
                    break;
                }
                j++;
            }

            if (j == floorPoints.size()) {
                throw new IllegalStateException("No matching passage found.");
            } else {
                bufor.add(nextPassage);
                if (nextPassage == null) {
                    throw new IllegalStateException("Not possible to smooth floor ponts");
                }

                boolean smoothingPossible = isSmoothingSegmentPossible(mesh, floorNumber, iPoint, nextPassage);
                if (smoothingPossible) {
                    appendResultWithSmoothing(floorNumber, smoothedPaths, bufor);
                } else {
                    appendResultWithoutSmoothing(floorNumber, floorPoints, smoothedPaths, i, j);
                }

                i = j;

                if (j == floorPoints.size() - 1) {
                    List<Point> pts = new ArrayList<>();
                    if (!smoothedPaths.containsKey(floorNumber)) {
                        smoothedPaths.put(floorNumber, pts);
                    } else {
                        pts = smoothedPaths.get(floorNumber);
                    }

                    pts.add(floorPoints.get(j));

                    break;
                }
            }
        }

        return smoothedPaths;
    }

    private void appendResultWithoutSmoothing(int floorNumber, List<Point> floorPoints, Map<Integer, List<Point>> smoothedPaths, int i, int j) {
        for (int k = i; k <= j; k++) {
            List<Point> pts = new ArrayList<>();
            if (!smoothedPaths.containsKey(floorNumber)) {
                smoothedPaths.put(floorNumber, pts);
            } else {
                pts = smoothedPaths.get(floorNumber);
            }

            pts.add(floorPoints.get(k));
        }
    }

    private void appendResultWithSmoothing(int floorNumber, Map<Integer, List<Point>> smoothedPaths, List<Point> segment) {
        List<Point> pts = new ArrayList<>();
        for (int i = 0; i < segment.size(); i++) {
            if (!smoothedPaths.containsKey(floorNumber)) {
                smoothedPaths.put(floorNumber, pts);
            } else {
                pts = smoothedPaths.get(floorNumber);
            }

            pts.add(segment.get(i));
        }
    }

    /**
     * Returns information whether smoothing specific segment is possible
     *
     * @param mesh        mesh object
     * @param floorNumber floor number
     * @param start       start point
     * @param end         end point
     * @return true if yes, otherwise no
     */
    private boolean isSmoothingSegmentPossible(MeshResult mesh, int floorNumber, Point start, Point end) {
        boolean smoothed = true;

        for (float row = start.getY() + Y_MESH_STEP; row <= end.getY() - Y_MESH_STEP; row++) {
            for (float col = start.getX() + X_MESH_STEP; col <= end.getX() - X_MESH_STEP; col++) {
                if (mesh.getGraph().getVertexByCoordinates(col, row, floorNumber) == null) {
                    smoothed = false;
                    break;
                }
            }
        }

        return smoothed;
    }

    /**
     * Returns scaled path point
     *
     * @param stepWidth  x-scale coefficient
     * @param stepHeight y-scale coefficient
     * @param position   point
     * @return scaled point
     */
    @NonNull
    private Point calculateScaledPoint(int stepWidth, int stepHeight, Point position) {
        final float xValue = position.getX() * 2 * stepWidth + stepWidth / 2;
        final float yValue = position.getY() * 2 * stepWidth + 2 * stepHeight;

        return new Point(xValue, yValue, position.getZ());
    }

    /**
     * Splits points due to floor number
     *
     * @param points list of points
     * @return dictionary: key is floor number, value is list of points on specific floor
     */
    private Map<Integer, List<Point>> splitPointsDueToFloorNumber(List<Point> points) {
        final Map<Integer, List<Point>> result = new HashMap<>();

        if (points == null || points.isEmpty()) {
            return result;
        }

        for (final Point point : points) {
            int floorNumber = (int) point.getZ();
            List<Point> buffer;

            if (result.containsKey(floorNumber)) {
                buffer = result.get(floorNumber);
            } else {
                buffer = new ArrayList<>();
                result.put(floorNumber, buffer);
            }

            buffer.add(point);
        }

        return result;
    }
}
