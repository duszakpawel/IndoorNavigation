package com.wut.indoornavigation.map.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.wut.indoornavigation.R;
import com.wut.indoornavigation.data.model.BuildingData;
import com.wut.indoornavigation.data.model.Door;
import com.wut.indoornavigation.data.model.Elevator;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.data.model.Stairs;
import com.wut.indoornavigation.data.model.Wall;
import com.wut.indoornavigation.extensions.CanvasExtender;
import com.wut.indoornavigation.map.MapEngine;
import com.wut.indoornavigation.map.OnMapReadyListener;

import java.util.HashMap;
import java.util.Map;

public final class MapEngineImpl implements MapEngine {

    private static final int DEFAULT_MAP_WIDTH = 100;
    private static final int DEFAULT_MAP_HEIGHT = 100;

    private static final float TEXT_SIZE = 10;
    private static final float TEXT_PADDING = 5;

    private Paint wallPaint;
    private Paint doorPaint;
    private Paint elevatorPaint;
    private Paint stairsPaint;
    private Paint textPaint;
    private Paint textBackgroundPaint;

    private Context context;
    private final Map<Integer, Bitmap> mapBitmaps;

    private OnMapReadyListener onMapReadyListener = OnMapReadyListener.NULL;

    public MapEngineImpl(Context context) {
        this.context = context;
        mapBitmaps = new HashMap<>();
        init();
    }

    private void init() {
        wallPaint = new Paint();
        wallPaint.setColor(ContextCompat.getColor(context, R.color.wallColor));
        doorPaint = new Paint();
        doorPaint.setColor(ContextCompat.getColor(context, R.color.doorColor));
        elevatorPaint = new Paint();
        elevatorPaint.setColor(ContextCompat.getColor(context, R.color.elevatorColor));
        stairsPaint = new Paint();
        stairsPaint.setColor(ContextCompat.getColor(context, R.color.stairsColor));
        textPaint = new Paint();
        textPaint.setColor(ContextCompat.getColor(context, R.color.textColor));
        textBackgroundPaint = new Paint();
        textBackgroundPaint.setColor(ContextCompat.getColor(context, R.color.textBackgroundColor));
    }

    @Override
    public void renderMap(@NonNull BuildingData buildingData) {
        for (Floor floor : buildingData.Floors) {
            Bitmap bitmap = Bitmap.createBitmap(DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT, Bitmap.Config.ARGB_8888);
            mapBitmaps.put(floor.Number, bitmap);

            Canvas canvas = new Canvas(bitmap);
            renderFloor(canvas, floor);
        }

        onMapReadyListener.onMapReady();
    }

    private void renderFloor(Canvas canvas, Floor floor) {
        CanvasExtender canvasExtender = new CanvasExtender(canvas);

        for(Wall wall : floor.Walls){
            canvasExtender.DrawLine(wall.Start, wall.End, wallPaint);
        }

        for(Door door : floor.Doors){
            canvasExtender.DrawLine(door.Start, door.End, doorPaint);

            if(door.isDestinationPoint){
                String text = door.Id.toString();
                canvasExtender.DrawText(text, TEXT_SIZE, door.Start, door.End, TEXT_PADDING, textPaint, textBackgroundPaint);
            }
        }

        for(Stairs stairs : floor.Stairs){
            canvasExtender.DrawLine(stairs.Start, stairs.End, stairsPaint);
        }

        for(Elevator elevator : floor.Elevators){
            canvasExtender.DrawLine(elevator.Start, elevator.End, elevatorPaint);
        }
    }

    @Override
    public void setOnMapReadyListener(OnMapReadyListener onMapReadyListener) {
        this.onMapReadyListener = onMapReadyListener == null ? OnMapReadyListener.NULL : onMapReadyListener;
    }
}
