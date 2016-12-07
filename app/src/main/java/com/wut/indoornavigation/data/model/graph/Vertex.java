package com.wut.indoornavigation.data.model.graph;


import com.wut.indoornavigation.data.model.Point;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Vertex {
    int id;
    Point position;

    public Vertex(int id, Point coordinates) {
        this.id = id;
        this.position = coordinates;
    }
}
