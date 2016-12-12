package com.wut.indoornavigation.path.impl;

import com.wut.indoornavigation.data.graph.PathFinder;
import com.wut.indoornavigation.data.mesh.MeshProvider;
import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.model.graph.Vertex;
import com.wut.indoornavigation.data.model.mesh.MeshResult;
import com.wut.indoornavigation.path.PathFinderEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

/**
 * Path finder engine implementation
 */
public class PathFinderEngineImpl implements PathFinderEngine {
    private final MeshProvider meshProvider;
    private MeshResult mesh;

    @Inject
    public PathFinderEngineImpl(MeshProvider meshProvider){
        this.meshProvider = meshProvider;
    }

    @Override
    public void prepareMesh(Building building) {
        try {
            mesh = meshProvider.execute(building).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new IllegalStateException("Mesh creating was interrupted.");
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new IllegalStateException("Creating mesh failed.");
        }
    }

    @Override
    public List<Point> computePath(Point source, int destinationFloorIndex, int destinationVertexIndex) {
        PathFinder pathFinder = mesh.getGraph();
        // TODO: hardcoded for now, may throw exception
        // i think good idea is to get approximated location from beacons and approximate it to one of vertices?
        Vertex start = mesh.getDestinationPoints().get(0).get(0);
        Vertex end = mesh.getDestinationPoints().get(destinationFloorIndex).get(destinationVertexIndex);

        List<Vertex> vertexPath = pathFinder.aStar(start, end);

        List<Point> result = new ArrayList<>(vertexPath.size());
        for (Vertex vertex : vertexPath) {
            result.add(vertex.getPosition());
        }

        return result;
    }
}
