package com.wut.indoornavigation.map;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class MapView extends View {

    public MapView(Context context) {
        super(context);
        init();
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // TODO: the painting
    }

    private void init() {
        // TODO: 17.11.2016 Initialize objects here
    }
}
