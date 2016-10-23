package com.wut.indoornavigation;

import android.content.Context;
import android.os.Handler;
import android.os.StrictMode;

import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.nshmura.strictmodenotifier.StrictModeNotifier;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * Helper class which initializes debug tools
 */
@Singleton
public final class DebugMetricsHelper {

    @Inject
    public DebugMetricsHelper() {

    }

    public void init(Context context) {
        StrictModeNotifier.install(context);
        new Handler().post(() -> {
            final StrictMode.ThreadPolicy threadPolicy =
                    new StrictMode.ThreadPolicy.Builder().detectAll()
                            .permitDiskReads()
                            .permitDiskWrites()
                            .penaltyLog() // Must!
                            .build();
            StrictMode.setThreadPolicy(threadPolicy);

            final StrictMode.VmPolicy vmPolicy =
                    new StrictMode.VmPolicy.Builder()
                            .detectAll()
                            .penaltyLog() // Must!
                            .build();
            StrictMode.setVmPolicy(vmPolicy);
        });

        AndroidDevMetrics.initWith(context);
        LeakCanary.install((IndoorNavigationApp) context.getApplicationContext());
        Timber.plant(new Timber.DebugTree());
    }
}
