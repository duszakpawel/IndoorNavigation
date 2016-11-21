package com.wut.indoornavigation.extensions;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.wut.indoornavigation.data.model.Point;

public interface CanvasExtender {
    void DrawLine(Canvas canvas, Point start, Point end, Paint paint);
    void DrawText(Canvas canvas, String text, float textSize, Point start, Point end, float padding, Paint textPaint, Paint backgroundPaint);
    void DrawPath(Canvas canvas, Path path, Paint paint);
}
