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
        //Assert.assertEquals(unionFind.connected(0, 1), true);
    }
}