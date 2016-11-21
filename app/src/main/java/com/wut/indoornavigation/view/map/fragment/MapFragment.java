package com.wut.indoornavigation.view.map.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wut.indoornavigation.IndoorNavigationApp;
import com.wut.indoornavigation.R;
import com.wut.indoornavigation.presenter.map.fragment.MapFragmentContract;
import com.wut.indoornavigation.presenter.map.fragment.MapFragmentPresenter;
import com.wut.indoornavigation.view.base.BaseMvpFragment;

import javax.inject.Inject;

public class MapFragment extends BaseMvpFragment<MapFragmentContract.View, MapFragmentContract.Presenter>
        implements MapFragmentContract.View {

    public static final String TAG = MapFragment.class.getSimpleName();

    @Inject
    MapFragmentPresenter mapFragmentPresenter;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @NonNull
    @Override
    public MapFragmentContract.Presenter createPresenter() {
        return mapFragmentPresenter;
    }

    @Override
    protected void injectDependencies() {
        IndoorNavigationApp.getDependencies(getContext()).getMapActivityComponent().inject(this);
    }
}
