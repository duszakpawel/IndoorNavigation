package com.wut.indoornavigation.di;

import com.wut.indoornavigation.IndoorNavigationApp;
import com.wut.indoornavigation.di.module.ApplicationModule;
import com.wut.indoornavigation.di.module.MapActivityModule;
import com.wut.indoornavigation.di.module.SplashActivityModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class
})
/**
 * Dagger component for application
 */
public interface ApplicationComponent {

    /**
     * Adds {@link MapActivityComponent} dependencies to Application Component
     * @param module {@link MapActivityModule}
     * @return {@link MapActivityComponent}
     */
    MapActivityComponent plus(MapActivityModule module);

    /**
     * Adds {@link SplashActivityComponent} dependencies to Application Component
     * @param module {@link SplashActivityModule}
     * @return {@link SplashActivityComponent}
     */
    SplashActivityComponent plus(SplashActivityModule module);

    /**
     * Injects Application Component dependencies
     * @param indoorNavigationApp application
     */
    void inject(IndoorNavigationApp indoorNavigationApp);
}