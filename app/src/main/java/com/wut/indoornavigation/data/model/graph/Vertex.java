package com.wut.indoornavigation.data.model.graph;


import com.wut.indoornavigation.data.model.Point;

import lombok.Getter;
import lombok.Setter;

public class Vertex {
    @Getter
    @Setter
    int id;
    @Getter
    @Setter
    Point position;

    public Vertex(int id, Point coordinates) {
        this.id = id;
        this.position = coordinates;
    }
}
