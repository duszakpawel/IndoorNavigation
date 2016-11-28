package com.wut.indoornavigation.data.model;

import lombok.Value;

@Value
public class Point {
    float X;
    float Y;
    float Z;

    public Point(float x, float y, float z) {
        this.X = x;
        this.Y = y;
        this.Z = z;
    }
}
