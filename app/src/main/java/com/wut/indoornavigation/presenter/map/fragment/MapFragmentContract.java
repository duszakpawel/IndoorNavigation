package com.wut.indoornavigation.presenter.map.fragment;

import android.content.Context;
import android.graphics.Bitmap;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Contract for map fragment
 */
public interface MapFragmentContract {

    /**
     * View contract
     */
    interface View extends MvpView {

        /**
         * Shows map for user
         *
         * @param bitmap map
         */
        void showMap(Bitmap bitmap);

        /**
         * Shows progress dialog
         */
        void showProgressDialog();

        /**
         * Hides progress dialog
         */
        void hideProgressDialog();

        /**
         * Shows error message
         *
         * @param message error message
         */
        void showError(String message);
    }

    /**
     * Presenter contract
     */
    interface Presenter extends MvpPresenter<View> {

        /**
         * Gets data for floor spinner
         *
         * @return floor spinner data
         */
        String[] getFloorSpinnerData();

        /**
         * Gets room spinner data
         *
         * @return room spinner data
         */
        String[] getRoomSpinnerData();

        /**
         * Reacts when floor has been selected
         *
         * @param position selected floor position
         */
        void floorSelected(int position);

        /**
         * Reacts when room has been selected
         *
         * @param context    current context
         * @param roomNumber room number
         * @param floorIndex current floor index
         */
        void roomSelected(Context context, int roomNumber, int floorIndex);

        /**
         * Reacts when empty room selected
         *
         * @param floorIndex current floor index
         */
        void emptyRoomSelected(int floorIndex);
    }
}
