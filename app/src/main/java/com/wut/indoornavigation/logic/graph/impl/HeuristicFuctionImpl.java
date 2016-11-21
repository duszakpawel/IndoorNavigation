package com.wut.indoornavigation.logic.graph.impl;

import com.wut.indoornavigation.logic.graph.HeuristicFuction;
import com.wut.indoornavigation.logic.graph.models.Vertex;

import static java.lang.Math.sqrt;
import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class HeuristicFuctionImpl implements HeuristicFuction {
    @Override
    public double Execute(Vertex source, Vertex destination) {
        double xDistance = abs(source.position.X - destination.position.X);
        double yDistance = abs(source.position.Y - destination.position.Y);

        return sqrt(pow(xDistance, 2) + pow(yDistance, 2));
    }
}
