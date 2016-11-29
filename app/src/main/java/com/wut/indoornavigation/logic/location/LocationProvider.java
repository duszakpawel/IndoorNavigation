package com.wut.indoornavigation.logic.location;

import com.wut.indoornavigation.data.model.Point;

import java.util.List;

public interface LocationProvider {
    Point computeLocation(List<Point> positions, List<Float> weights);
}
