package com.wut.indoornavigation.view.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

import butterknife.ButterKnife;

/**
 * Base class for activities with MVP pattern
 * @param <V> view contract
 * @param <P> presenter contract
 */
public abstract class BaseMvpActivity<V extends MvpView, P extends MvpPresenter<V>>
        extends MvpActivity<V, P> {

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    /**
     * Replaces fragment in selected container
     * @param containerViewId selected container id
     * @param fragment selected fragment
     * @param tag selected fragment tag
     * @return instance of {@link FragmentTransaction}
     */
    protected FragmentTransaction replaceFragment(int containerViewId, Fragment fragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(containerViewId, fragment, tag);
        return ft;
    }
}
