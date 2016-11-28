package com.wut.indoornavigation.logic.location.impl;

import android.support.annotation.NonNull;

import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.logic.location.CentroidLocationProvider;

import java.util.List;

public class CentroidLocationProviderImpl implements CentroidLocationProvider {
    private static final int k = 1;

    @Override
    public Point ComputeLocation(@NonNull List<Point> positions, @NonNull List<Float> weights) throws Exception {
        int positionsSize = positions.size();
        int weightsSize = weights.size();

        if (positionsSize != weightsSize) {
            throw new Exception("Collections must have equal length.");
        }

        double x_nominator = 0;
        double y_nominator = 0;
        double denominator = 0;

        for (int i = 0; i < positions.size(); i++) {
            double divider = Math.pow(weights.get(i), -k);
            x_nominator += divider * positions.get(i).getX();
            y_nominator += divider * positions.get(i).getY();
            denominator += divider;
        }

        double x = x_nominator / denominator;
        double y = y_nominator / denominator;

        return new Point((int)x, (int)y);
    }
}
