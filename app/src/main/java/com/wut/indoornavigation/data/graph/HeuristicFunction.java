package com.wut.indoornavigation.data.graph;

import com.wut.indoornavigation.data.model.graph.Vertex;

import javax.inject.Inject;
import javax.inject.Singleton;

import static java.lang.Math.sqrt;
import static java.lang.Math.abs;
import static java.lang.Math.pow;

@Singleton
public class HeuristicFunction {

    @Inject
    public HeuristicFunction() {

    }

    public double execute(Vertex source, Vertex destination) {
        double xDistance = abs(source.getPosition().getX() - destination.getPosition().getX());
        double yDistance = abs(source.getPosition().getY() - destination.getPosition().getY());

        return sqrt(pow(xDistance, 2) + pow(yDistance, 2));
    }
}
