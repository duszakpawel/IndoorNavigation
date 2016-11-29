package com.wut.indoornavigation.logic.graph.impl;

import com.wut.indoornavigation.logic.graph.models.Vertex;

import static java.lang.Math.sqrt;
import static java.lang.Math.abs;
import static java.lang.Math.pow;

public final class HeuristicFunction {
    public double execute(Vertex source, Vertex destination) {
        double xDistance = abs(source.getPosition().getX() - destination.getPosition().getX());
        double yDistance = abs(source.getPosition().getY() - destination.getPosition().getY());

        return sqrt(pow(xDistance, 2) + pow(yDistance, 2));
    }
}
