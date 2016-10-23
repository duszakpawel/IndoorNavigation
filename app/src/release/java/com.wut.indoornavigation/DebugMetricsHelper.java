package com.wut.indoornavigation;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Helper class which initializes debug tools
 */
@Singleton
public final class DebugMetricsHelper {

    @Inject
    public DebugMetricsHelper() {
    }

    public void init(Context context) {
        //dummy - no debug tools for release builds
    }
}
