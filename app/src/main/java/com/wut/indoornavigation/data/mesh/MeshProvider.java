package com.wut.indoornavigation.data.mesh;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import com.wut.indoornavigation.data.graph.Graph;
import com.wut.indoornavigation.data.graph.HeuristicFunction;
import com.wut.indoornavigation.data.graph.UnionFind;
import com.wut.indoornavigation.data.graph.VertexComparator;
import com.wut.indoornavigation.data.graph.impl.GraphImpl;
import com.wut.indoornavigation.data.mesh.processingStrategy.ProcessingStrategy;
import com.wut.indoornavigation.data.mesh.processingStrategy.StrategyProvider;
import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.BuildingObject;
import com.wut.indoornavigation.data.model.Elevator;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.data.model.FloorObject;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.Stairs;
import com.wut.indoornavigation.data.model.graph.Edge;
import com.wut.indoornavigation.data.model.graph.Vertex;
import com.wut.indoornavigation.data.model.mesh.MeshResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * MeshProvider creator for building
 */
public final class MeshProvider extends AsyncTask<Building, Void, MeshResult> {
    /**
     * Edge weight for horizontal or vertical segment in mesh
     */
    public static final double HORIZONTAL_VERTICAL_EDGE_WEIGHT = 0.5;
    /**
     * Edge weight for diagonal segment in mesh
     */
    public static final double DIAGONAL_EDGE_WEIGHT = 0.7;
    /**
     * Edge weight for elevator-elevator edge weight
     */
    public static final int EDGE_ELEVATOR_WEIGHT = 5000;
    /**
     * Edge weight for stairs-stairs edge weight
     */
    public static final int EDGE_STAIRS_WEIGHT = 5000;

    private static final int ID_SEED_INIT = -1;
    private static final int START_POINT_X_SEED = 0;
    private static final int START_POINT_Y_SEED = 0;
    private static final int VERTEX_NOT_FOUND = -1;

    private final StrategyProvider processingStrategyProvider;
    private final HeuristicFunction heuristicFunction;
    private final UnionFind unionFind;

    private int idSeed;

    private Map<Integer, List<Vertex>> destinationVerticesDict;
    private Map<Integer, List<Vertex>> elevatorsVerticesDict;
    private Map<Integer, List<Vertex>> stairsVerticesDict;
    private Map<Integer, List<Point>> beaconsDict;

    @Inject
    public MeshProvider(StrategyProvider strategyProvider, HeuristicFunction heuristicFunction, UnionFind unionFind){
        this.processingStrategyProvider = strategyProvider;
        this.heuristicFunction = heuristicFunction;
        this.unionFind = unionFind;
    }

    /**
     * Creates mesh (graph) for building
     *
     * @param building          Building object
     * @return MeshProvider result object
     */
    public MeshResult create(Building building) {
        init();

        Graph graph = new GraphImpl(heuristicFunction, unionFind, new VertexComparator(heuristicFunction));

        for (Floor floor : building.getFloors()) {
            processFloor(graph, floor);
            prepareVerticesAndBeaconsSets(floor);
        }

        for (Floor floor : building.getFloors()) {
            int floorNumber = floor.getNumber();
            linkStairsOnFloor(building, graph, floor, floorNumber);
            linkElevatorsOnFloor(building, graph, floor, floorNumber);
        }

        unionFind.initialize(graph.verticesCount());

        return new MeshResult(graph, destinationVerticesDict, beaconsDict);
    }

    private void prepareVerticesAndBeaconsSets(Floor floor) {
        List<Vertex> floorDestinationVertices = destinationVerticesDict.get(floor.getNumber());
        List<Vertex> floorStairsVertices = stairsVerticesDict.get(floor.getNumber());
        List<Vertex> floorElevatorsVertices = elevatorsVerticesDict.get(floor.getNumber());
        List<Point> floorBeacons = beaconsDict.get(floor.getNumber());

        Comparator<Vertex> by2dPosition = (v1, v2) -> {
            if (v2.getPosition().getY() - v1.getPosition().getY() == 0) {
                return Math.round(v2.getPosition().getX() - v1.getPosition().getX());
            } else {
                return Math.round(v2.getPosition().getY() - v1.getPosition().getY());
            }
        };


        sortVerticesSetOnFloor(floorDestinationVertices, by2dPosition);
        sortVerticesSetOnFloor(floorStairsVertices, by2dPosition);
        sortVerticesSetOnFloor(floorElevatorsVertices, by2dPosition);

        if (floorBeacons != null) {
            Collections.sort(floorBeacons, (v1, v2) -> {
                if (v2.getY() - v1.getY() == 0) {
                    return Math.round(v2.getX() - v1.getX());
                } else {
                    return Math.round(v2.getY() - v1.getY());
                }
            });
        }
    }

    private void sortVerticesSetOnFloor(List<Vertex> verticesSet, Comparator<Vertex> comparator) {
        if (verticesSet != null) {
            Collections.sort(verticesSet, comparator);
        }
    }

    private void processFloor(Graph graph, Floor floor) {
        int floorNumber = floor.getNumber();
        FloorObject[][] enumMap = floor.getEnumMap();

        int width = enumMap.length;
        int height = enumMap[0].length;

        boolean[][] visited = new boolean[width][height];
        boolean[][] processedNeighbours = new boolean[width][height];

        Pair<Integer, Integer> startPosition = findStartPositionOnFloor(width, height, enumMap);
        int x = startPosition.first;
        int y = startPosition.second;


        if (x == START_POINT_X_SEED || x == width || y == START_POINT_Y_SEED || y == height) {
            throw new RuntimeException("Incorrect building description table.");
        }

        Vertex vertex = processCell(x, y, enumMap, floorNumber, visited, graph);
        processNeighboursOfVertexOnFloor(vertex, x, y, enumMap, floorNumber, visited, processedNeighbours, graph);
    }

    private Pair<Integer, Integer> findStartPositionOnFloor(int width, int height, FloorObject[][] enumMap) {
        int x = START_POINT_X_SEED;
        int y = START_POINT_Y_SEED;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (enumMap[i][j] == FloorObject.CORNER) {
                    return new Pair<>(i + 1, j + 1);
                }
            }
        }

        return new Pair<>(x, y);
    }

    private void init() {
        idSeed = ID_SEED_INIT;
        destinationVerticesDict = new HashMap<>();
        elevatorsVerticesDict = new HashMap<>();
        stairsVerticesDict = new HashMap<>();
        beaconsDict = new HashMap<>();
    }

    private void linkElevatorsOnFloor(Building building, Graph graph, Floor floor, int floorNumber) {
        if (elevatorsVerticesDict.size() == 0) {
            return;
        }

        for (int i = 0; i < elevatorsVerticesDict.get(floorNumber).size(); i++) {
            Elevator elevator = floor.getElevators().get(i);
            linkElevatorOnFloor(building, graph, floorNumber, i, elevator);
        }
    }

    private void linkElevatorOnFloor(Building building, Graph graph, int floorNumber, int i, Elevator elevator) {
        for (int k = floorNumber - 1; k < floorNumber + 2; k += 2) {
            if (building.getFloors().size() < k + 1 || elevatorsVerticesDict.size() < k + 1) {
                continue;
            }

            Floor kFloor = getFloorByNumber(building, k);

            if (kFloor == null) {
                continue;
            }

            List<Elevator> endFloorElevators = kFloor.getElevators();
            List<Vertex> endFloorElevatorsGraphVertices = elevatorsVerticesDict.get(k);
            int endVertexIndex = findMatchingObjectOnFloor(elevator, endFloorElevators);

            Vertex startVertex = elevatorsVerticesDict.get(floorNumber).get(i);
            Vertex endVertex = endFloorElevatorsGraphVertices.get(endVertexIndex);
            addEdgesBetweenElevators(graph, startVertex, endVertex);

            break;
        }
    }

    private void addEdgesBetweenElevators(Graph graph, Vertex startVertex, Vertex endVertex) {
        if (startVertex.getId() != endVertex.getId()) {
            if (!graph.containsEdge(startVertex.getId(), endVertex.getId())) {
                graph.addEdge(new Edge(startVertex, endVertex, EDGE_ELEVATOR_WEIGHT));
            }

            if (!graph.containsEdge(endVertex.getId(), startVertex.getId())) {
                graph.addEdge(new Edge(endVertex, startVertex, EDGE_ELEVATOR_WEIGHT));
            }
        }
    }

    @Nullable
    private Floor getFloorByNumber(Building building, int k) {
        Floor kFloor = null;
        for (Floor f : building.getFloors()) {
            if (f.getNumber() == k) {
                kFloor = f;
                break;
            }
        }

        return kFloor;
    }

    private void linkStairsOnFloor(Building building, Graph graph, Floor floor, int floorNumber) {
        if (stairsVerticesDict.size() == 0) {
            return;
        }

        for (int i = 0; i < stairsVerticesDict.get(floorNumber).size(); i++) {
            Stairs stairs = floor.getStairs().get(i);
            linkStairOnFloor(building, graph, floorNumber, i, stairs);
        }
    }

    private void linkStairOnFloor(Building building, Graph graph, int floorNumber, int i, Stairs stairs) {
        if (stairs.getEndfloor() != floorNumber) {
            int k = stairs.getEndfloor();

            if (building.getFloors().size() < k + 1 || stairsVerticesDict.size() < k + 1) {
                return;
            }

            Floor kFloor = getFloorByNumber(building, k);

            if (kFloor == null) {
                return;
            }

            List<Stairs> endFloorStairs = kFloor.getStairs();

            if (stairs.getEndfloor() == floorNumber) {
                return;
            }

            List<Vertex> endFloorStairsGraphVertices = stairsVerticesDict.get(k);
            int endVertexIndex = findMatchingObjectOnFloor(stairs, endFloorStairs);

            Vertex startVertex = stairsVerticesDict.get(floorNumber).get(i);
            Vertex endVertex = endFloorStairsGraphVertices.get(endVertexIndex);

            listStairOnFloor(graph, startVertex, endVertex);
        }
    }

    private void listStairOnFloor(Graph graph, Vertex startVertex, Vertex endVertex) {
        if (startVertex.getId() != endVertex.getId()) {
            if (!graph.containsEdge(startVertex.getId(), endVertex.getId())) {
                graph.addEdge(new Edge(startVertex, endVertex, EDGE_STAIRS_WEIGHT));
            }
        }
    }

    private <T extends BuildingObject> int findMatchingObjectOnFloor(T object, List<T> endFloorObjects) {
        int endVertexIndex = VERTEX_NOT_FOUND;
        for (int j = 0; j < endFloorObjects.size(); j++) {
            if (endFloorObjects.get(j).getId() == object.getId()) {
                endVertexIndex = j;
                break;
            }
        }

        if (endVertexIndex == VERTEX_NOT_FOUND) {
            throw new IllegalStateException("There were no elevator matching current elevator. Description is incorrect.");
        }

        return endVertexIndex;
    }

    private Vertex processCell(int x, int y, FloorObject[][] enumMap, int floorNumber, boolean[][] visited, Graph graph) {
        if (visited[x][y]) {
            return graph.getVertexByCoordinates((float) x / 2, (float) y / 2, floorNumber);
        }

        visited[x][y] = true;

        ProcessingStrategy strategy = processingStrategyProvider.provideStrategy(enumMap[x][y]);
        Point coordinates = new Point((float) x / 2, (float) y / 2, floorNumber);

        Map<Integer, List<Vertex>> elements;
        switch (enumMap[x][y]) {
            case STAIRS:
                elements = stairsVerticesDict;
                break;
            case ELEVATOR:
                elements = elevatorsVerticesDict;
                break;
            case ROOM:
                elements = destinationVerticesDict;
                break;
            default:
                elements = null;
        }

        Vertex resultVertex = strategy.process(coordinates, elements, floorNumber, graph, idSeed);

        if (resultVertex != null) {
            idSeed--;
        }

        if (enumMap[x][y] == FloorObject.BEACON) {
            List<Point> floorBeacons = beaconsDict.get(floorNumber);
            if (floorBeacons == null) {
                floorBeacons = new ArrayList<>();
                beaconsDict.put(floorNumber, floorBeacons);
            }
            floorBeacons.add(coordinates);
        }

        return resultVertex;
    }

    @SuppressWarnings("ConstantConditions")
    private void processNeighboursOfVertexOnFloor(Vertex vertex, int x, int y, FloorObject[][] enumMap, int floorNumber, boolean[][] visited, boolean[][] processedNeighbours, Graph graph) {
        final int width = enumMap.length;
        final int height = enumMap[0].length;

        int startPosX = (x - 1 < 0) ? x : x - 1;
        int startPosY = (y - 1 < 0) ? y : y - 1;
        int endPosX = (x + 1 > width - 1) ? x : x + 1;
        int endPosY = (y + 1 > height - 1) ? y : y + 1;

        for (int rowNum = startPosX; rowNum <= endPosX; rowNum++) {
            for (int colNum = startPosY; colNum <= endPosY; colNum++) {
                Vertex v = processCell(rowNum, colNum, enumMap, floorNumber, visited, graph);

                if (v != null) {
                    double weight;
                    if (isSegmentDiagonal(x, y, rowNum, colNum)) {
                        weight = DIAGONAL_EDGE_WEIGHT;
                    } else {
                        weight = HORIZONTAL_VERTICAL_EDGE_WEIGHT;
                    }

                    if (v.getId() != vertex.getId() && v.getPosition().getZ() == vertex.getPosition().getZ()) {
                        graph.addEdge(new Edge(v, vertex, weight));
                        graph.addEdge(new Edge(vertex, v, weight));
                    }
                    if (v == vertex) {
                        continue;
                    }

                    if (!processedNeighbours[rowNum][colNum]) {
                        processedNeighbours[rowNum][colNum] = true;
                        processNeighboursOfVertexOnFloor(v, rowNum, colNum, enumMap, floorNumber, visited, processedNeighbours, graph);
                    }
                }
            }
        }
    }

    private boolean isSegmentDiagonal(int x, int y, int xRelative, int yRelative) {
        return (xRelative < x && yRelative < y) || (xRelative > x && yRelative < y) || (xRelative < x && yRelative > y) || (xRelative > x && yRelative > y);
    }

    @Override
    protected MeshResult doInBackground(Building... params) {
        if(params == null || params.length < 1){
            throw new IllegalArgumentException("There was no building provided to convert into mesh.");
        }

        Building building = params[0];

        return create(building);
    }
}
