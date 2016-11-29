package com.wut.indoornavigation;

import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.logic.location.LocationProvider;
import com.wut.indoornavigation.logic.location.impl.LocationProviderImpl;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LocationTests {
    @Test
    public void locationProviderTestForIsoscelesTriangleInSignalCenter() throws Exception {
        LocationProvider locationProvider = new LocationProviderImpl();

        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0, 0));
        points.add(new Point(4, 0, 0));
        points.add(new Point(2, 3, 0));

        List<Float> weights = new ArrayList<>();
        weights.add(1f);
        weights.add(1f);
        weights.add(1f);

        Point approximatedLocation = locationProvider.computeLocation(points, weights);

        Assert.assertEquals(approximatedLocation.getX(), 2f);
        Assert.assertEquals(approximatedLocation.getY(), 1f);
    }
}
