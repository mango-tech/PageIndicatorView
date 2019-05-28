package com.rd;

import android.database.DataSetObserver;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

class ViewPagerAttacher implements PageIndicatorView.PagerAttacher<ViewPager> {

    private DataSetObserver dataSetObserver;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private ViewPager.OnAdapterChangeListener onAdapterChangeListener;
    private View.OnTouchListener onTouchListener;
    private ViewPager viewPager;
    private PagerAdapter adapter;

    private ScrollActionsListener listener;

    @Override
    public void attachToPager(@NonNull final ScrollActionsListener listener, @NonNull ViewPager pager, final boolean isDynamicCount) {
        this.listener = listener;
        this.adapter = pager.getAdapter();
        this.viewPager = pager;

        registerObserver();

        onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                listener.onPageScrolled(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                listener.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    listener.onPagerScrollIsIdle();
                }
            }
        };

        onAdapterChangeListener = new ViewPager.OnAdapterChangeListener() {

            @Override
            public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter) {
                if (isDynamicCount) {
                    if (oldAdapter != null && dataSetObserver != null) {
                        unregisterObserver();
                    }
                    registerObserver();
                }
                listener.updateState();
            }
        };

        onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return listener.onTouch(event);
            }
        };

        pager.addOnPageChangeListener(onPageChangeListener);
        pager.addOnAdapterChangeListener(onAdapterChangeListener);
        pager.setOnTouchListener(onTouchListener);

    }

    @Override
    public void detachFromPager() {
        unregisterObserver();
        viewPager.removeOnPageChangeListener(onPageChangeListener);
        viewPager.removeOnAdapterChangeListener(onAdapterChangeListener);
        viewPager.setOnTouchListener(null);
    }

    @Override
    public void registerObserver() {
        if (dataSetObserver != null || adapter == null) {
            return;
        }

        dataSetObserver = new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listener.updateState();
            }
        };

        try {
            adapter.registerDataSetObserver(dataSetObserver);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unregisterObserver() {
        if (dataSetObserver == null || adapter == null) {
            return;
        }

        try {
            adapter.unregisterDataSetObserver(dataSetObserver);
            dataSetObserver = null;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCurrentItem() {
        return viewPager.getCurrentItem();
    }

    @Override
    public int getCount() {
        return adapter.getCount();
    }
}
