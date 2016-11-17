package com.wut.indoornavigation.presenter.map;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.wut.indoornavigation.di.scope.PerActivity;

import javax.inject.Inject;

@PerActivity
public final class MapActivityPresenter extends MvpNullObjectBasePresenter<MapActivityViewContract.View>
        implements MapActivityViewContract.Presenter {

    @Inject
    public MapActivityPresenter() {

    }
}
