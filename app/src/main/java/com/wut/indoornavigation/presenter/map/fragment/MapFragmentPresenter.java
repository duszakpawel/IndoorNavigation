package com.wut.indoornavigation.presenter.map.fragment;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.wut.indoornavigation.map.MapEngine;

import java.util.List;

import javax.inject.Inject;

public class MapFragmentPresenter extends MvpNullObjectBasePresenter<MapFragmentContract.View>
        implements MapFragmentContract.Presenter {

    private final MapEngine mapEngine;

    @Inject
    public MapFragmentPresenter(MapEngine mapEngine) {
        this.mapEngine = mapEngine;
    }


    @Override
    public String[] getFloorSpinnerData() {
        return parseFloorNumbers();
    }

    @Override
    public void floorSelected(int position) {
        final List<Integer> floorNumberList = mapEngine.getFloorNumbers();
        getView().showMap(mapEngine.getMapForFloor(floorNumberList.get(position)));
    }

    private String[] parseFloorNumbers() {
        final List<Integer> floorNumberList = mapEngine.getFloorNumbers();
        final String[] floorNumbers = new String[floorNumberList.size()];
        for (int i = 0; i < floorNumberList.size(); i++) {
            floorNumbers[i] = String.valueOf(floorNumberList.get(i));
        }

        return floorNumbers;
    }
}
