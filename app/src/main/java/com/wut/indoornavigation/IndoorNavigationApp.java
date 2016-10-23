package com.wut.indoornavigation;

import android.app.Application;
import android.content.Context;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.wut.indoornavigation.di.DependencyComponentManager;

import javax.inject.Inject;

public class IndoorNavigationApp extends Application {

    @Inject
    DebugMetricsHelper debugMetricsHelper;

    private DependencyComponentManager dependencies;

    @Override
    public void onCreate() {
        super.onCreate();
        dependencies = new DependencyComponentManager(this);
        dependencies.getApplicationComponent().inject(this);

        AndroidThreeTen.init(this);
        debugMetricsHelper.init(this);
    }

    /**
     * Allows to get dependency component manager
     * @param context current context
     * @return instance of {@link DependencyComponentManager}
     */
    public static DependencyComponentManager getDependencies(Context context) {
        final IndoorNavigationApp application = (IndoorNavigationApp) context.getApplicationContext();
        return application.dependencies;
    }
}
