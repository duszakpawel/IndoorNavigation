package com.wut.indoornavigation.data.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Wall extends BuildingObject {

    @Builder
    private Wall(int id) {
        super(id);
    }
}
