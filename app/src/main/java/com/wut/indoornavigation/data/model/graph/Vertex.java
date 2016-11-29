package com.wut.indoornavigation.data.model.graph;


import com.wut.indoornavigation.data.model.Point;

import lombok.Value;

@Value
public class Vertex {
    int id;
    Point position;
}
