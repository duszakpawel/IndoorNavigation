package com.wut.indoornavigation.view.map;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

public interface MapActivityViewContract {

    interface View extends MvpView {

    }

    interface Presenter extends MvpPresenter<MapActivityViewContract.View> {

    }
}
