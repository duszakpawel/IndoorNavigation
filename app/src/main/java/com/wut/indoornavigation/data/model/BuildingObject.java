package com.wut.indoornavigation.data.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Abstract class representing building object with id
 */
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class BuildingObject {
    int id;
    int x;
    int y;
}
