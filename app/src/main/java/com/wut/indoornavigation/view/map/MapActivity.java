package com.wut.indoornavigation.view.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.wut.indoornavigation.IndoorNavigationApp;
import com.wut.indoornavigation.R;
import com.wut.indoornavigation.presenter.map.MapActivityPresenter;
import com.wut.indoornavigation.presenter.map.MapActivityViewContract;
import com.wut.indoornavigation.view.base.BaseMvpActivity;
import com.wut.indoornavigation.view.map.fragment.MapFragment;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;

public class MapActivity extends BaseMvpActivity<MapActivityViewContract.View, MapActivityViewContract.Presenter>
        implements MapActivityViewContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindColor(R.color.white)
    int colorWhite;

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
        initializeToolbar();
        if (savedInstanceState == null) {
            replaceFragment(R.id.activity_map_container, MapFragment.newInstance(), MapFragment.TAG).commit();
        }
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

    private void initializeToolbar() {
        toolbar.setTitleTextColor(colorWhite);
        setSupportActionBar(toolbar);
    }
}
