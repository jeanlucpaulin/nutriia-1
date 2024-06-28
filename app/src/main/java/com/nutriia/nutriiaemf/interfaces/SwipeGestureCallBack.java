package com.nutriia.nutriiaemf.interfaces;

import com.nutriia.nutriiaemf.detectors.SwipeGestureDetector;

/**
 * Interface for swipe gesture callback
 */
public interface SwipeGestureCallBack {
    /**
     * Method called when a swipe gesture is detected
     * @param direction the direction of the swipe
     */
    void onSwipe(SwipeGestureDetector.SwipeDirection direction);
}
