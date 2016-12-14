package com.wut.indoornavigation.presenter.splash;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.wut.indoornavigation.data.parser.Parser;
import com.wut.indoornavigation.data.storage.BuildingStorage;
import com.wut.indoornavigation.render.map.MapEngine;
import com.wut.indoornavigation.render.map.OnMapReadyListener;
import com.wut.indoornavigation.render.path.PathFinderEngine;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;
import timber.log.Timber;

public class SplashPresenter extends MvpNullObjectBasePresenter<SplashContract.View>
        implements SplashContract.Presenter, OnMapReadyListener {

    private final MapEngine mapEngine;
    private final PathFinderEngine pathFinderEngine;
    private final Parser parser;
    private final BuildingStorage storage;

    @NonNull
    private Subscription subscription;

    @Inject
    public SplashPresenter(MapEngine mapEngine, PathFinderEngine pathFinderEngine,
                           Parser parser, BuildingStorage storage) {
        this.mapEngine = mapEngine;
        this.pathFinderEngine = pathFinderEngine;
        this.parser = parser;
        this.storage = storage;
        subscription = Subscriptions.unsubscribed();
        mapEngine.setOnMapReadyListener(this);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        subscription.unsubscribe();
    }

    @Override
    public void onMapReady() {
        getView().showMap();
    }

    @Override
    public void prepareMap(String fileName, Context context) {
        subscription = Observable.just(fileName)
                .map(parser::parse)
                .doOnNext(storage::storeBuilding)
                .doOnNext(pathFinderEngine::prepareMesh)
                .doOnNext(building -> mapEngine.renderMap(context, building))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(getView()::hideLoadingView)
                .subscribe(building -> Timber.d("Rendering map for %s", building),
                        throwable -> Timber.e(throwable, "Error while rendering map"));
    }
}
