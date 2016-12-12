package com.wut.indoornavigation.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.wut.indoornavigation.data.mesh.MeshProvider;
import com.wut.indoornavigation.data.storage.BuildingStorage;
import com.wut.indoornavigation.di.qualifier.BuildingPreferences;
import com.wut.indoornavigation.render.map.MapEngine;
import com.wut.indoornavigation.render.map.impl.MapEngineImpl;
import com.wut.indoornavigation.render.path.PathFinderEngine;
import com.wut.indoornavigation.render.path.impl.PathFinderEngineImpl;

import javax.inject.Singleton;
import javax.xml.parsers.DocumentBuilderFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Application module
 */
@Module
public class ApplicationModule {

    public static final String BUILDING_PREFERENCES = "buildingPreferences";

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
    PathFinderEngine providePathFinderEngine(MeshProvider meshProvider, BuildingStorage buildingStorage) {
        return new PathFinderEngineImpl(meshProvider, buildingStorage);
    }

    @Singleton
    @BuildingPreferences
    @Provides
    SharedPreferences provideBuildingSharedPreferences() {
        return context.getSharedPreferences(BUILDING_PREFERENCES, Context.MODE_PRIVATE);
    }
}
