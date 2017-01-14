package com.wut.indoornavigation.data.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Class representing beacon
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Beacon extends BuildingObject {

    int major;
    int minor;

    @Builder(toBuilder = true)
    private Beacon(int id, int x, int y, int major, int minor) {
        super(id, x, y);
        this.major = major;
        this.minor = minor;
    }
}

