package com.example.laptop.restrict;

import android.app.Application;
import android.graphics.Point;


public class Global extends Application {

    private Point popupPoint = null;

    public Point getPopupPoint() {
        return popupPoint;
    }

    public void setPopupPoint(Point popupPoint) {
        this.popupPoint = popupPoint;
    }
}
