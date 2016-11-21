package com.wut.indoornavigation.di.module;

import android.content.Context;
import android.graphics.Canvas;

import com.wut.indoornavigation.extensions.CanvasExtender;
import com.wut.indoornavigation.extensions.impl.CanvasExtenderImpl;
import com.wut.indoornavigation.map.MapEngine;
import com.wut.indoornavigation.map.impl.MapEngineImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Application module
 */
@Module
public class ApplicationModule {

    private final Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    MapEngine provideMapEngine() {
        return new MapEngineImpl(context);
    }

    @Singleton
    @Provides
    CanvasExtender provideCanvasExtender(){ return new CanvasExtenderImpl();}
}
