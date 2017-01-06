package com.wut.indoornavigation.di;

import com.wut.indoornavigation.di.module.SplashActivityModule;
import com.wut.indoornavigation.di.scope.PerActivity;
import com.wut.indoornavigation.view.splash.SplashActivity;

import dagger.Subcomponent;

/**
 * Dagger component for map activity. See also {@link PerActivity}
 */
@PerActivity
@Subcomponent(modules = SplashActivityModule.class)
public interface SplashActivityComponent {

    /**
     * Injects dependencies to SplashActivity
     *
     * @param activity {@link SplashActivity}
     */
    void inject(SplashActivity activity);
}
