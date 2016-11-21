package com.wut.indoornavigation.data.model;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Door extends BuildingObject {

    boolean destinationPoint;
}
