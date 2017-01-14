package com.wut.indoornavigation.data.model;

import java.util.List;

import lombok.Builder;
import lombok.Value;

/**
 * Class representing floor
 */
@Value
@Builder(toBuilder = true)
public class Floor {

    int number;
    FloorObject[][] enumMap;
    List<Room> rooms;
    List<Door> doors;
    List<Stairs> stairs;
    List<Elevator> elevators;
    List<Beacon> beacons;
}
