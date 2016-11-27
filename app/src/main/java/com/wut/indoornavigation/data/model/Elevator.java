package com.wut.indoornavigation.data.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Elevator extends BuildingObject {

    @Builder
    private Elevator(int id) {
        super(id);
    }
}
