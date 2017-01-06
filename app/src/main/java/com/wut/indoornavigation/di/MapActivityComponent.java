package com.wut.indoornavigation.di;

import com.wut.indoornavigation.di.module.MapActivityModule;
import com.wut.indoornavigation.di.scope.PerActivity;
import com.wut.indoornavigation.view.map.MapActivity;
import com.wut.indoornavigation.view.map.fragment.MapFragment;

import dagger.Subcomponent;

/**
 * Dagger component for map activity. See also {@link PerActivity}
 */
@PerActivity
@Subcomponent(modules = MapActivityModule.class)
public interface MapActivityComponent {

    /**
     * Injects dependencies to MapActivity
     * @param activity {@link MapActivity}
     */
    void inject(MapActivity activity);

    /**
     * Injects dependencies to MapFragment
     * @param fragment {@link MapFragment}
     */
    void inject(MapFragment fragment);
}
