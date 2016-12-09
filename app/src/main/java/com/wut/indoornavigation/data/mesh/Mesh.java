package com.wut.indoornavigation.data.mesh;

import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import com.wut.indoornavigation.data.graph.Graph;
import com.wut.indoornavigation.data.graph.HeuristicFunction;
import com.wut.indoornavigation.data.graph.UnionFind;
import com.wut.indoornavigation.data.graph.VertexComparator;
import com.wut.indoornavigation.data.graph.impl.GraphImpl;
import com.wut.indoornavigation.data.model.Building;
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

/**
 * Mesh creator for building
 */
public final class Mesh {
    private static final int ID_SEED_INIT = -1;
    public static final double HORIZONTAL_VERTICAL_EDGE_WEIGHT = 0.5;
    public static final double DIAGONAL_EDGE_WEIGHT = 0.7;
    public static final int EDGE_ELEVATOR_WEIGHT = 5000;
    public static final int EDGE_STAIRS_WEIGHT = 5000;
    private static final int START_POINT_X_SEED = 0;
    private static final int START_POINT_Y_SEED = 0;

    private int idSeed;

    private Map<Integer, List<Vertex>> destinationVerticesDict;
    private Map<Integer, List<Vertex>> elevatorsVerticesDict;
    private Map<Integer, List<Vertex>> stairsVerticesDict;
    private Map<Integer, List<Point>> beaconsDict;

    /**
     * Creates mesh (graph) for building
     * @param building Building object
     * @param heuristicFunction heuristic function handler (to inject into graph)
     * @param unionFind union find structure (to inject into graph)
     * @return
     */
    public MeshResult create(Building building, HeuristicFunction heuristicFunction, UnionFind unionFind) {
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
            int endVertexIndex = findMatchingElevator(elevator, endFloorElevators);

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

    private int findMatchingElevator(Elevator elevator, List<Elevator> endFloorElevators) {
        int endVertexIndex = -1;
        for (int j = 0; j < endFloorElevators.size(); j++) {
            if (endFloorElevators.get(j).getId() == elevator.getId()) {
                endVertexIndex = j;
                break;
            }
        }

        if (endVertexIndex == -1) {
            throw new IllegalStateException("There were no elevator matching current elevator. Description is incorrect.");
        }
        return endVertexIndex;
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
            int endVertexIndex = findMatchingStairsOnFloor(stairs, endFloorStairs);

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

    private int findMatchingStairsOnFloor(Stairs stairs, List<Stairs> endFloorStairs) {
        int endVertexIndex = -1;
        for (int j = 0; j < endFloorStairs.size(); j++) {
            if (endFloorStairs.get(j).getId() == stairs.getId()) {
                endVertexIndex = j;
                break;
            }
        }

        if (endVertexIndex == -1) {
            throw new IllegalStateException("This algorithm is bugged as f*ck.");
        }
        return endVertexIndex;
    }

    @Nullable
    private Vertex processCell(int x, int y, FloorObject[][] enumMap, int floorNumber, boolean[][] visited, Graph graph) {
        if (visited[x][y]) {
            return graph.getVertexByCoordinates((float) x / 2, (float) y / 2, floorNumber);
        }

        visited[x][y] = true;
        if (enumMap[x][y] == FloorObject.SPACE || enumMap[x][y] == FloorObject.DOOR || enumMap[x][y] == FloorObject.ROOM || enumMap[x][y] == FloorObject.STAIRS || enumMap[x][y] == FloorObject.ELEVATOR) {
            Point coordinates = new Point((float) x / 2, (float) y / 2, floorNumber);
            Vertex vertex = new Vertex(idSeed--, coordinates);
            graph.addVertex(vertex);

            List<Vertex> vertices = findVerticesSetByFloorObjectSign(enumMap[x][y], floorNumber);

            if (vertices != null) {
                vertices.add(vertex);
            }

            return vertex;
        }

        if (enumMap[x][y] == FloorObject.BEACON) {
            Point coordinates = new Point((float) x / 2, (float) y / 2, floorNumber);
            List<Point> floorBeacons = beaconsDict.get(floorNumber);
            if (floorBeacons == null) {
                floorBeacons = new ArrayList<>();
                beaconsDict.put(floorNumber, floorBeacons);
            }
            floorBeacons.add(coordinates);
        }

        return null;
    }

    @Nullable
    private List<Vertex> findVerticesSetByFloorObjectSign(FloorObject floorObject, int floorNumber) {
        List<Vertex> vertices = null;
        if (floorObject == FloorObject.ROOM) {
            if (destinationVerticesDict.containsKey(floorNumber)) {
                vertices = destinationVerticesDict.get(floorNumber);
            } else {
                vertices = new ArrayList<>();
                destinationVerticesDict.put(floorNumber, vertices);
            }
        }
        if (floorObject == FloorObject.ELEVATOR) {
            if (elevatorsVerticesDict.containsKey(floorNumber)) {
                vertices = elevatorsVerticesDict.get(floorNumber);
            } else {
                vertices = new ArrayList<>();
                elevatorsVerticesDict.put(floorNumber, vertices);
            }
        }
        if (floorObject == FloorObject.STAIRS) {
            if (stairsVerticesDict.containsKey(floorNumber)) {
                vertices = stairsVerticesDict.get(floorNumber);
            } else {
                vertices = new ArrayList<>();
                stairsVerticesDict.put(floorNumber, vertices);
            }
        }
        return vertices;
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
                    if ((rowNum < x && colNum < y) || (rowNum > x && colNum < y) || (rowNum < x && colNum > y) || (rowNum > x && colNum > y)) {
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
}
