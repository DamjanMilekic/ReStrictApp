package com.example.laptop.restrict;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by Laptop on 11/8/2017.
 */

public class Circle extends com.example.laptop.restrict.Shape {

    final float mRadius;
    float mScaleFactorCurrent;

    public Circle(float x, float y, float r, float mScaleFactorCurrent) {
        super(x, y);
        mRadius = r;
       this.mScaleFactorCurrent = mScaleFactorCurrent;
    }

    final float getRadius() {
        return mRadius;
    }

    public float getmScaleFactorCurrent()
    {
        return mScaleFactorCurrent;
    }
    public void setmScaleFactorCurrent(float r)
    {
        mScaleFactorCurrent=r;
    }



}
