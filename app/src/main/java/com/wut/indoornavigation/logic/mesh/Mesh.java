package com.wut.indoornavigation.logic.mesh;

import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.logic.graph.Graph;

public interface Mesh  {
    Graph create(Building buildingObject);
}
