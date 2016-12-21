package com.wut.indoornavigation.data.path;

import android.graphics.Path;

import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.render.path.PathFactory;
import com.wut.indoornavigation.render.path.impl.PathFactoryImpl;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PathFactoryTest {
    private PathFactory pathFactory = new PathFactoryImpl();

    @Test
    public void ProducePathTestForSampleCorrectDataSuccess() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0,0,0));
        points.add(new Point(0,1,0));
        points.add(new Point(2,2,0));
        points.add(new Point(3,4,0));
        points.add(new Point(4,4,0));
        points.add(new Point(5,6,0));

        Path path = pathFactory.producePath(points);

        Assert.assertEquals(path.isEmpty(), false);
    }

    @Test
    public void ProducePathTestForEmptyDataSuccess() {
        List<Point> points = new ArrayList<>();

        Path path = pathFactory.producePath(points);

        Assert.assertEquals(path.isEmpty(), false);
    }

    @Test
    public void ProducePathTestForNullDataSuccess() {
        Path path = pathFactory.producePath(null);

        Assert.assertEquals(path.isEmpty(), false);
    }
}
