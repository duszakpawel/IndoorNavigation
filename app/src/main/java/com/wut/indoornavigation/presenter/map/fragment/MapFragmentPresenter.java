package com.wut.indoornavigation.presenter.map.fragment;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import javax.inject.Inject;

public class MapFragmentPresenter extends MvpNullObjectBasePresenter<MapFragmentContract.View>
        implements MapFragmentContract.Presenter {

    @Inject
    public MapFragmentPresenter() {

    }
}
