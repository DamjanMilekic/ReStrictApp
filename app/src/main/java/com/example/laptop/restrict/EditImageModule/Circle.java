package com.example.laptop.restrict.EditImageModule;

/**
 * Created by Laptop on 11/8/2017.
 */

public class Circle extends com.example.laptop.restrict.EditImageModule.Shape {

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
