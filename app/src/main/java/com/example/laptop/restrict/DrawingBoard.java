package com.example.laptop.restrict;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.util.ArrayList;

public class DrawingBoard extends View {

    private static final int JUST_SCALED_DURATION = 100;
    private static final int MAX_TOUCH_DURATION = 1000;
    private static final int MAX_TOUCH_DISTANCE = 10;
    private boolean stayedWithinTouchDistance;
    private float firstTouchX, firstTouchY;
    private long firstTouchTime, lastScaleTime;

    private float posX, posY, lastTouchX, lastTouchY;
    private ScaleGestureDetector scaleDetector;
    private float minScaleFactor = 0.1f;
    private float maxScaleFactor = 5.0f;
    private float scaleFactor = 1.0f;
    private float cellSize = 50.0f;
    private int numCells = 50;

    private ArrayList<Point> points;
    private Paint linePaint, pointPaint;

    public DrawingBoard(Context context) {
        this(context, null, 0);
    }

    public DrawingBoard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawingBoard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        points = new ArrayList<>();
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(-65536);
        linePaint.setStrokeWidth(1.0f);
        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setColor(-65536);
        pointPaint.setStrokeWidth(1.0f);
        posX = linePaint.getStrokeWidth();
        posY = linePaint.getStrokeWidth();
        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    private static float distancePx(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    private float distanceDp(float distancePx) {
        return distancePx / getResources().getDisplayMetrics().density;
    }

    private Point coerceToGrid(Point point) {
        point.x = (int) ((((int) (point.x / cellSize)) * cellSize) + (cellSize / 2));
        point.y = (int) ((((int) (point.y / cellSize)) * cellSize) + (cellSize / 2));
        return point;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        scaleDetector.onTouchEvent(event);
        final int action = MotionEventCompat.getActionMasked(event);
        if (action == MotionEvent.ACTION_DOWN) {
            stayedWithinTouchDistance = true;
            firstTouchX = lastTouchX = event.getRawX();
            firstTouchY = lastTouchY = event.getRawY();
            firstTouchTime = System.currentTimeMillis();
        } else if (action == MotionEvent.ACTION_MOVE) {
            float thisTouchX = event.getRawX();
            float thisTouchY = event.getRawY();

            boolean justScaled = System.currentTimeMillis() - lastScaleTime < JUST_SCALED_DURATION;
            float distancePx = distancePx(firstTouchX, firstTouchY, thisTouchX, thisTouchY);
            stayedWithinTouchDistance = stayedWithinTouchDistance &&
                    distanceDp(distancePx) < MAX_TOUCH_DISTANCE;

            if (!stayedWithinTouchDistance && !scaleDetector.isInProgress() && !justScaled) {
                posX += thisTouchX - lastTouchX;
                posY += thisTouchY - lastTouchY;
                invalidate();
            }

            lastTouchX = thisTouchX;
            lastTouchY = thisTouchY;
        } else if (action == MotionEvent.ACTION_UP) {
            long touchDuration = System.currentTimeMillis() - firstTouchTime;
            if (touchDuration < MAX_TOUCH_DURATION && stayedWithinTouchDistance) {
                int[] location = {0, 0};
                getLocationOnScreen(location);
                float x = ((lastTouchX - posX - location[0]) / scaleFactor);
                float y = ((lastTouchY - posY - location[1]) / scaleFactor);
                points.add(coerceToGrid(new Point((int) x, (int) y)));
                invalidate();
            }
        }
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            lastScaleTime = System.currentTimeMillis();
            float scale = detector.getScaleFactor();
            scaleFactor = Math.max(minScaleFactor, Math.min(scaleFactor * scale, maxScaleFactor));
            if (scaleFactor > minScaleFactor && scaleFactor < maxScaleFactor) {
                float centerX = detector.getFocusX();
                float centerY = detector.getFocusY();
                float diffX = centerX - posX;
                float diffY = centerY - posY;
                diffX = diffX * scale - diffX;
                diffY = diffY * scale - diffY;
                posX -= diffX;
                posY -= diffY;
                invalidate();
                return true;
            }
            return false;
        }
    }

    private float getScaledCellSize() {
        return scaleFactor * cellSize;
    }

    private float getScaledBoardSize() {
        return numCells * getScaledCellSize();
    }

    private void drawBoard(Canvas canvas) {
        for (int i = 0; i <= numCells; i++) {
            float total = getScaledBoardSize();
            float offset = getScaledCellSize() * i;
            canvas.drawLine(offset, 0, offset, total, linePaint);
            canvas.drawLine(0, offset, total, offset, linePaint);
        }
    }

    private void drawPoints(Canvas canvas) {
        for (Point point : points) {
            float x = point.x * scaleFactor;
            float y = point.y * scaleFactor;
            float r = getScaledCellSize() / 4;
            canvas.drawCircle(x, y, r, pointPaint);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float total = getScaledBoardSize();
        float edge = linePaint.getStrokeWidth();
        posX = Math.max(Math.min(edge, getWidth() - total - edge), Math.min(edge, posX));
        posY = Math.max(Math.min(edge, getHeight() - total - edge), Math.min(edge, posY));

        canvas.save();
        canvas.translate(posX, posY);
        drawBoard(canvas);
        drawPoints(canvas);
        canvas.restore();
    }

}
