package com.wut.indoornavigation.presenter.splash;

import android.content.Context;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Contract for splash activity
 */
public interface SplashContract {

    /**
     * View contract
     */
    interface View extends MvpView {

        /**
         * Shows {@link com.wut.indoornavigation.view.map.MapActivity}
         */
        void showMap();

        /**
         * Hides loading view
         */
        void hideLoadingView();

        /**
         * Shows error to user
         * @param message message for user
         */
        void showError(String message);
    }

    /**
     * Presenter contract
     */
    interface Presenter extends MvpPresenter<View> {

        /**
         * Prepares map for user
         *
         * @param fileName map file name
         * @param context  current context
         */
        void prepareMap(String fileName, Context context);
    }
}
