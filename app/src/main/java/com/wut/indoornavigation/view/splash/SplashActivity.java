package com.wut.indoornavigation.view.splash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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

/**
 * Splash activity which show static image for user
 */
public class SplashActivity extends BaseMvpActivity<SplashContract.View, SplashContract.Presenter>
        implements SplashContract.View {

    private static final int PERMISSION_REQUEST = 501;
    private static final String FILE_NAME = "/test.xml";

    @BindView(R.id.loadingView)
    ProgressBar loadingView;

    @Inject
    SplashPresenter splashPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST) {
            checkPermission();
        }
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

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
        } else {
            startMapEngine();
        }
    }

    private void startMapEngine() {
        final String buildingPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                .getAbsolutePath() + FILE_NAME;
        splashPresenter.prepareMap(buildingPath, this);
    }
}
