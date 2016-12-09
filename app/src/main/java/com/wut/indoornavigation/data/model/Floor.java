package com.wut.indoornavigation.data.model;

import java.util.List;

import lombok.Value;

@Value
public class Floor {

    FloorObject[][] enumMap;
    Integer number;
    List<Wall> walls;
    List<Door> doors;
    List<Stairs> stairs;
    List<Elevator> elevators;
    List<Beacon> beacons;
}
