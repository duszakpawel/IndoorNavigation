package com.wut.indoornavigation.data.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Class representing room
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Room extends BuildingObject {
    int number;

    @Builder(toBuilder = true)
    private Room(int number, int id, int x, int y) {
        super(id, x, y);
        this.number = number;
    }
}
