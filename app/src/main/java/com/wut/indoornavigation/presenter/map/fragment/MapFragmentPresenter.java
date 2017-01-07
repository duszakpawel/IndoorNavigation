package com.wut.indoornavigation.presenter.map.fragment;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.render.map.MapEngine;
import com.wut.indoornavigation.render.path.PathFinderEngine;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;
import timber.log.Timber;

/**
 * Presenter for {@link com.wut.indoornavigation.view.map.fragment.MapFragment}
 */
public class MapFragmentPresenter extends MvpNullObjectBasePresenter<MapFragmentContract.View>
        implements MapFragmentContract.Presenter {

    private final MapEngine mapEngine;
    private final PathFinderEngine pathFinderEngine;

    @NonNull
    private Subscription pathFinderSubscription;

    @Inject
    MapFragmentPresenter(MapEngine mapEngine, PathFinderEngine pathFinderEngine) {
        this.mapEngine = mapEngine;
        this.pathFinderEngine = pathFinderEngine;
        pathFinderSubscription = Subscriptions.unsubscribed();
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        pathFinderSubscription.unsubscribe();
    }

    @Override
    public String[] getFloorSpinnerData() {
        return parseFloorNumbers();
    }

    @Override
    public String[] getRoomSpinnerData() {
        return parseRoomNumbers();
    }

    @Override
    public void floorSelected(int position) {
        final List<Integer> floorNumberList = mapEngine.getFloorNumbers();
        getView().showMap(mapEngine.getMapForFloor(floorNumberList.get(position)));
    }

    @Override
    public void startNavigation(Context context, int roomNumber, int floorIndex) {
        getView().showProgressDialog();
        final int destinationFloorNumber = pathFinderEngine.destinationFloorNumber(roomNumber);
        final int destinationRoomIndex = pathFinderEngine.getRoomIndex(roomNumber);

        // TODO: 15.12.2016 Provide user point
        pathFinderSubscription = Observable.just(new Point(0, 0, 0))
                .doOnNext(point -> pathFinderEngine.renderPath(mapEngine,
                        context, point, destinationFloorNumber, destinationRoomIndex))
                .map(point -> mapEngine.getFloorNumbers().get(floorIndex))
                .map(pathFinderEngine::getMapWithPathForFloor)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(getView()::hideProgressDialog)
                .subscribe(getView()::showMap, throwable -> {
                    Timber.e(throwable, "Error while rendering path");
                    getView().showError(throwable.getMessage());
                });
    }

    @Override
    public void emptyRoomSelected(int floorIndex) {
        final int destinationFloorNumber = pathFinderEngine.destinationFloorNumber(floorIndex);
        getView().showMap(mapEngine.getMapForFloor(destinationFloorNumber));
    }

    private String[] parseFloorNumbers() {
        final List<Integer> floorNumberList = mapEngine.getFloorNumbers();
        final String[] floorNumbers = new String[floorNumberList.size()];
        for (int i = 0; i < floorNumberList.size(); i++) {
            floorNumbers[i] = String.valueOf(floorNumberList.get(i));
        }

        return floorNumbers;
    }

    private String[] parseRoomNumbers() {
        final List<Integer> roomNumberList = mapEngine.getRoomNumbers();
        final String[] roomNumbers = new String[roomNumberList.size() + 1];
        roomNumbers[0] = "";
        for (int i = 0; i < roomNumberList.size(); i++) {
            roomNumbers[i + 1] = String.valueOf(roomNumberList.get(i));
        }

        return roomNumbers;
    }
}
