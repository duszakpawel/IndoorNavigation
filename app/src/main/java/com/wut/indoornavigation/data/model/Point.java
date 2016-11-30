package com.wut.indoornavigation.data.model;

import lombok.Value;

@Value
public class Point {
    float x;
    float y;
    float z;

    public boolean equals(Point a, Point b){
        return a.x == b.x && a.y == b.y && a.z == b.z;
    }
}
