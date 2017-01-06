package com.wut.indoornavigation.view.map.fragment;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.wut.indoornavigation.IndoorNavigationApp;
import com.wut.indoornavigation.R;
import com.wut.indoornavigation.presenter.map.fragment.MapFragmentContract;
import com.wut.indoornavigation.presenter.map.fragment.MapFragmentPresenter;
import com.wut.indoornavigation.view.base.BaseMvpFragment;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Fragment which shows map
 */
public class MapFragment extends BaseMvpFragment<MapFragmentContract.View, MapFragmentContract.Presenter>
        implements MapFragmentContract.View {

    /**
     * Tag for {@link MapFragment}
     */
    public static final String TAG = MapFragment.class.getSimpleName();

    @BindString(R.string.title_progress_path)
    String progressDialogTitle;
    @BindString(R.string.progress_looking_path)
    String progressDialogMessage;

    @BindView(R.id.fragment_map_floor_spinner)
    Spinner floorSpinner;
    @BindView(R.id.fragment_map_room_spinner)
    Spinner roomSpinner;
    @BindView(R.id.fragment_map_map)
    ImageView map;

    @Inject
    MapFragmentPresenter mapFragmentPresenter;

    private PhotoViewAttacher mapAttacher;
    private ProgressDialog progressDialog;

    /**
     * Creates new instance of {@link MapFragment}
     *
     * @return new instance of {@link MapFragment}
     */
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

    @Override
    public void showMap(Bitmap bitmap) {
        map.setImageBitmap(bitmap);
        mapAttacher.update();
    }

    @Override
    public void showProgressDialog() {
        progressDialog = ProgressDialog.show(getContext(), progressDialogTitle,
                progressDialogMessage, true);
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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
                android.R.layout.simple_spinner_dropdown_item, mapFragmentPresenter.getRoomSpinnerData());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(adapter);
        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final int floorIndex = floorSpinner.getSelectedItemPosition();
                if (position == 0) {
                    getPresenter().emptyRoomSelected(floorIndex);
                    return;
                }
                final int destinationRoomNumber = Integer.parseInt((String) roomSpinner.getSelectedItem());
                getPresenter().roomSelected(getContext(), destinationRoomNumber, floorIndex);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
