package com.wut.indoornavigation.data.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Beacon extends BuildingObject {

    @Builder
    private Beacon(int id) {
        super(id);
    }
}

