package com.wut.indoornavigation.logic.graph.models;


import com.wut.indoornavigation.data.model.Point;

import java.util.Objects;

import lombok.Value;

@Value
public class Vertex {
    private int id;
    private Point position;

    public Vertex(int id, Point position){
        this.id = id;
        this.position = position;
    }

    public Vertex(int id){
        this.id = id;
        this.position = null;
    }
}
