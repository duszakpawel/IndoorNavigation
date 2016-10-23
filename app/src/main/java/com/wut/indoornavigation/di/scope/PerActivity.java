package com.wut.indoornavigation.di.scope;

import javax.inject.Scope;

/**
 * Dagger scope. Objects in this scope lives as long as activity.
 */
@Scope
public @interface PerActivity {
}
