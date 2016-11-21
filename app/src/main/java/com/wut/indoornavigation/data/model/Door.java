package com.wut.indoornavigation.data.model;

public final class Door extends BuildingObject {
    private boolean isDestinationPoint;

    public Door(Point start, Point end, int id, boolean isDestinationPoint){
        super(start, end, id);
        this.isDestinationPoint = isDestinationPoint;
    }

    public boolean getIsDestinationPoint(){
        return isDestinationPoint;
    }
}
