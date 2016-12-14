package com.wut.indoornavigation.render.map.impl;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;

import com.wut.indoornavigation.R;
import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.data.model.FloorObject;
import com.wut.indoornavigation.data.model.Room;
import com.wut.indoornavigation.render.RenderEngine;
import com.wut.indoornavigation.render.map.MapEngine;
import com.wut.indoornavigation.render.map.OnMapReadyListener;


import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of {@link MapEngine}
 */
public final class MapEngineImpl extends RenderEngine implements MapEngine {

    private final Paint wallPaint = new Paint();
    private final Paint doorPaint = new Paint();
    private final Paint roomPaint = new Paint();
    private final Paint elevatorPaint = new Paint();
    private final Paint stairsPaint = new Paint();
    private final Paint textPaint = new Paint();
    private final Paint textBackgroundPaint = new Paint();

    private final SparseArray<Bitmap> mapBitmaps;
    private final List<Integer> keyList;
    private final List<Integer> roomNumbers;

    private float textSize;
    private float textPadding;

    private OnMapReadyListener onMapReadyListener = OnMapReadyListener.NULL;

    @Inject
    public MapEngineImpl() {
        keyList = new LinkedList<>();
        roomNumbers = new LinkedList<>();
        mapBitmaps = new SparseArray<>();
    }

    private void init(Context context) {
        final Resources resources = context.getResources();
        textSize = resources.getDimension(R.dimen.map_text_size);
        textPadding = resources.getDimension(R.dimen.max_text_padding);
        wallPaint.setColor(ContextCompat.getColor(context, R.color.wallColor));
        doorPaint.setColor(ContextCompat.getColor(context, R.color.doorColor));
        roomPaint.setColor(ContextCompat.getColor(context, R.color.roomColor));
        elevatorPaint.setColor(ContextCompat.getColor(context, R.color.elevatorColor));
        stairsPaint.setColor(ContextCompat.getColor(context, R.color.stairsColor));
        textPaint.setColor(ContextCompat.getColor(context, R.color.textColor));
        textBackgroundPaint.setColor(ContextCompat.getColor(context, R.color.textBackgroundColor));

        getMapHeight(context);
        getMapWidth(context);
    }

    @Override
    public void renderMap(Context context, @NonNull Building building) {
        init(context);
        for (final Floor floor : building.getFloors()) {
            final Bitmap bitmap = Bitmap.createBitmap(mapWidth, mapHeight, Bitmap.Config.ARGB_8888);

            addRoomNumbers(floor.getRooms());
            keyList.add(floor.getNumber());
            mapBitmaps.put(floor.getNumber(), bitmap);
            renderFloor(bitmap, floor);
        }

        onMapReadyListener.onMapReady();
        onMapReadyListener = OnMapReadyListener.NULL;
    }

    @Override
    public void setOnMapReadyListener(OnMapReadyListener onMapReadyListener) {
        this.onMapReadyListener = onMapReadyListener == null ? OnMapReadyListener.NULL : onMapReadyListener;
    }

    @Override
    public List<Integer> getFloorNumbers() {
        return keyList;
    }

    @Override
    public List<Integer> getRoomNumbers() {
        return roomNumbers;
    }

    @NonNull
    @Override
    public Bitmap getMapForFloor(int floorNumber) {
        final Bitmap bitmap = mapBitmaps.get(floorNumber);
        if (bitmap != null) {
            return bitmap;
        }
        throw new IllegalStateException("There is no map for floor: " + floorNumber);
    }

    private void renderFloor(Bitmap bitmap, Floor floor) {
        final Canvas canvas = new Canvas(bitmap);
        final FloorObject[][] map = floor.getEnumMap();
        final int stepWidth = calculateStepWidth(map[0].length);
        final int stepHeight = calculateStepHeight(map.length);

        setPaintsStrokeWidth(stepWidth);

        int currentHeight = stepHeight * 2;
        int currentWidth = 0;

        for (int i = 0 ; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // TODO: make decision if the if statement will fix the bug with empty cells
                if(map[i][j]!=null) {
                    switch (map[i][j]) {
                        case SPACE:
                            break;
                        case ELEVATOR:
                            canvas.drawLine(currentWidth, currentHeight,
                                    currentWidth + stepWidth, currentHeight, elevatorPaint);
                            break;
                        case STAIRS:
                            canvas.drawLine(currentWidth, currentHeight,
                                    currentWidth + stepWidth, currentHeight, stairsPaint);
                            break;
                        case DOOR:
                            canvas.drawLine(currentWidth, currentHeight,
                                    currentWidth + stepWidth, currentHeight, doorPaint);
                            break;
                        case ROOM:
                            canvas.drawLine(currentWidth, currentHeight,
                                    currentWidth + stepWidth, currentHeight, roomPaint);
                            break;
                        default:
                            canvas.drawLine(currentWidth, currentHeight,
                                    currentWidth + stepWidth, currentHeight, wallPaint);
                    }
                }
                currentWidth += stepWidth;
            }
            currentHeight += stepWidth;
            currentWidth = 0;
        }
    }


    private void addRoomNumbers(List<Room> rooms) {
        for (final Room room : rooms) {
            roomNumbers.add(room.getNumber());
        }
    }

    private void setPaintsStrokeWidth(int paintsStrokeWidth) {
        wallPaint.setStrokeWidth(paintsStrokeWidth);
        doorPaint.setStrokeWidth(paintsStrokeWidth);
        elevatorPaint.setStrokeWidth(paintsStrokeWidth);
        stairsPaint.setStrokeWidth(paintsStrokeWidth);
        roomPaint.setStrokeWidth(paintsStrokeWidth);
    }
}
