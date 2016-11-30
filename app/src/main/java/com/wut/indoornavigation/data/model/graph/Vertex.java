package com.wut.indoornavigation.data.model.graph;


import com.wut.indoornavigation.data.model.Point;

import lombok.Builder;

@Builder(toBuilder = true)
public class Vertex {
    int id;
    Point position;
}
