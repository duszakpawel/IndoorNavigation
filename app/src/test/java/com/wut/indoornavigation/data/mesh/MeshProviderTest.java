package com.wut.indoornavigation.data.mesh;

import com.wut.indoornavigation.data.graph.Graph;
import com.wut.indoornavigation.data.graph.HeuristicFunction;
import com.wut.indoornavigation.data.graph.UnionFind;
import com.wut.indoornavigation.data.graph.impl.GraphImpl;
import com.wut.indoornavigation.data.mesh.processingStrategy.StrategyProvider;
import com.wut.indoornavigation.data.model.IndoorBeacon;
import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Door;
import com.wut.indoornavigation.data.model.Elevator;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.data.model.FloorObject;
import com.wut.indoornavigation.data.model.Stairs;
import com.wut.indoornavigation.data.model.graph.Edge;
import com.wut.indoornavigation.data.model.graph.Vertex;
import com.wut.indoornavigation.data.model.mesh.MeshResult;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MeshProviderTest {

    @Mock
    HeuristicFunction heuristicFunction;
    @Mock
    UnionFind unionFind;
    @Mock
    StrategyProvider strategyProvider;

    @InjectMocks
    GraphImpl graph;

    @Before
    public void setUp() {
        when(heuristicFunction.execute(anyObject(), anyObject())).thenCallRealMethod();
        when(strategyProvider.provideStrategy(anyObject())).thenCallRealMethod();
    }

    @Test
    public void meshTest_Success() {
        MeshProvider mesh = new MeshProvider(strategyProvider, heuristicFunction, unionFind);
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}};
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(Floor.builder().enumMap(groundFloor).number(0).doors(doors).stairs(stairs).elevators(elevators).build());
        Building building = Building.builder().floors(floors).build();
        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getGraph().verticesCount(), 9);
    }

    @Test
    public void meshTestWithOffset_Success() {
        MeshProvider mesh = new MeshProvider(strategyProvider, heuristicFunction, unionFind);
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}};
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(Floor.builder().enumMap(groundFloor).number(0).doors(doors).stairs(stairs).elevators(elevators).build());
        Building building = Building.builder().floors(floors).build();

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getGraph().verticesCount(), 9);
    }

    @Test
    public void meshTestEdges_Success() {
        MeshProvider mesh = new MeshProvider(strategyProvider, heuristicFunction, unionFind);
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}};
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(Floor.builder().enumMap(groundFloor).number(0).doors(doors).stairs(stairs).elevators(elevators).build());
        Building building = Building.builder().floors(floors).build();

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
        MeshProvider mesh = new MeshProvider(strategyProvider, heuristicFunction, unionFind);
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
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(Floor.builder().enumMap(groundFloor).number(0).doors(doors).stairs(stairs).elevators(elevators).build());
        Building building = Building.builder().floors(floors).build();

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getGraph().verticesCount(), 25);
    }

    @Test
    public void meshTestTwoRoomsWithHallway_Success() {
        MeshProvider mesh = new MeshProvider(strategyProvider, heuristicFunction, unionFind);
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}
        };
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(Floor.builder().enumMap(groundFloor).number(0).doors(doors).stairs(stairs).elevators(elevators).build());
        Building building = Building.builder().floors(floors).build();

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getGraph().verticesCount(), 36);
    }

    @Test
    public void meshTestNotRectangular_Success() {
        MeshProvider mesh = new MeshProvider(strategyProvider, heuristicFunction, unionFind);
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}
        };
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(Floor.builder().enumMap(groundFloor).number(0).doors(doors).stairs(stairs).elevators(elevators).build());
        Building building = Building.builder().floors(floors).build();

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getGraph().verticesCount(), 30);
    }

    @Test
    public void meshTestTwoRoomsWithThinHallwayAndTwoDoors_Success() {
        MeshProvider mesh = new MeshProvider(strategyProvider, heuristicFunction, unionFind);
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}
        };
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(Floor.builder().enumMap(groundFloor).number(0).doors(doors).stairs(stairs).elevators(elevators).build());
        Building building = Building.builder().floors(floors).build();

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getGraph().verticesCount(), 24);
    }

    @Test
    public void meshTestTwoRoomsOneHallwayWithDoors_Success() {
        MeshProvider mesh = new MeshProvider(strategyProvider, heuristicFunction, unionFind);
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
        List<Door> doors = new ArrayList<>();
        Door firstRoom = Door.builder().destinationPoint(true).id(1).build();
        doors.add(firstRoom);
        Door secondRoom = Door.builder().destinationPoint(true).id(2).build();
        doors.add(secondRoom);
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(Floor.builder().enumMap(groundFloor).number(0).doors(doors).stairs(stairs).elevators(elevators).build());
        Building building = Building.builder().floors(floors).build();

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getGraph().verticesCount(), 44);
    }

    @Test
    public void meshTestTwoRoomsOneHallwayWithDoorsTestingDestinationDoorQuantity_Success() {
        MeshProvider mesh = new MeshProvider(strategyProvider, heuristicFunction, unionFind);
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
        List<Door> doors = new ArrayList<>();
        Door firstRoom = Door.builder().destinationPoint(true).id(1).build();
        doors.add(firstRoom);
        Door secondRoom = Door.builder().destinationPoint(true).id(2).build();
        doors.add(secondRoom);
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(Floor.builder().enumMap(groundFloor).number(0).doors(doors).stairs(stairs).elevators(elevators).build());
        Building building = Building.builder().floors(floors).build();

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getMeshDetails().getDestinationVerticesDict().get(0).size(), 2);
    }

    @Test
    public void meshTestTwoFloors_Success() {
        MeshProvider mesh = new MeshProvider(strategyProvider, heuristicFunction, unionFind);
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}};
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(Floor.builder().enumMap(groundFloor).number(0).doors(doors).stairs(stairs).elevators(elevators).build());
        floors.add(Floor.builder().enumMap(groundFloor).number(1).doors(doors).stairs(stairs).elevators(elevators).build());
        Building building = Building.builder().floors(floors).build();

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getGraph().verticesCount(), 18);
    }

    @Test
    public void meshTestThreeFloors_Success() {
        MeshProvider mesh = new MeshProvider(strategyProvider, heuristicFunction, unionFind);
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}};
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(Floor.builder().enumMap(groundFloor).number(-1).doors(doors).stairs(stairs).elevators(elevators).build());
        floors.add(Floor.builder().enumMap(groundFloor).number(0).doors(doors).stairs(stairs).elevators(elevators).build());
        floors.add(Floor.builder().enumMap(groundFloor).number(1).doors(doors).stairs(stairs).elevators(elevators).build());
        Building building = Building.builder().floors(floors).build();


        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getGraph().verticesCount(), 27);
    }

    @Test
    public void meshTestTwoFloorsWithOneElevators_Success() {
        MeshProvider mesh = new MeshProvider(strategyProvider, heuristicFunction, unionFind);
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.ELEVATOR, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.CORNER}};
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevatorsMinusOne = new ArrayList<>();
        List<Elevator> elevatorsZero = new ArrayList<>();
        Elevator elevatorMinusOne = Elevator.builder().id(1).build();
        Elevator elevatorZero = Elevator.builder().id(1).build();
        elevatorsMinusOne.add(elevatorMinusOne);
        elevatorsZero.add(elevatorZero);
        floors.add(Floor.builder().enumMap(groundFloor).number(-1).doors(doors).stairs(stairs).elevators(elevatorsMinusOne).build());
        floors.add(Floor.builder().enumMap(groundFloor).number(0).doors(doors).stairs(stairs).elevators(elevatorsZero).build());
        Building building = Building.builder().floors(floors).build();


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
        MeshProvider mesh = new MeshProvider(strategyProvider, heuristicFunction, unionFind);
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.ELEVATOR, FloorObject.ELEVATOR, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}};
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevatorsMinusOne = new ArrayList<>();
        List<Elevator> elevatorsZero = new ArrayList<>();
        Elevator elevatorMinusOne = Elevator.builder().build();
        Elevator elevatorMinusOneSecond = Elevator.builder().build();
        Elevator elevatorZero = Elevator.builder().build();
        Elevator elevatorZeroSecond = Elevator.builder().build();

        elevatorsMinusOne.add(elevatorMinusOne);
        elevatorsZero.add(elevatorZero);

        elevatorsMinusOne.add(elevatorMinusOneSecond);
        elevatorsZero.add(elevatorZeroSecond);

        floors.add(Floor.builder().enumMap(groundFloor).number(-1).doors(doors).stairs(stairs).elevators(elevatorsMinusOne).build());
        floors.add(Floor.builder().enumMap(groundFloor).number(0).doors(doors).stairs(stairs).elevators(elevatorsZero).build());
        Building building = Building.builder().floors(floors).build();


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
        MeshProvider mesh = new MeshProvider(strategyProvider, heuristicFunction, unionFind);
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.ELEVATOR, FloorObject.SPACE, FloorObject.ELEVATOR, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}};
        List<Door> doors = new ArrayList<>();
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevatorsMinusOne = new ArrayList<>();
        List<Elevator> elevatorsZero = new ArrayList<>();
        Elevator elevatorMinusOne = Elevator.builder().id(1).build();
        Elevator elevatorMinusOneSecond = Elevator.builder().id(2).build();
        Elevator elevatorZero = Elevator.builder().id(1).build();
        Elevator elevatorZeroSecond = Elevator.builder().id(2).build();

        elevatorsMinusOne.add(elevatorMinusOne);
        elevatorsZero.add(elevatorZero);

        elevatorsMinusOne.add(elevatorMinusOneSecond);
        elevatorsZero.add(elevatorZeroSecond);

        floors.add(Floor.builder().enumMap(groundFloor).number(-1).doors(doors).stairs(stairs).elevators(elevatorsMinusOne).build());
        floors.add(Floor.builder().enumMap(groundFloor).number(0).doors(doors).stairs(stairs).elevators(elevatorsZero).build());
        Building building = Building.builder().floors(floors).build();


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
        MeshProvider mesh = new MeshProvider(strategyProvider, heuristicFunction, unionFind);
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.WALL, FloorObject.STAIRS, FloorObject.SPACE, FloorObject.STAIRS, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}};
        List<Door> doors = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        List<Stairs> stairsMinusOne = new ArrayList<>();
        List<Stairs> stairsZero = new ArrayList<>();
        Stairs stairMinusOne = Stairs.builder().endfloor(0).endid(1).id(1).build();
        Stairs stairMinusOneSecond = Stairs.builder().endfloor(0).endid(2).id(2).build();
        Stairs stairZero = Stairs.builder().endfloor(-1).endid(1).id(1).build();
        Stairs stairZeroSecond = Stairs.builder().endfloor(-1).endid(2).id(2).build();

        stairsMinusOne.add(stairMinusOne);
        stairsZero.add(stairZero);

        stairsMinusOne.add(stairMinusOneSecond);
        stairsZero.add(stairZeroSecond);

        floors.add(Floor.builder().enumMap(groundFloor).number(-1).doors(doors).stairs(stairsMinusOne).elevators(elevators).build());
        floors.add(Floor.builder().enumMap(groundFloor).number(0).doors(doors).stairs(stairsZero).elevators(elevators).build());
        Building building = Building.builder().floors(floors).build();

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
    public void meshTestForSample2Building_Success() {
        MeshProvider mesh = new MeshProvider(strategyProvider, heuristicFunction, unionFind);
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

        List<Door> doorsZero = new ArrayList<>();
        Door doorZero1 = Door.builder().destinationPoint(true).id(1).build();
        doorsZero.add(doorZero1);
        Door doorZero2 = Door.builder().destinationPoint(true).id(2).build();

        doorsZero.add(doorZero2);

        List<Door> doorsOne = new ArrayList<>();
        Door doorOne1 = Door.builder().destinationPoint(true).id(1).build();
        doorsOne.add(doorOne1);
        Door doorOne2 = Door.builder().destinationPoint(true).id(2).build();
        doorsOne.add(doorOne2);
        Door doorOne3 = Door.builder().destinationPoint(true).id(3).build();
        doorsOne.add(doorOne3);
        List<Stairs> stairsZero = new ArrayList<>();
        List<Stairs> stairsOne = new ArrayList<>();

        Stairs stairZero1 = Stairs.builder().endfloor(1).endid(1).id(1).build();
        Stairs stairZero2 = Stairs.builder().endfloor(1).endid(2).id(2).build();
        Stairs stairOne1 = Stairs.builder().endfloor(0).endid(1).id(1).build();
        Stairs stairOne2 = Stairs.builder().endfloor(0).endid(2).id(2).build();

        stairsZero.add(stairZero1);
        stairsZero.add(stairZero2);

        stairsOne.add(stairOne1);
        stairsOne.add(stairOne2);

        List<Elevator> elevatorsOne = new ArrayList<>();
        List<Elevator> elevatorsZero = new ArrayList<>();
        Elevator elevatorOne = Elevator.builder().id(1).build();
        Elevator elevatorZero = Elevator.builder().id(1).build();


        elevatorsZero.add(elevatorZero);
        elevatorsOne.add(elevatorOne);

        floors.add(Floor.builder().enumMap(groundFloor).number(0).doors(doorsZero).stairs(stairsZero).elevators(elevatorsZero).build());
        floors.add(Floor.builder().enumMap(firstFloor).number(1).doors(doorsOne).stairs(stairsOne).elevators(elevatorsOne).build());
        Building building = Building.builder().floors(floors).build();


        MeshResult result = mesh.create(building);
        Graph graph = result.getGraph();
        Assert.assertEquals(graph.verticesCount(), 378);

        int edgesAcrossFloorsCount = 0;
        for (List<Edge> edges : graph.getEdges().values()) {
            for (Edge edge : edges) {
                if(edge.getWeight() == MeshProvider.EDGE_ELEVATOR_WEIGHT){
                    edgesAcrossFloorsCount++;
                }
            }
        }
        Assert.assertEquals(edgesAcrossFloorsCount, 6);
    }

    @Test
    public void meshTestTwoRoomsWithHallwayTestDestinationPoints_Success() {
        MeshProvider mesh = new MeshProvider(strategyProvider, heuristicFunction, unionFind);
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.BEACON, FloorObject.ROOM, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.BEACON, FloorObject.SPACE, FloorObject.ROOM, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}
        };

        List<Door> doors = new ArrayList<>();
        Door door1 = Door.builder().destinationPoint(true).id(1).build();
        Door door2 = Door.builder().destinationPoint(true).id(2).build();

        doors.add(door1);
        doors.add(door2);
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        List<IndoorBeacon> indoorBeacons = new ArrayList<>();
        indoorBeacons.add(IndoorBeacon.builder().id(0).build());
        indoorBeacons.add(IndoorBeacon.builder().id(1).build());
        floors.add(Floor.builder().enumMap(groundFloor).number(0).doors(doors).stairs(stairs).elevators(elevators).indoorBeacons(indoorBeacons).build());
        Building building = Building.builder().floors(floors).build();

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getMeshDetails().getBeaconsDict().get(0).size(), 2);
    }

    @Test
    public void meshTestTwoRoomsWithHallwaySimpleAstarTest_Success() {
        MeshProvider mesh = new MeshProvider(strategyProvider, heuristicFunction, unionFind);
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.BEACON, FloorObject.ROOM, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.BEACON, FloorObject.SPACE, FloorObject.ROOM, FloorObject.WALL},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER}
        };

        List<Door> doors = new ArrayList<>();
        Door door1 = Door.builder().destinationPoint(true).id(1).build();
        Door door2 = Door.builder().destinationPoint(true).id(2).build();

        doors.add(door1);
        doors.add(door2);
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(Floor.builder().enumMap(groundFloor).number(-1).doors(doors).stairs(stairs).elevators(elevators).build());
        floors.add(Floor.builder().enumMap(groundFloor).number(0).doors(doors).stairs(stairs).elevators(elevators).build());
        Building building = Building.builder().floors(floors).build();

        MeshResult result = mesh.create(building);
        Assert.assertEquals(result.getMeshDetails().getBeaconsDict().get(0).size(), 2);
        Graph graph = result.getGraph();
        List<Vertex> res = graph.aStar(result.getMeshDetails().getDestinationVerticesDict().get(0).get(0), result.getMeshDetails().getDestinationVerticesDict().get(0).get(1));
        Assert.assertEquals(res.size(), 3);
    }
}