package com.wut.indoornavigation.data.model;

import java.util.List;

/**
 * Created by sa on 06.11.2016.
 */

public class Floor {
    FloorObject[][] enumMap;
    public Integer Number;
    public List<Wall> Walls;
    public List<Door> Doors;
    public List<Stairs> Stairs;
    public List<Elevator> Elevators;
}
