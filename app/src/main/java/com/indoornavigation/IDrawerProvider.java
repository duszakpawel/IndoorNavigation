package com.indoornavigation;

import android.graphics.Canvas;
import android.graphics.Path;

interface IDrawerProvider
{
    void DrawMap(Canvas canvas);
    void Clear(Canvas canvas);
    void Cache(Canvas canvas);
    void DrawPath(Canvas canvas, Path path);
}
