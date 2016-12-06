package com.wut.indoornavigation.data.graph;

import com.wut.indoornavigation.data.mesh.Mesh;
import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Door;
import com.wut.indoornavigation.data.model.Elevator;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.data.model.FloorObject;
import com.wut.indoornavigation.data.model.Point;
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
        List<Floor> floors = new ArrayList<>();
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
        List<Floor> floors = new ArrayList<>();
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
        List<Floor> floors = new ArrayList<>();
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
        List<Floor> floors = new ArrayList<>();
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

    @Test
    public void meshTestTwoRoomsWithHallway_Success() {
        Mesh mesh = new Mesh();
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}
        };
        List<Wall> walls = new ArrayList<>();
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getGraph().verticesCount(), 36);
    }

    @Test
    public void meshTestNotRectangular_Success() {
        Mesh mesh = new Mesh();
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}
        };
        List<Wall> walls = new ArrayList<>();
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getGraph().verticesCount(), 30);
    }

    @Test
    public void meshTestTwoRoomsWithThinHallwayAndTwoDoors_Success() {
        Mesh mesh = new Mesh();
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}
        };
        List<Wall> walls = new ArrayList<>();
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getGraph().verticesCount(), 24);
    }

    @Test
    public void meshTestTwoRoomsOneHallwayWithDoors_Success() {
        Mesh mesh = new Mesh();
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.DOOR, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.DOOR, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},

        };
        List<Wall> walls = new ArrayList<>();
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getGraph().verticesCount(), 44);
    }

    @Test
    public void meshTestTwoRoomsOneHallwayWithDoorsTestingDoorQuantity_Success() {
        Mesh mesh = new Mesh();
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.DOOR, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.DOOR, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},

        };
        List<Wall> walls = new ArrayList<>();
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getDestinationPoints().get(0).size(), 2);
    }

    @Test
    public void meshTestTwoFloors_Success() {
        Mesh mesh = new Mesh();
        List<Floor> floors = new ArrayList<>();
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
        floors.add(new Floor(groundFloor, 1, walls, doors, stairs, elevators));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getGraph().verticesCount(), 18);
    }

    @Test
    public void meshTestThreeFloors_Success() {
        Mesh mesh = new Mesh();
        List<Floor> floors = new ArrayList<>();
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
        floors.add(new Floor(groundFloor, -1, walls, doors, stairs, elevators));
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators));
        floors.add(new Floor(groundFloor, 1, walls, doors, stairs, elevators));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getGraph().verticesCount(), 27);
    }

    @Test
    public void meshTestTwoFloorsWithOneElevators_Success() {
        Mesh mesh = new Mesh();
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.ELEVATOR, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.CORNER}};
        List<Wall> walls = new ArrayList<>();
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevatorsMinusOne = new ArrayList<>();
        List<Elevator> elevatorsZero = new ArrayList<>();
        Point bot = new Point((float)0.5, (float)1.5, -1);
        Point mid = new Point((float)0.5, (float)1.5, 0);
        Elevator elevatorMinusOne = new Elevator();
        Elevator elevatorZero = new Elevator();
        elevatorMinusOne.setStart(bot);
        elevatorMinusOne.setEnd(mid);
        elevatorZero.setStart(mid);
        elevatorZero.setEnd(bot);
        elevatorsMinusOne.add(elevatorMinusOne);
        elevatorsZero.add(elevatorZero);
        floors.add(new Floor(groundFloor, -1, walls, doors, stairs, elevatorsMinusOne));
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevatorsZero));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building);
        Graph graph = result.getGraph();
        Assert.assertEquals(graph.verticesCount(), 2);
        Assert.assertEquals(graph.containsEdge(-1,-2), true);
        Assert.assertEquals(graph.containsEdge(-2, -1), true);
        Assert.assertEquals(graph.containsEdge(-2, -2), false);
        Assert.assertEquals(graph.containsEdge(-1, -1), false);
    }

    @Test
    public void meshTestTwoFloorsWithTwoElevators_Success() {
        Mesh mesh = new Mesh();
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.ELEVATOR, FloorObject.ELEVATOR, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}};
        List<Wall> walls = new ArrayList<>();
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevatorsMinusOne = new ArrayList<>();
        List<Elevator> elevatorsZero = new ArrayList<>();
        Point bot = new Point((float)0.5, (float)1.5, -1);
        Point mid = new Point((float)0.5, (float)1.5, 0);
        Point bot2 = new Point((float)0.5, (float)2, -1);
        Point mid2 = new Point((float)0.5, (float)2, 0);
        Elevator elevatorMinusOne = new Elevator();
        Elevator elevatorMinusOneSecond = new Elevator();
        Elevator elevatorZero = new Elevator();
        Elevator elevatorZeroSecond = new Elevator();
        elevatorMinusOne.setStart(bot);
        elevatorMinusOne.setEnd(mid);
        elevatorZero.setStart(mid);
        elevatorZero.setEnd(bot);

        elevatorMinusOneSecond.setStart(bot2);
        elevatorMinusOneSecond.setEnd(mid2);
        elevatorZeroSecond.setStart(mid2);
        elevatorZeroSecond.setEnd(bot2);

        elevatorsMinusOne.add(elevatorMinusOne);
        elevatorsZero.add(elevatorZero);

        elevatorsMinusOne.add(elevatorMinusOneSecond);
        elevatorsZero.add(elevatorZeroSecond);

        floors.add(new Floor(groundFloor, -1, walls, doors, stairs, elevatorsMinusOne));
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevatorsZero));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building);
        Graph graph = result.getGraph();
        Assert.assertEquals(graph.verticesCount(), 4);
        Assert.assertEquals(graph.containsEdge(-4, -1), true);
        Assert.assertEquals(graph.containsEdge(-1, -2), true);
        Assert.assertEquals(graph.containsEdge(-1, -3), true);
        Assert.assertEquals(graph.containsEdge(-1, -4), true);
        Assert.assertEquals(graph.containsEdge(-2, -1), true);
        Assert.assertEquals(graph.containsEdge(-2, -3), true);
        Assert.assertEquals(graph.containsEdge(-3, -1), true);
        Assert.assertEquals(graph.containsEdge(-3, -2), true);
        Assert.assertEquals(graph.containsEdge(-2, -2), false);
        Assert.assertEquals(graph.containsEdge(-1, -1), false);
    }

    @Test
    public void meshTestTwoFloorsWithTwoElevatorsSeparated_Success() {
        Mesh mesh = new Mesh();
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.ELEVATOR, FloorObject.SPACE, FloorObject.ELEVATOR, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}};
        List<Wall> walls = new ArrayList<>();
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevatorsMinusOne = new ArrayList<>();
        List<Elevator> elevatorsZero = new ArrayList<>();
        Point bot = new Point((float)0.5, (float)1.5, -1);
        Point mid = new Point((float)0.5, (float)1.5, 0);
        Point bot2 = new Point((float)0.5, (float)2, -1);
        Point mid2 = new Point((float)0.5, (float)2, 0);
        Elevator elevatorMinusOne = new Elevator();
        elevatorMinusOne.setId(1);
        Elevator elevatorMinusOneSecond = new Elevator();
        elevatorMinusOneSecond.setId(2);
        Elevator elevatorZero = new Elevator();
        elevatorZero.setId(1);
        Elevator elevatorZeroSecond = new Elevator();
        elevatorZeroSecond.setId(2);
        elevatorMinusOne.setStart(bot);
        elevatorMinusOne.setEnd(mid);
        elevatorZero.setStart(mid);
        elevatorZero.setEnd(bot);

        elevatorMinusOneSecond.setStart(bot2);
        elevatorMinusOneSecond.setEnd(mid2);
        elevatorZeroSecond.setStart(mid2);
        elevatorZeroSecond.setEnd(bot2);

        elevatorsMinusOne.add(elevatorMinusOne);
        elevatorsZero.add(elevatorZero);

        elevatorsMinusOne.add(elevatorMinusOneSecond);
        elevatorsZero.add(elevatorZeroSecond);


        floors.add(new Floor(groundFloor, -1, walls, doors, stairs, elevatorsMinusOne));
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevatorsZero));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building);
        Graph graph = result.getGraph();
        Assert.assertEquals(graph.verticesCount(), 6);
        Assert.assertEquals(graph.containsEdge(-4, -5), true);
        Assert.assertEquals(graph.containsEdge(-5, -4), true);
        Assert.assertEquals(graph.containsEdge(-5, -6), true);
        Assert.assertEquals(graph.containsEdge(-6, -5), true);
        Assert.assertEquals(graph.containsEdge(-3, -2), true);
        Assert.assertEquals(graph.containsEdge(-2, -3), true);
        Assert.assertEquals(graph.containsEdge(-2, -1), true);
        Assert.assertEquals(graph.containsEdge(-1, -2), true);
        Assert.assertEquals(graph.containsEdge(-6, -3), true);
        Assert.assertEquals(graph.containsEdge(-3, -6), true);
        Assert.assertEquals(graph.containsEdge(-4, -1), true);
        Assert.assertEquals(graph.containsEdge(-1, -4), true);
        Assert.assertEquals(graph.containsEdge(-2, -2), false);
        Assert.assertEquals(graph.containsEdge(-1, -1), false);
    }

    @Test
    public void meshTestTwoFloorsWithTwoStairsSeparated_Success() {
        Mesh mesh = new Mesh();
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.STAIRS, FloorObject.SPACE, FloorObject.STAIRS, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}};
        List<Wall> walls = new ArrayList<>();
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        List<Stairs> stairsMinusOne = new ArrayList<>();
        List<Stairs> stairsZero = new ArrayList<>();
        Stairs stairMinusOne = new Stairs(1, -1);
        stairMinusOne.setId(1);
        Stairs stairMinusOneSecond = new Stairs(2, -1);
        stairMinusOneSecond.setId(2);
        Stairs stairZero = new Stairs(1, 0);
        stairZero.setId(1);
        Stairs stairZeroSecond = new Stairs(2, 0);
        stairZeroSecond.setId(2);

        stairsMinusOne.add(stairMinusOne);
        stairsZero.add(stairZero);

        stairsMinusOne.add(stairMinusOneSecond);
        stairsZero.add(stairZeroSecond);


        floors.add(new Floor(groundFloor, -1, walls, doors, stairsMinusOne, elevators));
        floors.add(new Floor(groundFloor, 0, walls, doors, stairsZero, elevators));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building);
        Graph graph = result.getGraph();
        Assert.assertEquals(graph.verticesCount(), 6);
        Assert.assertEquals(graph.containsEdge(-4, -5), true);
        Assert.assertEquals(graph.containsEdge(-5, -4), true);
        Assert.assertEquals(graph.containsEdge(-5, -6), true);
        Assert.assertEquals(graph.containsEdge(-6, -5), true);
        Assert.assertEquals(graph.containsEdge(-3, -2), true);
        Assert.assertEquals(graph.containsEdge(-2, -3), true);
        Assert.assertEquals(graph.containsEdge(-2, -1), true);
        Assert.assertEquals(graph.containsEdge(-1, -2), true);
        Assert.assertEquals(graph.containsEdge(-6, -3), true);
        Assert.assertEquals(graph.containsEdge(-3, -6), true);
        Assert.assertEquals(graph.containsEdge(-4, -1), true);
        Assert.assertEquals(graph.containsEdge(-1, -4), true);
        Assert.assertEquals(graph.containsEdge(-2, -2), false);
        Assert.assertEquals(graph.containsEdge(-1, -1), false);
    }
}