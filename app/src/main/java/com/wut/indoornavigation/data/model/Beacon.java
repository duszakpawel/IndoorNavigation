package com.wut.indoornavigation.data.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Beacon extends BuildingObject {

    @Builder(toBuilder = true)
    private Beacon(int id, int x, int y) {
        super(id, x, y);
    }
}

