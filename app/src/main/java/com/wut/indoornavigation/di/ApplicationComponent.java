package com.wut.indoornavigation.di;

import com.wut.indoornavigation.IndoorNavigationApp;
import com.wut.indoornavigation.di.module.ApplicationModule;
import com.wut.indoornavigation.di.module.MainActivityModule;

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

    MainActivityComponent plus(MainActivityModule module);

    void inject(IndoorNavigationApp indoorNavigationApp);
}