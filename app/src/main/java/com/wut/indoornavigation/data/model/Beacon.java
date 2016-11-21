package com.wut.indoornavigation.data.model;

public final class Beacon  {
    private Point position;

    public Beacon(Point position){
        this.position = position;
    }

    public Point getPosition(){
        return position;
    }
}
