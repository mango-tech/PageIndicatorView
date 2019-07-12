package com.rd;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

/**
 * @author David García (david.garcia@inqbarna.com)
 * @version 1.0 2019-07-12
 */
public class PagerAttacherFactory {
    private PagerAttacherFactory() { }

    /**
     * Will return the proper compatible attacher.
     * @param viewPager Needs to be either ViewPager or ViewPager2, any other kind of view returns null
     * @return attacher for given ViewPager/ViewPager2... other kind of views produce null
     */
    @Nullable
    public static PageIndicatorView.PagerAttacher create(@NonNull View viewPager) {
        if(viewPager instanceof ViewPager){
            return new ViewPagerAttacher((ViewPager) viewPager);
        } else if(viewPager instanceof ViewPager2) {
            return new ViewPager2Attacher((ViewPager2) viewPager);
        } else {
            return null;
        }
    }
}
