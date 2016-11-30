package com.wut.indoornavigation.logic.mesh.MeshImpl;

import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Elevator;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.data.model.FloorObject;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.Stairs;
import com.wut.indoornavigation.logic.graph.Graph;
import com.wut.indoornavigation.logic.graph.impl.GraphImpl;
import com.wut.indoornavigation.logic.graph.models.Vertex;
import com.wut.indoornavigation.logic.mesh.Mesh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeshImpl implements Mesh{
    private static Integer idSeed;
    @Override
    public Graph create(Building building) {
        idSeed = -1;
        Graph graph = new GraphImpl();
        Map<Integer, List<Vertex>> destinationVerticesDict = new HashMap<>();
        Map<Integer, List<Vertex>> elevatorsVerticesDict = new HashMap<>();
        Map<Integer, List<Vertex>> stairsVerticesDict = new HashMap<>();
        for (Floor floor : building.getFloors()) {
            int floorNumber=  floor.getNumber();
            FloorObject[][] enumMap = floor.getEnumMap();
            boolean[][] visited = new boolean[enumMap[0].length][enumMap[1].length];
            int x = 0;
            int y = 0;
            for(int i=0; i<enumMap[0].length; i++){
                for(int j=0; j<enumMap[1].length; j++){
                    visited[i][j] = true;
                    if(enumMap[i][j] == FloorObject.CORNER){
                        x = i+1;
                        y = j+1;

                        if(x == enumMap[0].length || y == enumMap[1].length){
                            return graph;
                        }
                    }
                }
            }

            Vertex vertex = ProcessCell(x, y, enumMap, floorNumber, visited, graph, destinationVerticesDict, elevatorsVerticesDict, stairsVerticesDict);
            ProcessNeighbours(vertex, x, y, enumMap, floorNumber, visited, graph, destinationVerticesDict, elevatorsVerticesDict, stairsVerticesDict);
        }

        for (Floor floor : building.getFloors()) {
            int floorNumber = floor.getNumber();
            LinkStairsOnFloor(building, graph, stairsVerticesDict, floor, floorNumber);
            LinkElevatorsOnFloor(building, graph, elevatorsVerticesDict, floor, floorNumber);
        }

        return graph;
    }

    private void LinkElevatorsOnFloor(Building building, Graph graph, Map<Integer, List<Vertex>> elevatorsVerticesDict, Floor floor, int floorNumber) {
        for (int i = 0; i < elevatorsVerticesDict.get(floorNumber).size(); i++) {
            Elevator elevator = floor.getElevators().get(i);
            if (elevator.getStart() != elevator.getEnd()) { // loops in graph not needed
                for (int k = floorNumber - 1; k < floorNumber + 2; k += 2) {
                    if(building.getFloors().size() < k + 1 || elevatorsVerticesDict.size() < k + 1){
                        continue;
                    }
                    List<Elevator> endFloorElevators = building.getFloors().get(k).getElevators();
                    List<Vertex> endFloorElevatorsGraphVertices = elevatorsVerticesDict.get(k);

                    Comparator<Vertex> by2dPosition = (v1, v2) -> {
                        if (v1.getPosition().getX() - v2.getPosition().getX() == 0) {
                            return Math.round(v1.getPosition().getY() - v2.getPosition().getY());
                        } else return Math.round(v1.getPosition().getX() - v2.getPosition().getX());
                    };
                    Collections.sort(endFloorElevatorsGraphVertices, by2dPosition);
                    int endVertexIndex = -1;
                    for (int j = 0; j < endFloorElevators.size(); j++) {
                        if (endFloorElevators.get(j).getId() == elevator.getId()) {
                            endVertexIndex = j;
                            break;
                        }
                    }
                    if (endVertexIndex == -1) {
                        throw new IllegalStateException();
                    }
                    Vertex startVertex = endFloorElevatorsGraphVertices.get(i);
                    Vertex endVertex = endFloorElevatorsGraphVertices.get(endVertexIndex);
                    graph.addEdge(startVertex, endVertex, 5000);
                }
            }
        }
    }

    private void LinkStairsOnFloor(Building building, Graph graph, Map<Integer, List<Vertex>> stairsVerticesDict, Floor floor, int floorNumber) {
        for (int i = 0; i < stairsVerticesDict.get(floorNumber).size(); i++) {
            Stairs stairs = floor.getStairs().get(i);
            if (stairs.getStart() != stairs.getEnd()) { // loops in graph not needed
                List<Stairs> endFloorStairs = building.getFloors().get(stairs.getEndfloor()).getStairs();
                List<Vertex> endFloorStairsGraphVertices = stairsVerticesDict.get(stairs.getEndfloor());

                int endVertexIndex = -1;
                for (int j = 0; j < endFloorStairs.size(); j++) {
                    if(endFloorStairs.get(j).getId() == stairs.getEndid()){
                        endVertexIndex = j;
                        break;
                    }
                }
                if(endVertexIndex == -1){
                    throw new IllegalStateException();
                }
                Vertex startVertex = endFloorStairsGraphVertices.get(i);
                Vertex endVertex = endFloorStairsGraphVertices.get(endVertexIndex);
                graph.addEdge(startVertex, endVertex, 5000);
            }
        }
    }

    private Vertex ProcessCell(int x, int y, FloorObject[][] enumMap, int floorNumber, boolean[][] visited, Graph graph, Map<Integer, List<Vertex>> destinationVertices, Map<Integer, List<Vertex>> elevatorsVertices, Map<Integer, List<Vertex>> stairsVertices) {
        if(visited[x][y]){
            return graph.getVertexByCoordinates(x/2, y/2);
        }

        visited[x][y]= true;
        Vertex vertex;
        if(enumMap[x][y] == FloorObject.SPACE || enumMap[x][y] == FloorObject.DOOR || enumMap[x][y] == FloorObject.STAIRS || enumMap[x][y] == FloorObject.ELEVATOR){
            Point coordinates = new Point(x/2, y/2, floorNumber);
            vertex = new Vertex(idSeed--, coordinates);
            graph.addVertex(vertex);
            if(enumMap[x][y] == FloorObject.DOOR){
                List<Vertex> vertices = destinationVertices.get(floorNumber);
                if(vertices == null){
                    vertices = new ArrayList<>();
                }
                vertices.add(vertex);
            }
            if(enumMap[x][y] == FloorObject.ELEVATOR){
                List<Vertex> vertices = elevatorsVertices.get(floorNumber);
                if(vertices == null){
                    vertices = new ArrayList<>();
                }
                vertices.add(vertex);
            }
            if(enumMap[x][y] == FloorObject.STAIRS){
                List<Vertex> vertices = stairsVertices.get(floorNumber);
                if(vertices == null){
                    vertices = new ArrayList<>();
                }
                vertices.add(vertex);
            }
            return vertex;
        }

        return null;
    }

    private void ProcessNeighbours(Vertex vertex, int x, int y, FloorObject[][] enumMap, int floorNumber, boolean[][] visited, Graph graph, Map<Integer, List<Vertex>> destinationVertices, Map<Integer, List<Vertex>> elevatorsVertices, Map<Integer, List<Vertex>> stairsVertices) {
        final int width = enumMap[0].length;
        final int height = enumMap[1].length;

        int startPosX = (x - 1 < 0) ? x : x-1;
        int startPosY = (y - 1 < 0) ? y : y-1;
        int endPosX =   (x + 1 > width-1) ? x : x+1;
        int endPosY =   (y + 1 > height-1) ? y : y+1;

        for (int rowNum=startPosX; rowNum<=endPosX; rowNum++) {
            for (int colNum=startPosY; colNum<=endPosY; colNum++) {
                Vertex v = ProcessCell(rowNum, colNum, enumMap, floorNumber, visited, graph, destinationVertices, elevatorsVertices, stairsVertices);

                if (v != null) {
                    double weight;
                    if((rowNum < x && colNum < y) || (rowNum > x && colNum < y) || (rowNum < x && colNum > y) || (rowNum > x && colNum > y)){
                        weight = 0.7;
                    }
                    else{
                        weight = 0.5;
                    }
                    graph.addEdge(v, vertex, weight);
                    graph.addEdge(vertex, v, weight);
                    ProcessNeighbours(v, rowNum, colNum, enumMap, floorNumber, visited, graph, destinationVertices, elevatorsVertices, stairsVertices);
                }
            }
        }
    }
}
