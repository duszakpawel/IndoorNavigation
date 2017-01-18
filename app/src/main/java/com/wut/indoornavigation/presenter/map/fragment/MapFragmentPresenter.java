package com.wut.indoornavigation.presenter.map.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.positioning.Positioner;
import com.wut.indoornavigation.render.map.MapEngine;
import com.wut.indoornavigation.render.path.PathFinderEngine;
import com.wut.indoornavigation.render.position.PositionEngine;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    private static final int USER_POSITION_REFRESH_INTERVAL = 2;

    @VisibleForTesting
    boolean isNavigating;

    private final MapEngine mapEngine;
    private final PathFinderEngine pathFinderEngine;
    private final PositionEngine positionEngine;
    private final Positioner positioner;

    @NonNull
    private Subscription pathFinderSubscription;
    @NonNull
    private Subscription userPositionSubscription;
    @NonNull
    private Subscription mapRefreshSubscription;

    private Point currentUserPosition;

    @Inject
    MapFragmentPresenter(MapEngine mapEngine, PositionEngine positionEngine,
                         PathFinderEngine pathFinderEngine, Positioner positioner) {
        this.mapEngine = mapEngine;
        this.pathFinderEngine = pathFinderEngine;
        this.positionEngine = positionEngine;
        this.positioner = positioner;
        pathFinderSubscription = Subscriptions.unsubscribed();
        userPositionSubscription = Subscriptions.unsubscribed();
        mapRefreshSubscription = Subscriptions.unsubscribed();
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        pathFinderSubscription.unsubscribe();
        userPositionSubscription.unsubscribe();
        mapRefreshSubscription.unsubscribe();
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
        if (isNavigating) {
            getView().showMap(pathFinderEngine.getMapWithPathForFloor(floorNumberList.get(position)));
        } else {
            showProperBitmap(floorNumberList.get(position));
        }
    }

    @Override
    public void startNavigation(Context context, int roomNumber, int floorIndex) {
        if (!isNavigating) {
            getView().showProgressDialog();
            final int destinationFloorNumber = pathFinderEngine.destinationFloorNumber(roomNumber);
            final int destinationRoomIndex = pathFinderEngine.getRoomIndex(roomNumber);
            final Point userPosition = positioner.getUserPosition();

            pathFinderSubscription = Observable.just(userPosition)
                    .doOnNext(point -> pathFinderEngine.renderPath(mapEngine,
                            context, point, destinationFloorNumber, destinationRoomIndex))
                    .map(point -> mapEngine.getFloorNumbers().get(floorIndex))
                    .map(pathFinderEngine::getMapWithPathForFloor)
                    .flatMap(map -> {
                        startMapNavigationRefreshing();
                        return Observable.just(map);
                    })
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterTerminate(getView()::hideProgressDialog)
                    .subscribe(map -> Timber.d("Path rendered"), throwable -> {
                        Timber.e(throwable, "Error while rendering path");
                        getView().showError(throwable.getMessage());
                    }, () -> {
                        isNavigating = true;
                        getView().startNavigation();
                    });
        } else {
            cancelNavigation(floorIndex);
        }
    }

    @Override
    public void emptyRoomSelected(int floorIndex) {
        isNavigating = false;
        getView().showMap(mapEngine.getMapForFloor(floorIndex));
        positioner.getBeaconsManager().startDiscoveringBeacons();
    }

    @Override
    public void startUserPositioning() {
        userPositionSubscription = Observable.interval(USER_POSITION_REFRESH_INTERVAL, TimeUnit.SECONDS)
                .map(time -> positioner.getUserPosition())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(point -> {
                    Timber.d("Getting next user position " + point.toString());
                    if (currentUserPosition != point) {
                        currentUserPosition = point;
                        if (!isNavigating) {
                            showProperBitmap(mapEngine.getFloorNumbers().get(getView().getSelectedFloor()));
                        }
                    }
                })
                .subscribe(point -> getView().setToolbarFloorNumber(String.valueOf((int) (point.getZ()))),
                        throwable -> Timber.e(throwable, "Error while getting user position"));
    }

    @Override
    public void initUserPositioningEngine(Context context) {
        positionEngine.init(context);
    }

    private void startMapNavigationRefreshing() {
        mapRefreshSubscription = Observable.interval(USER_POSITION_REFRESH_INTERVAL, TimeUnit.SECONDS)
                .map(time -> mapEngine.getFloorNumbers().get((int) currentUserPosition.getZ()))
                .map(pathFinderEngine::getMapWithPathForFloor)
                .map(map -> positionEngine.renderMapWithUserPosition(map, currentUserPosition))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getView()::showMap,
                        throwable -> Timber.e(throwable, "Error while showing map with user position"));
    }

    private void showProperBitmap(int floorNumber) {
        final Bitmap map = mapEngine.getMapForFloor(floorNumber);
        if (currentUserPosition == null || (int) currentUserPosition.getZ() != floorNumber) {
            getView().showMap(map);
        } else {
            getView().showMap(positionEngine.renderMapWithUserPosition(map, currentUserPosition));
        }
    }

    private void cancelNavigation(int position) {
        mapRefreshSubscription.unsubscribe();
        isNavigating = false;
        final List<Integer> floorNumberList = mapEngine.getFloorNumbers();
        getView().cancelNavigation();
        showProperBitmap(floorNumberList.get(position));
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
