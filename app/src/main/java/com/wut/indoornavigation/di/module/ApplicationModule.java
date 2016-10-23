package com.wut.indoornavigation.di.module;

import android.content.Context;

import dagger.Module;

/**
 * Application module
 */
@Module
public class ApplicationModule {

    private final Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }
}
