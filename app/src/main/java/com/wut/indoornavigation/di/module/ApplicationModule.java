package com.wut.indoornavigation.di.module;

import android.content.Context;

import com.wut.indoornavigation.data.mesh.MeshProvider;
import com.wut.indoornavigation.map.MapEngine;
import com.wut.indoornavigation.map.impl.MapEngineImpl;
import com.wut.indoornavigation.path.PathFinderEngine;
import com.wut.indoornavigation.path.impl.PathFinderEngineImpl;

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

    @Singleton
    @Provides
    PathFinderEngine providePathFinderEngine(MeshProvider meshProvider) {
        return new PathFinderEngineImpl(meshProvider);
    }
}
