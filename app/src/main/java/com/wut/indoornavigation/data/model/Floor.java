package com.wut.indoornavigation.data.model;

import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Floor {

    int number;
    FloorObject[][] enumMap;
    List<Room> rooms;
    List<Wall> walls;
    List<Door> doors;
    List<Stairs> stairs;
    List<Elevator> elevators;
    List<Beacon> beacons;
}
