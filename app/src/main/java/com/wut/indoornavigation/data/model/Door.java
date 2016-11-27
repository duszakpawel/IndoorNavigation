package com.wut.indoornavigation.data.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Door extends BuildingObject {
    boolean destinationPoint;

    @Builder
    private Door(boolean destinationPoint, int id) {
        super(id);
        this.destinationPoint = destinationPoint;
    }
}
