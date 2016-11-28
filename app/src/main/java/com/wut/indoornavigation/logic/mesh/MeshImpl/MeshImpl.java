package com.wut.indoornavigation.logic.mesh.MeshImpl;

import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.data.model.FloorObject;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.logic.graph.Graph;
import com.wut.indoornavigation.logic.graph.impl.GraphImpl;
import com.wut.indoornavigation.logic.graph.models.Vertex;
import com.wut.indoornavigation.logic.mesh.Mesh;

import java.util.ArrayList;
import java.util.List;

// TODO: no tests for this class so far
public class MeshImpl implements Mesh{
    private static Integer idSeed;
    @Override
    public Graph create(Building building) {
        idSeed = -1;
        Graph graph = new GraphImpl();
        List<Vertex> destinationVertices = new ArrayList<>();
        List<Vertex> elevatorsVertices = new ArrayList<>();
        List<Vertex> stairsVertices = new ArrayList<>();
        for (Floor floor : building.getFloors()) {
            int floorNumber=  floor.getNumber();
            FloorObject[][] enumMap = floor.getEnumMap();
            boolean[][] visited = new boolean[enumMap[0].length][enumMap[1].length];
            for(int i=0; i<enumMap[0].length; i++){
                for(int j=0; j<enumMap[1].length; j++){
                    visited[i][j] = true;
                    // until the CORNER is reached
                    if(enumMap[i][j] == FloorObject.CORNER){
                        // the cell on the right and bottom from current cell should belong to mesh
                        int x = i+1;
                        int y = j+1;

                        // exception handling
                        if(x == enumMap[0].length || y == enumMap[1].length){
                            return graph;
                        }

                        // processing the vertex
                        Vertex vertex = ProcessCell(x, y, enumMap, floorNumber, visited, graph, destinationVertices, elevatorsVertices, stairsVertices);
                        // and his neighbours
                        ProcessNeighbours(vertex, x, y, enumMap, floorNumber, visited, graph, destinationVertices, elevatorsVertices, stairsVertices);
                    }
                }
            }
        }

        // TODO: link together floors (stairs, elevators)

        return graph;
    }

    private Vertex ProcessCell(int x, int y, FloorObject[][] enumMap, int floorNumber, boolean[][] visited, Graph graph, List<Vertex> destinationVertices, List<Vertex> elevatorsVertices, List<Vertex> stairsVertices) {
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
                destinationVertices.add(vertex);
            }
            if(enumMap[x][y] == FloorObject.ELEVATOR){
                elevatorsVertices.add(vertex);
            }
            if(enumMap[x][y] == FloorObject.STAIRS){
                stairsVertices.add(vertex);
            }
            return vertex;
        }

        return null;
    }

    private void ProcessNeighbours(Vertex vertex, int x, int y, FloorObject[][] enumMap, int floorNumber, boolean[][] visited, Graph graph, List<Vertex> destinationVertices, List<Vertex> elevatorsVertices, List<Vertex> stairsVertices) {
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
                    final double weight = 0.5;
                    graph.addEdge(v, vertex, weight);
                    graph.addEdge(vertex, v, weight);
                    ProcessNeighbours(v, rowNum, colNum, enumMap, floorNumber, visited, graph, destinationVertices, elevatorsVertices, stairsVertices);
                }
            }
        }
    }
}
