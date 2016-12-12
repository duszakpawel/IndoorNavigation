package com.wut.indoornavigation.render.path.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;

import com.wut.indoornavigation.R;
import com.wut.indoornavigation.data.graph.PathFinder;
import com.wut.indoornavigation.data.mesh.MeshProvider;
import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.FloorObject;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.graph.Vertex;
import com.wut.indoornavigation.data.model.mesh.MeshResult;
import com.wut.indoornavigation.data.storage.BuildingStorage;
import com.wut.indoornavigation.render.RenderEngine;
import com.wut.indoornavigation.render.path.PathFinderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

/**
 * Path finder engine implementation
 */
public class PathFinderEngineImpl extends RenderEngine implements PathFinderEngine {
    private final Paint pathPaint = new Paint();

    private final SparseArray<Bitmap> pathBitmaps;
    private final MeshProvider meshProvider;
    private final BuildingStorage storage;

    private MeshResult mesh;
    private Building building;

    public PathFinderEngineImpl(MeshProvider meshProvider, BuildingStorage storage) {
        this.meshProvider = meshProvider;
        this.storage = storage;
        pathBitmaps = new SparseArray<>();
    }

    private void init(Context context) {
        building = storage.getBuilding();
        pathPaint.setColor(ContextCompat.getColor(context, R.color.pathColor));

        getMapHeight(context);
        getMapWidth(context);
    }

    @Override
    public void prepareMesh(Building building) {
        mesh = meshProvider.create(building);
    }

    /**
     * Computes path between point and destination point on map
     *
     * @param source                 source point
     * @param destinationFloorIndex  destination floor index in floors list
     * @param destinationVertexIndex destination vertex index in vertices list
     * @return list of scaled points (path)
     */
    private List<Point> computePath(Point source, int destinationFloorIndex,
                                    int destinationVertexIndex, int buildingWidth, int buildingHeight) {
        PathFinder pathFinder = mesh.getGraph();
        // TODO: hardcoded for now, may throw exception
        Vertex start = mesh.getDestinationPoints().get(0).get(0);
        Vertex end = mesh.getDestinationPoints().get(0).get(1);

        List<Vertex> vertexPath = pathFinder.aStar(start, end);

        List<Point> result = new ArrayList<>(vertexPath.size());

        final int stepWidth = calculateStepWidth(buildingWidth);
        final int stepHeight = calculateStepHeight(buildingHeight);

        for (Vertex vertex : vertexPath) {
            Point position = vertex.getPosition();
            Point coordinates = new Point(position.getX() * stepWidth, position.getY() * stepHeight, position.getY());
            result.add(coordinates);
        }

        return result;
    }


    @Override
    public void renderPath(Context context, Point source, int destinationFloorIndex, int destinationVertexIndex) {
        init(context);

        final FloorObject[][] map = building.getFloors().get(0).getEnumMap();
        final int stepWidth = calculateStepWidth(map[0].length);
        final int stepHeight = calculateStepHeight(map.length);

        List<Point> points = computePath(source, destinationFloorIndex, destinationVertexIndex, stepWidth, stepHeight);

        Map<Integer, List<Point>> paths = new HashMap<>();
        List<Integer> keys = new ArrayList<>();
        for (final Point point : points) {
            int floorNumber = (int) point.getZ();
            List<Point> bufor;
            if (paths.containsKey(floorNumber)) {
                bufor = paths.get(floorNumber);
            } else {
                bufor = new ArrayList<>();
                paths.put(floorNumber, bufor);
                keys.add(floorNumber);
            }

            bufor.add(point);
        }

        for (Integer key : keys) {
            final Bitmap bitmap = Bitmap.createBitmap(mapWidth, mapHeight, Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(bitmap);
            Path path = produceCurvedPath(paths.get(key));
            canvas.drawPath(path, pathPaint);

            pathBitmaps.put(key, bitmap);
        }
    }

    private Path produceCurvedPath(List<Point> points) {
        Path path = new Path();
        boolean first = true;
        for (int i = 0; i < points.size(); ) {
            Point point = points.get(i);
            if (first) {
                first = false;
                path.moveTo(point.getX(), point.getY());
                i++;
            } else if (i < points.size() - 1) {
                Point next = points.get(i + 1);
                path.quadTo(point.getX(), point.getY(), next.getX(), next.getY());
                i += 2;
            } else {
                path.lineTo(point.getX(), point.getY());
                i++;
            }
        }

        return path;
    }
}
