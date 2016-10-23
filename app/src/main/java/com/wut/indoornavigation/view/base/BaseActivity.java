package com.wut.indoornavigation.view.base;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        injectDependencies();
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
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
}
