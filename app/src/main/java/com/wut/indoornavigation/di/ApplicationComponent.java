package com.wut.indoornavigation.di;

import com.wut.indoornavigation.IndoorNavigationApp;
import com.wut.indoornavigation.di.module.ApplicationModule;
import com.wut.indoornavigation.di.module.MapActivityModule;

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

    MapActivityComponent plus(MapActivityModule module);

    void inject(IndoorNavigationApp indoorNavigationApp);
}