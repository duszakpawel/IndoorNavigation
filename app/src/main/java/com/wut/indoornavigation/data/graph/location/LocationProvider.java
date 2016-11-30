package com.wut.indoornavigation.data.graph.location;

import android.support.annotation.NonNull;

import com.wut.indoornavigation.data.model.Point;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LocationProvider {

    private static final int K = 1;

    @Inject
    public LocationProvider() {

    }

    public Point computeLocation(@NonNull List<Point> positions, @NonNull List<Float> weights) {
        if (positions.size() != weights.size()) {
            // TODO: custom exception to be handled
            throw new RuntimeException("Collections must have equal length.");
        }

        double xNominator = 0;
        double yNominator = 0;
        double zNominator = 0;
        double denominator = 0;

        for (int i = 0; i < positions.size(); i++) {
            double divider = Math.pow(weights.get(i), -K);
            xNominator += divider * positions.get(i).getX();
            yNominator += divider * positions.get(i).getY();
            zNominator += divider * positions.get(i).getZ();
            denominator += divider;
        }

        return new Point((float)(xNominator / denominator), (float)(yNominator / denominator), (float)(zNominator / denominator));
    }
}
