package com.wut.indoornavigation.render.path.impl;

import lombok.Builder;

@Builder(toBuilder = true)
public class SplinePoint {
    public float x;
    public float y;
    public float dx;
    public float dy;
}
