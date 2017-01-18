package com.wut.indoornavigation.data.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Class representing beacon
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class IndoorBeacon extends BuildingObject {

    int major;
    int minor;
    double distance;

    @Builder(toBuilder = true)
    private IndoorBeacon(int id, int x, int y, int major, int minor, double distance) {
        super(id, x, y);
        this.major = major;
        this.minor = minor;
        this.distance = distance;
    }
}

