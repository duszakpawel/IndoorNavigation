package com.wut.indoornavigation.data.model;

import java.util.List;

public class BuildingData {
    private List<Floor> floors;

    public BuildingData(List<Floor> floors){
        this.floors = floors;
    }

    public List<Floor> getFloors(){
        return floors;
    }
}
