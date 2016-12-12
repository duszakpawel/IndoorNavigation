package com.wut.indoornavigation.presenter.splash;

import android.content.Context;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

public interface SplashContract {

    interface View extends MvpView {
        void showMap();
    }

    interface Presenter extends MvpPresenter<View> {
        void prepareMap(String fileName, Context context);
    }
}
