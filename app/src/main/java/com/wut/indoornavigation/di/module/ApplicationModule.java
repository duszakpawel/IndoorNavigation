package com.wut.indoornavigation.di.module;

import android.content.Context;

import com.wut.indoornavigation.map.MapEngine;
import com.wut.indoornavigation.map.impl.MapEngineImpl;

import javax.inject.Singleton;
import javax.xml.parsers.DocumentBuilderFactory;

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
        return new MapEngineImpl();
    }

    @Singleton
    @Provides
    DocumentBuilderFactory provideDocumentBuilder() {
        return DocumentBuilderFactory.newInstance();
    }
}
