package com.wut.indoornavigation.view.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;

import com.wut.indoornavigation.IndoorNavigationApp;
import com.wut.indoornavigation.R;
import com.wut.indoornavigation.presenter.main.MainActivityPresenter;
import com.wut.indoornavigation.view.base.BaseMvpActivity;

import javax.inject.Inject;

public class MainActivity extends BaseMvpActivity<MainActivityViewContract.View, MainActivityViewContract.Presenter>
        implements MainActivityViewContract.View {

    @Inject
    MainActivityPresenter mainActivityPresenter;

    public static void startActivity(Context context) {
        final Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @NonNull
    @Override
    public MainActivityViewContract.Presenter createPresenter() {
        return mainActivityPresenter;
    }

    @Override
    protected void injectDependencies() {
        IndoorNavigationApp.getDependencies(this).getMainActivityComponent().inject(this);
    }

    @Override
    protected void resetDependencies() {
        IndoorNavigationApp.getDependencies(this).releaseMainActivityComponent();
    }
}
