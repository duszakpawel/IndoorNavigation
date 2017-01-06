package com.wut.indoornavigation.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.wut.indoornavigation.data.mesh.MeshProvider;
import com.wut.indoornavigation.data.storage.BuildingStorage;
import com.wut.indoornavigation.di.qualifier.BuildingPreferences;
import com.wut.indoornavigation.render.map.MapEngine;
import com.wut.indoornavigation.render.map.impl.MapEngineImpl;
import com.wut.indoornavigation.render.path.PathFactory;
import com.wut.indoornavigation.render.path.PathFinderEngine;
import com.wut.indoornavigation.render.path.impl.PathFinderEngineImpl;
import com.wut.indoornavigation.render.path.impl.PathFactoryImpl;

import javax.inject.Singleton;
import javax.xml.parsers.DocumentBuilderFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Application module
 */
@Module
public class ApplicationModule {

    private static final String BUILDING_PREFERENCES = "buildingPreferences";

    private final Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    /**
     * Provides Singleton MapEngine
     *
     * @return {@link MapEngineImpl}
     */
    @Singleton
    @Provides
    MapEngine provideMapEngine() {
        return new MapEngineImpl();
    }

    /**
     * Provides Singleton Document Builder Factory
     *
     * @return {@link DocumentBuilderFactory}
     */
    @Singleton
    @Provides
    DocumentBuilderFactory provideDocumentBuilder() {
        return DocumentBuilderFactory.newInstance();
    }

    /**
     * Provides Singleton PathFinderEngine
     *
     * @param meshProvider    mesh provider
     * @param buildingStorage building storage
     * @param pathFactory     path factory
     * @return {@link PathFinderEngineImpl}
     */
    @Singleton
    @Provides
    PathFinderEngine providePathFinderEngine(MeshProvider meshProvider, BuildingStorage buildingStorage, PathFactory pathFactory) {
        return new PathFinderEngineImpl(meshProvider, buildingStorage, pathFactory);
    }

    /**
     * Provides Singleton {@link SharedPreferences} for building storage
     *
     * @return {@link SharedPreferences} for building storage
     */
    @Singleton
    @BuildingPreferences
    @Provides
    SharedPreferences provideBuildingSharedPreferences() {
        return context.getSharedPreferences(BUILDING_PREFERENCES, Context.MODE_PRIVATE);
    }

    /**
     * Provides path factory
     *
     * @return {@link PathFinderEngineImpl}
     */
    @Singleton
    @Provides
    PathFactory providePathFactory() {
        return new PathFactoryImpl();
    }
}
