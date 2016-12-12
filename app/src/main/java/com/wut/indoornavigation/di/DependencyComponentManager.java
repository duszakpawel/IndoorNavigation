package com.wut.indoornavigation.di;

import com.wut.indoornavigation.IndoorNavigationApp;
import com.wut.indoornavigation.di.module.ApplicationModule;
import com.wut.indoornavigation.di.module.MapActivityModule;
import com.wut.indoornavigation.di.module.SplashActivityModule;

/**
 * Dependency manager which holds references to components
 */
public final class DependencyComponentManager {

    private ApplicationComponent applicationComponent;
    private MapActivityComponent mapActivityComponent;
    private SplashActivityComponent splashActivityComponent;

    public DependencyComponentManager(IndoorNavigationApp application) {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(application.getBaseContext()))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public MapActivityComponent getMapActivityComponent() {
        if (mapActivityComponent == null) {
            mapActivityComponent = applicationComponent.plus(new MapActivityModule());
        }
        return mapActivityComponent;
    }

    public SplashActivityComponent getSplashActivityComponent() {
        if (splashActivityComponent == null) {
            splashActivityComponent = applicationComponent.plus(new SplashActivityModule());
        }
        return splashActivityComponent;
    }

    public void releaseSplashActivityComponent() {
        splashActivityComponent = null;
    }

    public void releaseMapActivityComponent() {
        mapActivityComponent = null;
    }
}