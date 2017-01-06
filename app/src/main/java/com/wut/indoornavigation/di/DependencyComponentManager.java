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
                .applicationModule(new ApplicationModule(application))
                .build();
    }

    /**
     * Gets application component
     *
     * @return {@link ApplicationComponent}
     */
    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    /**
     * Gets map activity component
     *
     * @return {@link MapActivityComponent}
     */
    public MapActivityComponent getMapActivityComponent() {
        if (mapActivityComponent == null) {
            mapActivityComponent = applicationComponent.plus(new MapActivityModule());
        }
        return mapActivityComponent;
    }

    /**
     * Gets splash activity component
     *
     * @return {@link SplashActivityComponent}
     */
    public SplashActivityComponent getSplashActivityComponent() {
        if (splashActivityComponent == null) {
            splashActivityComponent = applicationComponent.plus(new SplashActivityModule());
        }
        return splashActivityComponent;
    }

    /**
     * Releases splash activity component
     */
    public void releaseSplashActivityComponent() {
        splashActivityComponent = null;
    }

    /**
     * Releases map activity component
     */
    public void releaseMapActivityComponent() {
        mapActivityComponent = null;
    }
}