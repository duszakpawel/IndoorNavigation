package com.wut.indoornavigation.data.mesh.processingStrategy;

import com.wut.indoornavigation.data.mesh.processingStrategy.impl.BeaconProcessingStrategyImpl;
import com.wut.indoornavigation.data.mesh.processingStrategy.impl.CornerProcessingStrategyImpl;
import com.wut.indoornavigation.data.mesh.processingStrategy.impl.DoorProcessingStrategyImpl;
import com.wut.indoornavigation.data.mesh.processingStrategy.impl.ElevatorProcessingStrategyImpl;
import com.wut.indoornavigation.data.mesh.processingStrategy.impl.RoomProcessingStrategyImpl;
import com.wut.indoornavigation.data.mesh.processingStrategy.impl.SpaceProcessingStrategyImpl;
import com.wut.indoornavigation.data.mesh.processingStrategy.impl.StairsProcessingStrategyImpl;
import com.wut.indoornavigation.data.mesh.processingStrategy.impl.WallProcessingStrategyImpl;
import com.wut.indoornavigation.data.model.FloorObject;

import javax.inject.Inject;

/**
 * Strategy provider class
 */
public class StrategyProvider {
    @Inject
    public StrategyProvider() {

    }

    /**
     * Returns ProcessingStrategy object for specific FloorObject sign
     *
     * @param object FloorObject sign
     * @return processing strategy
     */
    public ProcessingStrategy provideStrategy(FloorObject object) {
        switch (object) {
            case CORNER:
                return new CornerProcessingStrategyImpl();
            case WALL:
                return new WallProcessingStrategyImpl();
            case BEACON:
                return new BeaconProcessingStrategyImpl();
            case DOOR:
                return new DoorProcessingStrategyImpl();
            case STAIRS:
                return new StairsProcessingStrategyImpl();
            case ELEVATOR:
                return new ElevatorProcessingStrategyImpl();
            case ROOM:
                return new RoomProcessingStrategyImpl();
            case SPACE:
                return new SpaceProcessingStrategyImpl();
            default:
                throw new IllegalArgumentException();
        }
    }

}
