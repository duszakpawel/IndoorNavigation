package com.wut.indoornavigation.view.map.legend;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.wut.indoornavigation.R;

/**
 * Dialog fragment which shows map legend
 */
public class MapLegendDialogFragment extends DialogFragment {

    /**
     * Map legend dialog fragment tag
     */
    public static final String TAG = DialogFragment.class.getSimpleName();

    /**
     * Creates new instance of {@link MapLegendDialogFragment}
     *
     * @return new instance of {@link MapLegendDialogFragment}
     */
    public static MapLegendDialogFragment newInstance() {
        return new MapLegendDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setView(R.layout.dialog_legend)
                .setCancelable(false)
                .setPositiveButton(R.string.action_ok, (dialog, which) -> dismiss())
                .create();
    }
}
