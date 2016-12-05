package com.wut.indoornavigation.data.graph;

import com.wut.indoornavigation.data.mesh.Mesh;
import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Door;
import com.wut.indoornavigation.data.model.Elevator;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.data.model.FloorObject;
import com.wut.indoornavigation.data.model.Stairs;
import com.wut.indoornavigation.data.model.Wall;
import com.wut.indoornavigation.data.model.mesh.MeshResult;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MeshTest {

    @Test
    public void meshTest_Success() {
        Mesh mesh = new Mesh();
        List<Floor> floors=  new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}};
        List<Wall> walls = new ArrayList<>();
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getGraph().verticesCount(), 9);
    }

    @Test
    public void meshTestWithOffset_Success() {
        Mesh mesh = new Mesh();
        List<Floor> floors=  new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}};
        List<Wall> walls = new ArrayList<>();
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getGraph().verticesCount(), 9);
    }

    @Test
    public void meshTestEdges_Success() {
        Mesh mesh = new Mesh();
        List<Floor> floors=  new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}};
        List<Wall> walls = new ArrayList<>();
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getGraph().verticesCount(), 4);
        Assert.assertEquals(result.getGraph().containsEdge(-3, -4), true);
        Assert.assertEquals(result.getGraph().containsEdge(-3, -2), true);
        Assert.assertEquals(result.getGraph().containsEdge(-3, -1), true);
        Assert.assertEquals(result.getGraph().containsEdge(-1, -4), true);
        Assert.assertEquals(result.getGraph().containsEdge(-1, -3), true);
        Assert.assertEquals(result.getGraph().containsEdge(-1, -2), true);
        Assert.assertEquals(result.getGraph().containsEdge(-4, -2), true);
        Assert.assertEquals(result.getGraph().containsEdge(-4, -1), true);
        Assert.assertEquals(result.getGraph().containsEdge(-4, -3), true);
        Assert.assertEquals(result.getGraph().containsEdge(-2, -4), true);
        Assert.assertEquals(result.getGraph().containsEdge(-2, -3), true);
        Assert.assertEquals(result.getGraph().containsEdge(-2, -1), true);
        Assert.assertEquals(result.getGraph().containsEdge(-2, -1), true);
        Assert.assertEquals(result.getGraph().containsEdge(-2, -2), false);
        Assert.assertEquals(result.getGraph().containsEdge(-3, -3), false);
        Assert.assertEquals(result.getGraph().containsEdge(-2, -2), false);
        Assert.assertEquals(result.getGraph().containsEdge(-4, -4), false);
        Assert.assertEquals(result.getGraph().containsEdge(-1, -7), false);
    }

    @Test
    public void meshTestDifferentShape_Success() {
        Mesh mesh = new Mesh();
        List<Floor> floors=  new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.SPACE, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
        };
        List<Wall> walls = new ArrayList<>();
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getGraph().verticesCount(), 25);
    }
}