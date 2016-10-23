package com.wut.indoornavigation.di;

import com.wut.indoornavigation.di.module.MainActivityModule;
import com.wut.indoornavigation.di.scope.PerActivity;
import com.wut.indoornavigation.view.main.MainActivity;

import dagger.Subcomponent;

/**
 * Dagger component for main activity. See also {@link PerActivity}
 */
@PerActivity
@Subcomponent(modules = MainActivityModule.class)
public interface MainActivityComponent {

    void inject(MainActivity activity);
}
