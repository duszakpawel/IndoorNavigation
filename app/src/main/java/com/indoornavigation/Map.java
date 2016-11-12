package com.indoornavigation;

import android.content.Context;
import android.content.res.TypedArray;

import android.graphics.Canvas;
import android.util.AttributeSet;

public class Map extends CanvasView
{
    public Map(Context context, AttributeSet attrs)
    {
        super(context, attrs);

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

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3)
    {

    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // TODO: the painting
    }
}
