package com.wut.indoornavigation.data.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Room extends BuildingObject {
    int number;

    @Builder
    private Room(int number, int id) {
        super(id);
        this.number = number;
    }
}
