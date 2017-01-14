package com.wut.indoornavigation.data.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Class representing elevator
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Elevator extends BuildingObject {

    @Builder(toBuilder = true)
    private Elevator(int id, int x, int y) {
        super(id, x, y);
    }
}
