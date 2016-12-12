package com.wut.indoornavigation.utils;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;


import com.wut.indoornavigation.data.model.Point;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Canvas extender class
 */
@Singleton
@Deprecated
public class CanvasExtender {

    @Inject
    public CanvasExtender() {

    }

    /**
     * Produces line on canvas
     * @param start Start point
     * @param end End point
     * @param paint Paint for line
     */
    public void DrawLine(Canvas canvas, Point start, Point end, Paint paint){
        canvas.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), paint);
    }

    /**
     * Produces filled rectangle with text in center on specified segment.
     * @param text Text to be displayed
     * @param textSize Text font size
     * @param start Start point of segment
     * @param end End point of segment
     * @param padding Padding for text
     * @param textPaint Paint for text
     * @param backgroundPaint Paint for background
     */
    public void DrawText(Canvas canvas, String text, float textSize, Point start, Point end, float padding, Paint textPaint, Paint backgroundPaint) {
        final Paint.FontMetrics fm = new Paint.FontMetrics();
        textPaint.setTextSize(textSize);
        textPaint.getFontMetrics(fm);

        float xAverage = (start.getX() + end.getX())/2;
        float yAverage = (start.getY() + end.getY())/2;

        float left = xAverage - padding;
        float top = yAverage + fm.top - padding;
        float right = xAverage + textPaint.measureText(text) + padding;
        float bottom = yAverage + fm.bottom + padding;
        canvas.drawRect(left, top, right, bottom, backgroundPaint);

        canvas.drawText(text, left, top, textPaint);
    }

    /**
     * Produces path on canvas
     * @param path Path element
     * @param paint Path paint
     */
    public void DrawPath(Canvas canvas, Path path, Paint paint){
        canvas.drawPath(path, paint);
    }
}
