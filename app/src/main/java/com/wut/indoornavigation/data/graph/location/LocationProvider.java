package com.wut.indoornavigation.data.graph.location;

import android.support.annotation.NonNull;

import com.wut.indoornavigation.data.model.Point;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Location provider class
 */
@Singleton
public class LocationProvider {

    /**
     * Some constant value for weighted-centroid algorithm; in papers it's usually 1 or 1/2.
     */
    private static final int K = 1;

    /**
     * Constructor of class
     */
    @Inject
    public LocationProvider() {

    }

    /**
     * Computes location of user depending on received becons signal strengths
     * @param positions position of beacons
     * @param weights weights of signals
     * @return User location computed by algorithm
     */
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
