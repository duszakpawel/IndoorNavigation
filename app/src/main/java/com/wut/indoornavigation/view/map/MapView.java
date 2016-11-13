package com.wut.indoornavigation.view.map;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.wut.indoornavigation.presenter.map.MapActivityPresenter;

import javax.inject.Inject;

public class MapView extends View
{
    @Inject
    MapActivityPresenter mapActivityPresenter;

    public MapView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // TODO: the painting
    }
}
