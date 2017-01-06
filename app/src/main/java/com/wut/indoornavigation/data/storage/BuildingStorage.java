package com.wut.indoornavigation.data.storage;

import android.content.SharedPreferences;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.di.qualifier.BuildingPreferences;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Storage which contains parsed building objects in memory
 */
@Singleton
public class BuildingStorage {

    private static final String BUILDING = "building";

    private final SharedPreferences preferences;
    private final Moshi moshi;
    private final JsonAdapter<Building> jsonAdapter;

    @Inject
    public BuildingStorage(@BuildingPreferences SharedPreferences preferences) {
        this.preferences = preferences;
        moshi = new Moshi.Builder().build();
        jsonAdapter = moshi.adapter(Building.class);

    }

    /**
     * Gets building from memory
     *
     * @return building object
     */
    public Building getBuilding() {
        try {
            return jsonAdapter.fromJson(preferences.getString(BUILDING, ""));
        } catch (IOException e) {
            throw new IllegalStateException("There is no stored building");
        }
    }

    /**
     * Stores building in the memory
     *
     * @param building building to be stored
     */
    public void storeBuilding(Building building) {
        preferences.edit().putString(BUILDING, jsonAdapter.toJson(building)).apply();
    }
}
