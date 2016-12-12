package com.wut.indoornavigation.view.splash;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wut.indoornavigation.IndoorNavigationApp;
import com.wut.indoornavigation.presenter.splash.SplashContract;
import com.wut.indoornavigation.presenter.splash.SplashPresenter;
import com.wut.indoornavigation.view.base.BaseMvpActivity;
import com.wut.indoornavigation.view.map.MapActivity;

import javax.inject.Inject;

public class SplashActivity extends BaseMvpActivity<SplashContract.View, SplashContract.Presenter>
        implements SplashContract.View {

    @Inject
    SplashPresenter splashPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 12.12.2016 Podac dobra sciezke do pliku
        splashPresenter.prepareMap("", this);
    }

    @Override
    protected void injectDependencies() {
        IndoorNavigationApp.getDependencies(this).getSplashActivityComponent().inject(this);
    }

    @Override
    protected void resetDependencies() {
        IndoorNavigationApp.getDependencies(this).releaseSplashActivityComponent();
    }

    @NonNull
    @Override
    public SplashContract.Presenter createPresenter() {
        return splashPresenter;
    }

    @Override
    public void showMap() {
        MapActivity.startActivity(this);
        finish();
    }
}
