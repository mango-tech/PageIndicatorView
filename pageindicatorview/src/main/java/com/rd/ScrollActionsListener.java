package com.rd;

import android.view.MotionEvent;

/**
 * Interface definition for a callbacks when any scroll action is invoked.
 */
public interface ScrollActionsListener {

    /**
     * This method will be invoked when a touch event is dispatched to a pager.
     *
     * @param event
     * @return True if the listener has consumed the event, false otherwise.
     */
    boolean onTouch(MotionEvent event);

    /**
     * This method will be invoked when the current page is scrolled.
     *
     * @param position Position index of the first page currently being displayed
     * @param positionOffset Value from [0, 1) indicating the offset from the page at position
     */
    void onPageScrolled(int position, float positionOffset);

    /**
     * This method will be invoked when a new page becomes selected.
     *
     * @param position Position index of the new seleced page
     */
    void onPageSelected(int position);

    /**
     * This method will be invoked when pager is in a idle state and the current page is fully in
     * view and no animation is in progress.
     */
    void onPagerScrollIsIdle();

    /**
     * Notify listener that data contained on pager has changed.
     */
    void updateState();

}
