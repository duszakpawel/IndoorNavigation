package com.wut.indoornavigation.presenter.main;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.wut.indoornavigation.di.scope.PerActivity;

import javax.inject.Inject;

@PerActivity
public final class MainActivityPresenter extends MvpNullObjectBasePresenter<MainActivityViewContract.View>
        implements MainActivityViewContract.Presenter {

    @Inject
    public MainActivityPresenter() {

    }
}
