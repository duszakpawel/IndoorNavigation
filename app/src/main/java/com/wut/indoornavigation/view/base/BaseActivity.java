package com.wut.indoornavigation.view.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            resetDependencies();
        }
    }

    /**
     * Injects Dagger dependencies. See also {@link com.wut.indoornavigation.di.DependencyComponentManager}
     */
    protected abstract void injectDependencies();

    /**
     * Resets Dagger dependencies. See also {@link com.wut.indoornavigation.di.DependencyComponentManager}
     */
    protected abstract void resetDependencies();

    protected FragmentTransaction replaceFragment(int containerViewId, Fragment fragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(containerViewId, fragment, tag);
        return ft;
    }
}
