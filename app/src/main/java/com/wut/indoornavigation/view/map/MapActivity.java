package com.wut.indoornavigation.view.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.wut.indoornavigation.IndoorNavigationApp;
import com.wut.indoornavigation.R;
import com.wut.indoornavigation.presenter.map.MapActivityPresenter;
import com.wut.indoornavigation.presenter.map.MapActivityViewContract;
import com.wut.indoornavigation.view.base.BaseMvpActivity;

import javax.inject.Inject;

public class MapActivity extends BaseMvpActivity<MapActivityViewContract.View, MapActivityViewContract.Presenter>
        implements MapActivityViewContract.View {

    @Inject
    MapActivityPresenter mapActivityPresenter;

    public static void startActivity(Context context) {
        final Intent intent = new Intent(context, MapActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    @NonNull
    @Override
    public MapActivityViewContract.Presenter createPresenter() {
        return mapActivityPresenter;
    }

    @Override
    protected void injectDependencies() {
        IndoorNavigationApp.getDependencies(this).getMapActivityComponent().inject(this);
    }

    @Override
    protected void resetDependencies() {
        IndoorNavigationApp.getDependencies(this).releaseMapActivityComponent();
    }
}
