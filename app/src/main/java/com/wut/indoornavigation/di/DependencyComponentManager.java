package com.wut.indoornavigation.di;

import com.wut.indoornavigation.IndoorNavigationApp;
import com.wut.indoornavigation.di.module.ApplicationModule;
import com.wut.indoornavigation.di.module.MapActivityModule;

/**
 * Dependency manager which holds references to components
 */
public final class DependencyComponentManager {

    private ApplicationComponent applicationComponent;
    private MapActivityComponent mapActivityComponent;

    public DependencyComponentManager(IndoorNavigationApp application) {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(application))
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

    public void releaseMapActivityComponent() {
        mapActivityComponent = null;
    }
}