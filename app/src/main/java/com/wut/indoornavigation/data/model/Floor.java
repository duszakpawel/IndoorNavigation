package com.wut.indoornavigation.data.model;

import java.util.List;

public class Floor {
    FloorObject[][] enumMap;
    public Integer Number;
    public List<Wall> Walls;
    public List<Door> Doors;
    public List<Stairs> Stairs;
    public List<Elevator> Elevators;
}
