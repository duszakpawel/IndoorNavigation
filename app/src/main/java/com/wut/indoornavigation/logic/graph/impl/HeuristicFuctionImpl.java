package com.wut.indoornavigation.logic.graph.impl;

import com.wut.indoornavigation.logic.graph.HeuristicFuction;
import com.wut.indoornavigation.logic.graph.models.Vertex;

import static java.lang.Math.sqrt;
import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class HeuristicFuctionImpl implements HeuristicFuction {
    @Override
    public double Execute(Vertex source, Vertex destination) {
        double xDistance = abs(source.getPosition().getX() - destination.getPosition().getX());
        double yDistance = abs(source.getPosition().getY() - destination.getPosition().getY());
        double result = sqrt(pow(xDistance, 2) + pow(yDistance, 2));

        return result;
    }
}
