package com.wut.indoornavigation.render;

import android.content.Context;
import android.util.TypedValue;

import com.wut.indoornavigation.R;

/**
 * Base class for render engines
 */
public abstract class RenderEngine {

    /**
     * Map height
     */
    protected int mapHeight;
    /**
     * Map width
     */
    protected int mapWidth;

    /**
     * Gets map width
     *
     * @param context context
     */
    protected void getMapWidth(Context context) {
        final int widthPadding = (int) context.getResources().getDimension(R.dimen.activity_horizontal_margin);
        final int width = context.getResources().getDisplayMetrics().widthPixels;

        mapWidth = width - 2 * widthPadding;
    }

    /**
     * Gets map height
     *
     * @param context context
     */
    protected void getMapHeight(Context context) {
        final int heightPadding = (int) context.getResources().getDimension(R.dimen.activity_vertical_margin);
        final int headerHeight = (int) context.getResources().getDimension(R.dimen.map_fragment_header_height);
        final int height = context.getResources().getDisplayMetrics().heightPixels;

        mapHeight = height - 2 * heightPadding - headerHeight - getToolbarHeight(context);
    }

    /**
     * Calculates step width
     *
     * @param buildingWidth building width
     * @return step width
     */
    protected int calculateStepWidth(int buildingWidth) {
        return mapWidth / buildingWidth;
    }

    /**
     * Calculates step height
     *
     * @param buildingHeight building height
     * @return step height
     */
    protected int calculateStepHeight(int buildingHeight) {
        return mapHeight / buildingHeight;
    }

    private int getToolbarHeight(Context context) {
        final TypedValue tv = new TypedValue();

        if (context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            if (context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
                return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
            }
        }

        throw new IllegalStateException("Cannot resolve action bar size");
    }
}
