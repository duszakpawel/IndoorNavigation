package com.wut.indoornavigation.data.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Door extends BuildingObject {
    boolean destinationPoint;

    @Builder(toBuilder = true)
    private Door(boolean destinationPoint, int id, int x, int y) {
        super(id, x, y);
        this.destinationPoint = destinationPoint;
    }
}
