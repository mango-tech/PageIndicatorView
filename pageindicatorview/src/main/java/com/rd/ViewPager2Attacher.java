package com.rd;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

class ViewPager2Attacher implements PageIndicatorView.PagerAttacher {

    private RecyclerView.AdapterDataObserver adapterDataObserver;
    private ViewPager2.OnPageChangeCallback onPageChangeCallback;
    private View.OnTouchListener onTouchListener;

    private ViewPager2 viewPager;
    private RecyclerView.Adapter adapter;

    private ScrollActionsListener listener;

    public ViewPager2Attacher(@NonNull ViewPager2 pager) {
        this.viewPager = pager;
        this.adapter = pager.getAdapter();
    }

    @Override
    public int getId() {
        return viewPager.getId();
    }

    @Override
    public void attachToPager(@NonNull final ScrollActionsListener listener, boolean isDynamicCount) {
        this.listener = listener;

        registerObserver();

        onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
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
                if(state == ViewPager2.SCROLL_STATE_IDLE){
                    listener.onPagerScrollIsIdle();
                }
            }
        };

        onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return listener.onTouch(event);
            }
        };

        viewPager.registerOnPageChangeCallback(onPageChangeCallback);
        viewPager.setOnTouchListener(onTouchListener);
    }

    @Override
    public void detachFromPager() {
        unregisterObserver();
        viewPager.unregisterOnPageChangeCallback(onPageChangeCallback);
        viewPager.setOnTouchListener(null);
    }

    @Override
    public boolean isAttached() {
        return viewPager != null;
    }

    @Override
    public void registerObserver() {
        if (adapterDataObserver != null || adapter == null) {
            return;
        }

        adapterDataObserver = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listener.updateState();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                listener.updateState();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
                super.onItemRangeChanged(positionStart, itemCount, payload);
                listener.updateState();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                listener.updateState();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                listener.updateState();
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                listener.updateState();
            }
        };

        adapter.registerAdapterDataObserver(adapterDataObserver);
    }

    @Override
    public void unregisterObserver() {
        if (adapterDataObserver == null || adapter == null) {
            return;
        }

        try {
            adapter.unregisterAdapterDataObserver(adapterDataObserver);
            adapterDataObserver = null;
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
        return adapter.getItemCount();
    }
}
