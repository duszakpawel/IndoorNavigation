package com.wut.indoornavigation.presenter.splash;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.wut.indoornavigation.data.parser.Parser;
import com.wut.indoornavigation.map.MapEngine;
import com.wut.indoornavigation.map.OnMapReadyListener;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.Subscriptions;
import timber.log.Timber;

public class SplashPresenter extends MvpNullObjectBasePresenter<SplashContract.View>
        implements SplashContract.Presenter, OnMapReadyListener {

    private final MapEngine mapEngine;
    private final Parser parser;

    @NonNull
    private Subscription subscription;

    @Inject
    public SplashPresenter(MapEngine mapEngine, Parser parser) {
        this.mapEngine = mapEngine;
        this.parser = parser;
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
                .doOnNext(building -> mapEngine.renderMap(context, building))
                .subscribe(building -> Timber.d("Rendering map for %s", building),
                        throwable -> Timber.e(throwable, "Error while rendering map"));
    }
}
