package com.wut.indoornavigation.di;

import com.wut.indoornavigation.IndoorNavigationApp;
import com.wut.indoornavigation.di.module.ApplicationModule;
import com.wut.indoornavigation.di.module.MainActivityModule;
import com.wut.indoornavigation.di.module.MapActivityModule;

/**
 * Dependency manager which holds references to components
 */
public final class DependencyComponentManager {

    private ApplicationComponent applicationComponent;
    private MainActivityComponent mainActivityComponent;
    private MapActivityComponent mapActivityComponent;

    public DependencyComponentManager(IndoorNavigationApp application) {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(application))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public MainActivityComponent getMainActivityComponent() {
        if (mainActivityComponent == null) {
            mainActivityComponent = applicationComponent.plus(new MainActivityModule());
        }
        return mainActivityComponent;
    }

    public void releaseMainActivityComponent() {
        mainActivityComponent = null;
    }

    public MapActivityComponent getMapActivityComponent() {
        if (mapActivityComponent == null) {
            mapActivityComponent = applicationComponent.plus(new MapActivityModule());
        }
        return mapActivityComponent;
    }

    public void releaseMapActivityComponent() {
        mapActivityComponent = null;
    }
}