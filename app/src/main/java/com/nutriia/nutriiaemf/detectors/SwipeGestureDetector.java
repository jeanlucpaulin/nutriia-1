package com.nutriia.nutriiaemf.detectors;

import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

import com.nutriia.nutriiaemf.interfaces.SwipeGestureCallBack;

public class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {

    public enum SwipeDirection {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private final SwipeGestureCallBack callBack;

    public SwipeGestureDetector(SwipeGestureCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public boolean onFling(MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        if(e1 == null) return false;
        float diffX = e2.getX() - e1.getX();
        float diffY = e2.getY() - e1.getY();
        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    callBack.onSwipe(SwipeDirection.RIGHT);
                } else {
                    callBack.onSwipe(SwipeDirection.LEFT);
                }
                return true;
            }
        } else {
            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    callBack.onSwipe(SwipeDirection.DOWN);
                } else {
                    callBack.onSwipe(SwipeDirection.UP);
                }
                return true;
            }
        }
        return false;
    }
}
