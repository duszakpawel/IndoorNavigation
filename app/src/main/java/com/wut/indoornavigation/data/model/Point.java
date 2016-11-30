package com.wut.indoornavigation.data.model;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode()
public class Point {
    float x;
    float y;
    float z;
}
