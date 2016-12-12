package com.wut.indoornavigation.view.splash;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.wut.indoornavigation.IndoorNavigationApp;
import com.wut.indoornavigation.R;
import com.wut.indoornavigation.presenter.splash.SplashContract;
import com.wut.indoornavigation.presenter.splash.SplashPresenter;
import com.wut.indoornavigation.view.base.BaseMvpActivity;
import com.wut.indoornavigation.view.map.MapActivity;

import javax.inject.Inject;

import butterknife.BindView;

public class SplashActivity extends BaseMvpActivity<SplashContract.View, SplashContract.Presenter>
        implements SplashContract.View {

    @BindView(R.id.loadingView)
    ProgressBar loadingView;

    @Inject
    SplashPresenter splashPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            requestPermissions(permissions, requestCode);
        }
        String buildingPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/test.xml";
        splashPresenter.prepareMap(buildingPath, this);
    }

    @Override
    protected void injectDependencies() {
        IndoorNavigationApp.getDependencies(this).getSplashActivityComponent().inject(this);
    }

    @Override
    protected void resetDependencies() {
        IndoorNavigationApp.getDependencies(this).releaseSplashActivityComponent();
    }

    @NonNull
    @Override
    public SplashContract.Presenter createPresenter() {
        return splashPresenter;
    }

    @Override
    public void showMap() {
        MapActivity.startActivity(this);
        finish();
    }

    @Override
    public void hideLoadingView() {
        loadingView.setVisibility(View.GONE);
    }
}
