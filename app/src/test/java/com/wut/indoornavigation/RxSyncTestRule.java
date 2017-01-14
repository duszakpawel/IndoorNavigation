package com.wut.indoornavigation;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

/**
 * Test helper class which allows to synchronize RxJava operations
 */
public final class RxSyncTestRule implements TestRule {

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {

                // Override RxJava schedulers
                RxJavaHooks.reset();
                RxJavaHooks.setOnIOScheduler(scheduler -> Schedulers.immediate());
                RxJavaHooks.setOnComputationScheduler(scheduler -> Schedulers.immediate());
                RxJavaHooks.setOnNewThreadScheduler(scheduler -> Schedulers.immediate());

                // Override RxAndroid schedulers
                final RxAndroidPlugins rxAndroidPlugins = RxAndroidPlugins.getInstance();
                rxAndroidPlugins.reset();
                rxAndroidPlugins.registerSchedulersHook(new RxAndroidSchedulersHook() {
                    @Override
                    public Scheduler getMainThreadScheduler() {
                        return Schedulers.immediate();
                    }
                });

                base.evaluate();

                RxJavaHooks.reset();
                rxAndroidPlugins.reset();
            }
        };
    }
}
