package com.wut.indoornavigation.map.impl;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.wut.indoornavigation.R;
import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.map.MapEngine;
import com.wut.indoornavigation.map.OnMapReadyListener;
import com.wut.indoornavigation.utils.CanvasExtender;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public final class MapEngineImpl implements MapEngine {
    private static final int DEFAULT_MAP_WIDTH = 100;
    private static final int DEFAULT_MAP_HEIGHT = 100;

    private final float textSize;
    private final float textPadding;

    private final Paint wallPaint = new Paint();
    private final Paint doorPaint = new Paint();
    private final Paint elevatorPaint = new Paint();
    private final Paint stairsPaint = new Paint();
    private final Paint textPaint = new Paint();
    private final Paint textBackgroundPaint = new Paint();

    private final Context context;
    private final CanvasExtender canvasExtender;
    private final Map<Integer, Bitmap> mapBitmaps;

    private OnMapReadyListener onMapReadyListener = OnMapReadyListener.NULL;

    @Inject
    public MapEngineImpl(Context context, CanvasExtender canvasExtender) {
        this.context = context;
        this.canvasExtender = canvasExtender;
        mapBitmaps = new HashMap<>();
        init();

        final Resources resources = context.getResources();
        textSize = resources.getDimension(R.dimen.map_text_size);
        textPadding = resources.getDimension(R.dimen.max_text_padding);

    }

    private void init() {
        wallPaint.setColor(ContextCompat.getColor(context, R.color.wallColor));
        doorPaint.setColor(ContextCompat.getColor(context, R.color.doorColor));
        elevatorPaint.setColor(ContextCompat.getColor(context, R.color.elevatorColor));
        stairsPaint.setColor(ContextCompat.getColor(context, R.color.stairsColor));
        textPaint.setColor(ContextCompat.getColor(context, R.color.textColor));
        textBackgroundPaint.setColor(ContextCompat.getColor(context, R.color.textBackgroundColor));
    }

    @Override
    public void renderMap(@NonNull Building building) {
        for (Floor floor : building.getFloors()) {
            Bitmap bitmap = Bitmap.createBitmap(DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT, Bitmap.Config.ARGB_8888);
            mapBitmaps.put(floor.getNumber(), bitmap);

            Canvas canvas = new Canvas(bitmap);
            renderFloor(canvas, floor);
        }

        onMapReadyListener.onMapReady();
    }

    // TODO: 28.11.2016 Need to be changed
    private void renderFloor(Canvas canvas, Floor floor) {

//
//        for(Door door : floor.getDoors()){
//            canvasExtender.DrawLine(canvas, door.getStart(), door.getEnd(), doorPaint);
//
//            if(door.isDestinationPoint()){
//                String text = Integer.toString(door.getId());
//                canvasExtender.DrawText(canvas, text, textSize, door.getStart(), door.getEnd(), textPadding, textPaint, textBackgroundPaint);
//            }
//        }
//
//        for(Stairs stairs : floor.getStairs()){
//            canvasExtender.DrawLine(canvas, stairs.getStart(), stairs.getEnd(), stairsPaint);
//        }
//
//        for(Elevator elevator : floor.getElevators()){
//            canvasExtender.DrawLine(canvas, elevator.getStart(), elevator.getEnd(), elevatorPaint);
//        }
    }

    @Override
    public void setOnMapReadyListener(OnMapReadyListener onMapReadyListener) {
        this.onMapReadyListener = onMapReadyListener == null ? OnMapReadyListener.NULL : onMapReadyListener;
    }
}
