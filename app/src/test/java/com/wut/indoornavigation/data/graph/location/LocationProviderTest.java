package com.wut.indoornavigation.data.graph.location;


import com.wut.indoornavigation.data.model.Point;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LocationProviderTest {

    @Test
    public void locationProviderTestForIsoscelesTriangleInSignalCenter() throws Exception {
        LocationProvider locationProvider = new LocationProvider();

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
        Assert.assertEquals(approximatedLocation.getZ(), 0f);
    }
}