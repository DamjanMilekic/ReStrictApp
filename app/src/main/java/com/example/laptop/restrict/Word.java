package com.example.laptop.restrict;

/**
 * Created by Laptop on 12/7/2017.
 */

public class Word extends com.example.laptop.restrict.Shape {

    private float   x;
    private float    y;
    private String text;
    private float mScaleFactorWordCurrent;

    public Word(float _x,float _y,String _text,float mScaleFactorWordCurrent ) {
        super(_x,_y);
        this.text = _text;
        this.mScaleFactorWordCurrent=mScaleFactorWordCurrent;
    }

    public float getmScaleFactorWordCurrent() {
        return mScaleFactorWordCurrent;
    }

    public void setmScaleFactorWordCurrent(float mScaleFactorWordCurrent) {
        this.mScaleFactorWordCurrent = mScaleFactorWordCurrent;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
