package com.wut.indoornavigation.render.position;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.wut.indoornavigation.R;
import com.wut.indoornavigation.data.model.FloorObject;
import com.wut.indoornavigation.data.model.Point;
import com.wut.indoornavigation.data.storage.BuildingStorage;
import com.wut.indoornavigation.render.RenderEngine;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Engine for drawing user position on the map
 */
@Singleton
public class PositionEngine extends RenderEngine {

    private static final float USER_POSITION_SCALE_DOWN = 2;
    private static final float USER_POSITION_SCALE_UP = 3 / 2;

    private final BuildingStorage buildingStorage;
    private Paint userPositionPaint;
    private int userPositionSize;

    @Inject
    PositionEngine(BuildingStorage buildingStorage) {
        this.buildingStorage = buildingStorage;
    }

    /**
     * Inits position engine
     *
     * @param context view context
     */
    public void init(Context context) {
        userPositionPaint = new Paint();
        userPositionSize = (int) context.getResources().getDimension(R.dimen.user_position_size);
        userPositionPaint.setColor(ContextCompat.getColor(context, R.color.userPositionColor));
        calculateMapHeight(context);
        calculateMapWidth(context);
    }

    /**
     * Renders user position on the map
     *
     * @param map          map
     * @param userPosition current user position
     * @return bitmap with user position
     */
    public Bitmap renderMapWithUserPosition(Bitmap map, Point userPosition) {
        final Bitmap bitmap = map.copy(Bitmap.Config.ARGB_8888, true);
        final Canvas canvas = new Canvas(bitmap);

        canvas.drawOval(getUserPositionOval(userPosition), userPositionPaint);
        return bitmap;
    }

    @NonNull
    private Point calculateScaledPoint(int stepWidth, int stepHeight, Point position) {
        stepWidth /= USER_POSITION_SCALE_DOWN;
        stepHeight /= USER_POSITION_SCALE_DOWN;
        final float xValue = position.getY() * USER_POSITION_SCALE_DOWN * stepWidth + stepWidth / USER_POSITION_SCALE_DOWN;
        final float yValue = position.getX() * USER_POSITION_SCALE_DOWN * stepWidth + USER_POSITION_SCALE_DOWN * stepHeight;

        return new Point(xValue, yValue, position.getZ());
    }

    private RectF getUserPositionOval(Point userPosition) {
        final FloorObject[][] buildingMap = buildingStorage.getBuilding().getFloors().get((int) userPosition.getZ()).getEnumMap();

        final int stepWidth = calculateStepWidth(buildingMap[0].length);
        final int stepHeight = calculateStepWidth(buildingMap.length);

        final Point scaledPoint = calculateScaledPoint(stepWidth, stepHeight, userPosition);

        return new RectF(scaledPoint.getX() + userPositionSize / USER_POSITION_SCALE_DOWN,
                scaledPoint.getY() + userPositionSize / USER_POSITION_SCALE_DOWN,
                scaledPoint.getX() + userPositionSize * USER_POSITION_SCALE_UP,
                scaledPoint.getY() + userPositionSize * USER_POSITION_SCALE_UP);
    }
}
