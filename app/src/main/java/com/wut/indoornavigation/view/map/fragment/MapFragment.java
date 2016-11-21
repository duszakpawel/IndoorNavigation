package com.wut.indoornavigation.view.map.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.wut.indoornavigation.IndoorNavigationApp;
import com.wut.indoornavigation.R;
import com.wut.indoornavigation.presenter.map.fragment.MapFragmentContract;
import com.wut.indoornavigation.presenter.map.fragment.MapFragmentPresenter;
import com.wut.indoornavigation.view.base.BaseMvpFragment;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;

public class MapFragment extends BaseMvpFragment<MapFragmentContract.View, MapFragmentContract.Presenter>
        implements MapFragmentContract.View {

    public static final String TAG = MapFragment.class.getSimpleName();

    @BindArray(R.array.room_array)
    String[] rooms;

    @BindArray(R.array.floor_array)
    String[] floors;

    @BindView(R.id.fragment_map_floor_spinner)
    Spinner floorSpinner;
    @BindView(R.id.fragment_map_room_spinner)
    Spinner roomSpinner;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeSpinners();
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

    private void initializeSpinners() {
        initializeSpinner(floorSpinner, floors);
        initializeSpinner(roomSpinner, rooms);
    }

    private void initializeSpinner(Spinner spinner, String[] data) {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, data);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
