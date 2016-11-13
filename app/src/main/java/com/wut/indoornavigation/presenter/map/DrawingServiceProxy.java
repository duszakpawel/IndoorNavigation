package com.wut.indoornavigation.presenter.map;


import android.graphics.Canvas;
import android.graphics.Path;

public interface DrawingServiceProxy {
    void DrawMap(Canvas canvas);
    void Clear(Canvas canvas);
    void Cache(Canvas canvas);
    void DrawPath(Canvas canvas, Path path);
}
