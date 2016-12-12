package com.wut.indoornavigation.render;

import android.content.Context;
import android.util.TypedValue;

import com.wut.indoornavigation.R;

public abstract class RenderEngine {
    protected int mapHeight;
    protected int mapWidth;

    protected void getMapWidth(Context context) {
        final int widthPadding = (int) context.getResources().getDimension(R.dimen.activity_horizontal_margin);
        final int width = context.getResources().getDisplayMetrics().widthPixels;

        mapWidth = width - 2 * widthPadding;
    }

    protected void getMapHeight(Context context) {
        final int heightPadding = (int) context.getResources().getDimension(R.dimen.activity_vertical_margin);
        final int headerHeight = (int) context.getResources().getDimension(R.dimen.map_fragment_header_height);
        final int height = context.getResources().getDisplayMetrics().heightPixels;

        mapHeight = height - 2 * heightPadding - headerHeight - getToolbarHeight(context);
    }

    private int getToolbarHeight(Context context) {
        final TypedValue tv = new TypedValue();

        if (context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            if (context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
                return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
            }
        }

        return 0;
        //throw new IllegalStateException("Cannot resolve action bar size");
    }

    protected int calculateStepWidth(int buildingWidth) {
        return mapWidth / buildingWidth;
    }

    protected int calculateStepHeight(int buildingHeight) {
        return mapHeight / buildingHeight;
    }
}
