package com.wut.indoornavigation.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.wut.indoornavigation.R;

public class MapView extends View {

    private final Paint wallPaint = new Paint();
    private final Paint doorPaint = new Paint();

    private final float doorWidth = getResources().getDimension(R.dimen.map_view_door_width);
    private final float defaultMargin = getResources().getDimension(R.dimen.map_view_default_margin);
    private final float defaultMarginBottom = getResources().getDimension(R.dimen.map_view_default_margin_bottom);
    private final float paintStroke = getResources().getDimension(R.dimen.map_view_paint_stroke);
    private final float room1MarginLeft = getResources().getDimension(R.dimen.map_view_room_1_margin_left);
    private final float room1Height = getResources().getDimension(R.dimen.map_view_room_1_height);
    private final float room1Width = getResources().getDimension(R.dimen.map_view_room_1_width);
    private final float room2Height = getResources().getDimension(R.dimen.map_view_room_2_height);
    private final float room2Width = getResources().getDimension(R.dimen.map_view_room_2_width);

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
        drawBorder(canvas);
        drawUpperRooms(canvas);
        drawBottomRoom(canvas);
        // TODO: the painting
    }

    private void drawBorder(Canvas canvas) {
        final int width = getWidth();
        final int height = getHeight();
        canvas.drawRect(defaultMargin, defaultMargin , width - defaultMargin, height - defaultMarginBottom, wallPaint);
    }

    private void drawUpperRooms(Canvas canvas) {
        //left
        canvas.drawRect(defaultMargin + room1MarginLeft, defaultMargin, defaultMargin + room1MarginLeft + room1Width,
                defaultMargin + room1Height, wallPaint);
        canvas.drawLine(defaultMargin + room1MarginLeft + room1Width / 2, defaultMargin + room1Height,
                defaultMargin + room1MarginLeft + room1Width / 2 + doorWidth, defaultMargin + room1Height, doorPaint);
        //right
        final float leftRoomLeft = defaultMargin + room1MarginLeft + room1Width;
        canvas.drawRect(leftRoomLeft, defaultMargin, leftRoomLeft + room1Width, defaultMargin + room1Height, wallPaint);
        canvas.drawLine(leftRoomLeft + room1Width / 2, defaultMargin + room1Height, leftRoomLeft + room1Width / 2 + doorWidth,
                defaultMargin + room1Height, doorPaint);
    }

    private void drawBottomRoom(Canvas canvas) {
        final int height = getHeight();
        canvas.drawRect(defaultMargin, height - defaultMarginBottom - room2Height, defaultMargin + room2Width,
                height - defaultMarginBottom, wallPaint);
        canvas.drawLine(defaultMargin + room2Width / 2, height - defaultMarginBottom - room2Height,
                defaultMargin + room2Width / 2 + doorWidth, height - defaultMarginBottom - room2Height, doorPaint);
    }

    private void init() {
        wallPaint.setColor(ContextCompat.getColor(getContext(), R.color.wallColor));
        doorPaint.setColor(ContextCompat.getColor(getContext(), R.color.doorColor));
        wallPaint.setStyle(Paint.Style.STROKE);
        doorPaint.setStyle(Paint.Style.STROKE);
        wallPaint.setStrokeWidth(paintStroke);
        doorPaint.setStrokeWidth(paintStroke);
    }
}
