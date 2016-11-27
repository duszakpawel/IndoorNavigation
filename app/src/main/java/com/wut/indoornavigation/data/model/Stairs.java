package com.wut.indoornavigation.data.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Stairs extends BuildingObject {

    int endfloor;
    int endid;

    @Builder
    private Stairs(int endfloor, int endid, int id) {
        super(id);
        this.endfloor = endfloor;
        this.endid = endid;
    }
}
