package com.wut.indoornavigation.view.map.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import uk.co.senab.photoview.PhotoViewAttacher;

public class MapFragment extends BaseMvpFragment<MapFragmentContract.View, MapFragmentContract.Presenter>
        implements MapFragmentContract.View {

    public static final String TAG = MapFragment.class.getSimpleName();

    @BindArray(R.array.room_array)
    String[] rooms;

    @BindView(R.id.fragment_map_floor_spinner)
    Spinner floorSpinner;
    @BindView(R.id.fragment_map_room_spinner)
    Spinner roomSpinner;
    @BindView(R.id.fragment_map_map)
    ImageView map;

    @Inject
    MapFragmentPresenter mapFragmentPresenter;

    private PhotoViewAttacher mapAttacher;

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
        initializeFloorSpinner();
        initializeRoomSpinner();
        mapAttacher = new PhotoViewAttacher(map);
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

    private void initializeFloorSpinner() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, mapFragmentPresenter.getFloorSpinnerData());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floorSpinner.setAdapter(adapter);
        floorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getPresenter().floorSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initializeRoomSpinner() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, rooms);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(adapter);
    }

    @Override
    public void showMap(Bitmap bitmap) {
        map.setImageBitmap(bitmap);
        mapAttacher.update();
    }
}
