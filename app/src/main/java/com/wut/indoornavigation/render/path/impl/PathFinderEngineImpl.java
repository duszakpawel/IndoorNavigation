package com.wut.indoornavigation.render.path.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;

import com.wut.indoornavigation.R;
import com.wut.indoornavigation.data.graph.PathFinder;
import com.wut.indoornavigation.data.mesh.MeshProvider;
import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.data.model.FloorObject;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.Room;
import com.wut.indoornavigation.data.model.graph.Vertex;
import com.wut.indoornavigation.data.model.mesh.MeshResult;
import com.wut.indoornavigation.data.storage.BuildingStorage;
import com.wut.indoornavigation.render.RenderEngine;
import com.wut.indoornavigation.render.map.MapEngine;
import com.wut.indoornavigation.render.path.PathFinderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Path finder engine implementation
 */
public class PathFinderEngineImpl extends RenderEngine implements PathFinderEngine {
    private final Paint pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

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
        pathPaint.setStyle(Paint.Style.STROKE);
        // TODO: move to resources
        pathPaint.setStrokeWidth(10f);

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
        Vertex end = mesh.getDestinationPoints().get(destinationFloorIndex).get(destinationVertexIndex);

        List<Vertex> vertexPath = pathFinder.aStar(start, end);

        List<Point> result = new ArrayList<>(vertexPath.size());

        final int stepWidth = calculateStepWidth(buildingWidth);
        final int stepHeight = calculateStepHeight(buildingHeight);

        int currentHeight = stepHeight * 2;
        int currentWidth = 0;

        for (Vertex vertex : vertexPath) {
            Point position = vertex.getPosition();
            Point coordinates = new Point(currentWidth + position.getX() * stepWidth, currentHeight + position.getY() * stepHeight, position.getZ());
            result.add(coordinates);
        }
//        result.add(new Point(0,0,0));
//        result.add(new Point(100, 100,0));
//        result.add(new Point(100, 200,0));
//        result.add(new Point(200, 200,0));
//        result.add(new Point(200, 500,0));
//        result.add(new Point(200, 600,0));

        return result;
    }


    @Override
    public void renderPath(MapEngine mapEngine, Context context, Point source, int destinationFloorIndex, int destinationVertexIndex) {
        init(context);

        final FloorObject[][] map = building.getFloors().get(0).getEnumMap();
        final int stepWidth = calculateStepWidth(map[0].length);
        final int stepHeight = calculateStepHeight(map.length);

        List<Point> points = computePath(source, destinationFloorIndex, destinationVertexIndex, stepWidth, stepHeight);

        Map<Integer, List<Point>> paths = new HashMap<>();
        for (final Point point : points) {
            int floorNumber = (int) point.getZ();
            List<Point> bufor;
            if (paths.containsKey(floorNumber)) {
                bufor = paths.get(floorNumber);
            } else {
                bufor = new ArrayList<>();
                paths.put(floorNumber, bufor);
            }

            bufor.add(point);
        }

        for (Floor floor : building.getFloors()) {
            int floorNumber = floor.getNumber();
            final Bitmap bitmap = mapEngine.getMapForFloor(floorNumber).copy(Bitmap.Config.ARGB_8888, true);
            final Canvas canvas = new Canvas(bitmap);
            Path path = produceCurvedPath(paths.get(floorNumber));
            canvas.drawPath(path, pathPaint);

            pathBitmaps.put(floorNumber, bitmap);
        }
    }

    @NonNull
    @Override
    public Bitmap getMapWithPathForFloor(int floorNumber) {
        final Bitmap bitmap = pathBitmaps.get(floorNumber);
        if (bitmap != null) {
            return bitmap;
        }
        throw new IllegalStateException("There is no map for floor: " + floorNumber);
    }

    @Override
    public int getRoomIndex(int roomNumber) {
        for (Floor floor : storage.getBuilding().getFloors()) {
            for (Room room : floor.getRooms()) {
                if(room.getNumber() == roomNumber){
                    return floor.getRooms().indexOf(room);
                }
            }
        }

        throw new IllegalArgumentException("Room index not found for specified room number.");

    }

    @Override
    public int getFloorIndex(int roomNumber) {
        for (Floor floor : storage.getBuilding().getFloors()) {
            for (Room room : floor.getRooms()) {
                if(room.getNumber() == roomNumber){
                    return storage.getBuilding().getFloors().indexOf(floor);
                }
            }
        }

        throw new IllegalArgumentException("Floor index not found for specified room number.");
    }

    private Path produceCurvedPath(List<Point> points) {
        Path path = new Path();

        if(points == null){
            return path;
        }

        if(points.size() == 2){
            Point first = points.get(0);
            Point second = points.get(1);
            path.moveTo(first.getX(), first.getY());
            path.lineTo(second.getX(), second.getY());
        }

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
