package com.indoornavigation;

import android.content.Context;
import android.content.res.TypedArray;

import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class Map extends ViewGroup
{
    private IDrawerProvider drawerProvider;

    public Map(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        drawerProvider = new DrawProvider();

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Map,
                0, 0);

        try {
            // TODO: consume resources
        } finally {
            a.recycle();
        }
        setWillNotDraw(false);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // TODO: the painting
        drawerProvider.DrawMap(canvas);
    }

    @Override
    protected void onLayout(boolean b, int i, int j, int k, int l) { }
}
