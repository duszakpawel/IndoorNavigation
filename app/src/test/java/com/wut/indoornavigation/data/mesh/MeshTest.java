package com.wut.indoornavigation.data.mesh;

import com.wut.indoornavigation.data.graph.Graph;
import com.wut.indoornavigation.data.graph.HeuristicFunction;
import com.wut.indoornavigation.data.graph.UnionFind;
import com.wut.indoornavigation.data.mesh.processingStrategy.StrategyProvider;
import com.wut.indoornavigation.data.model.Beacon;
import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Door;
import com.wut.indoornavigation.data.model.Elevator;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.data.model.FloorObject;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.Stairs;
import com.wut.indoornavigation.data.model.Wall;
import com.wut.indoornavigation.data.model.graph.Edge;
import com.wut.indoornavigation.data.model.mesh.MeshResult;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MeshTest {

    @Mock
    HeuristicFunction heuristicFunction;
    @Mock
    UnionFind unionFind;

    @Test
    public void meshTest_Success() {
        Mesh mesh = new Mesh();
        StrategyProvider strategyProvider = new StrategyProvider();
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
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators, null));
        Building building = new Building(floors);
        MeshResult result = mesh.create(building, strategyProvider, heuristicFunction, unionFind);
        Assert.assertEquals(result.getGraph().verticesCount(), 9);
    }

    @Test
    public void meshTestWithOffset_Success() {
        Mesh mesh = new Mesh();
        StrategyProvider strategyProvider = new StrategyProvider();
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
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators, null));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building, strategyProvider, heuristicFunction, unionFind);
        Assert.assertEquals(result.getGraph().verticesCount(), 9);
    }

    @Test
    public void meshTestEdges_Success() {
        Mesh mesh = new Mesh();
        StrategyProvider strategyProvider = new StrategyProvider();
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
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators, null));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building, strategyProvider, heuristicFunction, unionFind);
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
        StrategyProvider strategyProvider = new StrategyProvider();
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
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators, null));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building, strategyProvider, heuristicFunction, unionFind);
        Assert.assertEquals(result.getGraph().verticesCount(), 25);
    }

    @Test
    public void meshTestTwoRoomsWithHallway_Success() {
        Mesh mesh = new Mesh();
        StrategyProvider strategyProvider = new StrategyProvider();
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
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators, null));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building, strategyProvider, heuristicFunction, unionFind);
        Assert.assertEquals(result.getGraph().verticesCount(), 36);
    }

    @Test
    public void meshTestNotRectangular_Success() {
        Mesh mesh = new Mesh();
        StrategyProvider strategyProvider = new StrategyProvider();
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
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators, null));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building, strategyProvider, heuristicFunction, unionFind);
        Assert.assertEquals(result.getGraph().verticesCount(), 30);
    }

    @Test
    public void meshTestTwoRoomsWithThinHallwayAndTwoDoors_Success() {
        Mesh mesh = new Mesh();
        StrategyProvider strategyProvider = new StrategyProvider();
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
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators, null));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building, strategyProvider, heuristicFunction, unionFind);
        Assert.assertEquals(result.getGraph().verticesCount(), 24);
    }

    @Test
    public void meshTestTwoRoomsOneHallwayWithDoors_Success() {
        Mesh mesh = new Mesh();
        StrategyProvider strategyProvider = new StrategyProvider();
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
        Door firstRoom = new Door(true);
        firstRoom.setId(1);
        doors.add(firstRoom);
        Door secondRoom = new Door(true);
        secondRoom.setId(2);
        doors.add(secondRoom);
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators, null));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building, strategyProvider, heuristicFunction, unionFind);
        Assert.assertEquals(result.getGraph().verticesCount(), 44);
    }

    @Test
    public void meshTestTwoRoomsOneHallwayWithDoorsTestingDestinationDoorQuantity_Success() {
        Mesh mesh = new Mesh();
        StrategyProvider strategyProvider = new StrategyProvider();
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.ROOM, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.ROOM, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},

        };
        List<Wall> walls = new ArrayList<>();
        List<Door> doors = new ArrayList<>();
        Door firstRoom = new Door(true);
        firstRoom.setId(1);
        doors.add(firstRoom);
        Door secondRoom = new Door(true);
        secondRoom.setId(2);
        doors.add(secondRoom);
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators, null));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building, strategyProvider, heuristicFunction, unionFind);
        Assert.assertEquals(result.getDestinationPoints().get(0).size(), 2);
    }

    @Test
    public void meshTestTwoFloors_Success() {
        Mesh mesh = new Mesh();
        StrategyProvider strategyProvider = new StrategyProvider();
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
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators, null));
        floors.add(new Floor(groundFloor, 1, walls, doors, stairs, elevators, null));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building, strategyProvider, heuristicFunction, unionFind);
        Assert.assertEquals(result.getGraph().verticesCount(), 18);
    }

    @Test
    public void meshTestThreeFloors_Success() {
        Mesh mesh = new Mesh();
        StrategyProvider strategyProvider = new StrategyProvider();
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
        floors.add(new Floor(groundFloor, -1, walls, doors, stairs, elevators, null));
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators, null));
        floors.add(new Floor(groundFloor, 1, walls, doors, stairs, elevators, null));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building, strategyProvider, heuristicFunction, unionFind);
        Assert.assertEquals(result.getGraph().verticesCount(), 27);
    }

    @Test
    public void meshTestTwoFloorsWithOneElevators_Success() {
        Mesh mesh = new Mesh();
        StrategyProvider strategyProvider = new StrategyProvider();
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
        floors.add(new Floor(groundFloor, -1, walls, doors, stairs, elevatorsMinusOne, null));
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevatorsZero, null));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building, strategyProvider, heuristicFunction, unionFind);
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
        StrategyProvider strategyProvider = new StrategyProvider();
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
        Elevator elevatorMinusOne = new Elevator();
        Elevator elevatorMinusOneSecond = new Elevator();
        Elevator elevatorZero = new Elevator();
        Elevator elevatorZeroSecond = new Elevator();

        elevatorsMinusOne.add(elevatorMinusOne);
        elevatorsZero.add(elevatorZero);

        elevatorsMinusOne.add(elevatorMinusOneSecond);
        elevatorsZero.add(elevatorZeroSecond);

        floors.add(new Floor(groundFloor, -1, walls, doors, stairs, elevatorsMinusOne, null));
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevatorsZero, null));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building, strategyProvider, heuristicFunction, unionFind);
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
        StrategyProvider strategyProvider = new StrategyProvider();
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
        Elevator elevatorMinusOne = new Elevator();
        elevatorMinusOne.setId(1);
        Elevator elevatorMinusOneSecond = new Elevator();
        elevatorMinusOneSecond.setId(2);
        Elevator elevatorZero = new Elevator();
        elevatorZero.setId(1);
        Elevator elevatorZeroSecond = new Elevator();
        elevatorZeroSecond.setId(2);

        elevatorsMinusOne.add(elevatorMinusOne);
        elevatorsZero.add(elevatorZero);

        elevatorsMinusOne.add(elevatorMinusOneSecond);
        elevatorsZero.add(elevatorZeroSecond);


        floors.add(new Floor(groundFloor, -1, walls, doors, stairs, elevatorsMinusOne, null));
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevatorsZero, null));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building, strategyProvider, heuristicFunction, unionFind);
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
        StrategyProvider strategyProvider = new StrategyProvider();
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.STAIRS, FloorObject.SPACE, FloorObject.STAIRS, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}};
        List<Wall> walls = new ArrayList<>();
        List<Door> doors = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        List<Stairs> stairsMinusOne = new ArrayList<>();
        List<Stairs> stairsZero = new ArrayList<>();
        Stairs stairMinusOne = new Stairs(1, 0);
        stairMinusOne.setId(1);
        Stairs stairMinusOneSecond = new Stairs(2, 0);
        stairMinusOneSecond.setId(2);
        Stairs stairZero = new Stairs(1, -1);
        stairZero.setId(1);
        Stairs stairZeroSecond = new Stairs(2, -1);
        stairZeroSecond.setId(2);

        stairsMinusOne.add(stairMinusOne);
        stairsZero.add(stairZero);

        stairsMinusOne.add(stairMinusOneSecond);
        stairsZero.add(stairZeroSecond);


        floors.add(new Floor(groundFloor, -1, walls, doors, stairsMinusOne, elevators, null));
        floors.add(new Floor(groundFloor, 0, walls, doors, stairsZero, elevators, null));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building, strategyProvider, heuristicFunction, unionFind);
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
    public void meshTestForSample2Building_Success() {
        Mesh mesh = new Mesh();
        StrategyProvider strategyProvider = new StrategyProvider();
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.STAIRS, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.DOOR, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.DOOR, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.DOOR, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.STAIRS, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.ELEVATOR, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.DOOR, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.DOOR, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}
        };

        FloorObject[][] firstFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.STAIRS, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.DOOR, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.DOOR, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.DOOR, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.STAIRS, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.DOOR, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.ELEVATOR, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}
        };

        List<Wall> walls = new ArrayList<>();
        List<Door> doorsZero = new ArrayList<>();
        Door doorZero1 = new Door(true);
        doorZero1.setId(1);
        doorsZero.add(doorZero1);
        Door doorZero2 = new Door(true);
        doorZero2.setId(2);
        doorsZero.add(doorZero2);

        List<Door> doorsOne = new ArrayList<>();
        Door doorOne1 = new Door(true);
        doorOne1.setId(1);
        doorsOne.add(doorOne1);
        Door doorOne2 = new Door(true);
        doorOne2.setId(2);
        doorsOne.add(doorOne2);
        Door doorOne3 = new Door(true);
        doorOne3.setId(2);
        doorsOne.add(doorOne3);
        List<Stairs> stairsZero = new ArrayList<>();
        List<Stairs> stairsOne = new ArrayList<>();

        Stairs stairZero1 = new Stairs(0, 1);
        Stairs stairZero2 = new Stairs(1, 1);
        Stairs stairOne1 = new Stairs(0, 0);
        Stairs stairOne2 = new Stairs(1, 0);
        stairsZero.add(stairZero1);
        stairsZero.add(stairZero2);

        stairsOne.add(stairOne1);
        stairsOne.add(stairOne2);

        List<Elevator> elevatorsOne = new ArrayList<>();
        List<Elevator> elevatorsZero = new ArrayList<>();
        Elevator elevatorOne = new Elevator();
        elevatorOne.setId(1);
        Elevator elevatorZero = new Elevator();
        elevatorZero.setId(1);

        elevatorsZero.add(elevatorZero);
        elevatorsOne.add(elevatorOne);

        floors.add(new Floor(groundFloor, 0, walls, doorsZero, stairsZero, elevatorsZero, null));
        floors.add(new Floor(firstFloor, 1, walls, doorsOne, stairsOne, elevatorsOne, null));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building, strategyProvider, heuristicFunction, unionFind);
        Graph graph = result.getGraph();
        Assert.assertEquals(graph.verticesCount(), 378);

        int edgesAcrossFloorsCount = 0;
        for (List<Edge> edges : graph.getEdges().values()) {
            for (Edge edge : edges) {
                if(edge.getWeight() == Mesh.EDGE_ELEVATOR_WEIGHT){
                    edgesAcrossFloorsCount++;
                }
            }
        }
        Assert.assertEquals(edgesAcrossFloorsCount, 6);
    }

    @Test
    public void meshTestTwoRoomsWithHallwayTestDestinationPoints_Success() {
        Mesh mesh = new Mesh();
        StrategyProvider strategyProvider = new StrategyProvider();
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.BEACON, FloorObject.ROOM, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.BEACON, FloorObject.SPACE, FloorObject.ROOM, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}
        };

        List<Wall> walls = new ArrayList<>();
        List<Door> doors = new ArrayList<>();
        Door door1 = new Door(true);
        Door door2 = new Door(true);
        door1.setId(1);
        door2.setId(2);
        doors.add(door1);
        doors.add(door2);
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        List<Beacon> beacons = new ArrayList<>();
        beacons.add(new Beacon(0));
        beacons.add(new Beacon(1));
        floors.add(new Floor(groundFloor, 0, walls, doors, stairs, elevators, beacons));
        Building building = new Building(floors);

        MeshResult result = mesh.create(building, strategyProvider, heuristicFunction, unionFind);
        Assert.assertEquals(result.getBeaconsDict().get(0).size(), 2);
    }
}