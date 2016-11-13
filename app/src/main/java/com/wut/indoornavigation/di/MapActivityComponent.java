package com.wut.indoornavigation.di;

import com.wut.indoornavigation.di.module.MapActivityModule;
import com.wut.indoornavigation.di.scope.PerActivity;
import com.wut.indoornavigation.view.map.MapActivity;

import dagger.Subcomponent;

/**
 * Dagger component for map activity. See also {@link PerActivity}
 */
@PerActivity
@Subcomponent(modules = MapActivityModule.class)
public interface MapActivityComponent {

    void inject(MapActivity activity);
}
