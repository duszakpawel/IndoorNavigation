package com.wut.indoornavigation.logic.location.impl;

import android.support.annotation.NonNull;

import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.logic.location.LocationProvider;

import java.util.List;

public class LocationProviderImpl implements LocationProvider {
    private static final int k = 1;

    @Override
    public Point ComputeLocation(@NonNull List<Point> positions, @NonNull List<Float> weights) {
        if (positions.size() != weights.size()) {
            // TODO: custom exception to be handled
            throw new RuntimeException("Collections must have equal length.");
        }

        double xNominator = 0;
        double yNominator = 0;
        double denominator = 0;

        for (int i = 0; i < positions.size(); i++) {
            double divider = Math.pow(weights.get(i), -k);
            xNominator += divider * positions.get(i).getX();
            yNominator += divider * positions.get(i).getY();
            denominator += divider;
        }

        return new Point((float)(xNominator / denominator), (float)(yNominator / denominator));
    }
}
