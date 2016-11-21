package com.wut.indoornavigation.data.model;

import lombok.Value;

@Value
public class Point {
    float X;
    float Y;

    public Point(float x, float y) {
        this.X = x;
        this.Y = y;
    }
}
