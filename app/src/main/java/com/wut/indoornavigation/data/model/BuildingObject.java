package com.wut.indoornavigation.data.model;

public abstract class BuildingObject {
    private Point Start;
    private Point End;
    private int Id;

    public BuildingObject(Point start, Point end, int id) {
        Start = start;
        End = end;
        Id = id;
    }

    public Point getStart() {
        return Start;
    }

    public Point getEnd(){
        return End;
    }

    public int getId(){
        return Id;
    }
}
