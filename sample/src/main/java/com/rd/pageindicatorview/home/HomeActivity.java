package com.rd.pageindicatorview.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.rd.PageIndicatorView;
import com.rd.pageindicatorview.base.BaseActivity;
import com.rd.pageindicatorview.customize.CustomizeActivity;
import com.rd.pageindicatorview.data.Customization;
import com.rd.pageindicatorview.sample.R;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends BaseActivity {

    private PageIndicatorView pageIndicatorView;
    private Customization customization;

    public static Intent launchWithViewPager(Context context) {
        return new Intent(context, HomeActivity.class).putExtra("init_with_view_pager", true);
    }

    public static Intent launchWithViewPager2(Context context) {
        return new Intent(context, HomeActivity.class).putExtra("init_with_view_pager_2", true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().getBooleanExtra("init_with_view_pager", false)) {
            setContentView(R.layout.ac_home_vp);
        }

        if(getIntent().getBooleanExtra("init_with_view_pager_2", false)) {
            setContentView(R.layout.ac_home_vp2);
        }

        customization = new Customization();

        initToolbar();
        initViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        boolean customization = requestCode == CustomizeActivity.EXTRAS_CUSTOMIZATION_REQUEST_CODE && resultCode == RESULT_OK;
        if (customization && intent != null) {
            this.customization = intent.getParcelableExtra(CustomizeActivity.EXTRAS_CUSTOMIZATION);
            updateIndicator();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customize, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionCustomize:
                CustomizeActivity.start(this, customization);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void initViews() {
        final Object pager = findViewById(R.id.viewPager);
        if(pager instanceof ViewPager) {
            HomePagerAdapter homePagerAdapter = new HomePagerAdapter();
            homePagerAdapter.setData(createPageList());

            ((ViewPager) pager).setAdapter(homePagerAdapter);
        } else if(pager instanceof ViewPager2) {{
            HomeAdapter homeAdapter = new HomeAdapter();
            homeAdapter.setData(createPageList());
            ((ViewPager2) pager).setAdapter(homeAdapter);
        }}

        pageIndicatorView = findViewById(R.id.pageIndicatorView);
    }

    @NonNull
    private List<View> createPageList() {
        List<View> pageList = new ArrayList<>();
        pageList.add(createPageView(R.color.google_red));
        pageList.add(createPageView(R.color.google_blue));
        pageList.add(createPageView(R.color.google_yellow));
        pageList.add(createPageView(R.color.google_green));

        return pageList;
    }

    @NonNull
    private View createPageView(int color) {
        View view = new View(this);
        view.setBackgroundColor(getResources().getColor(color));

        return view;
    }

    private void updateIndicator() {
        if (customization == null) {
            return;
        }

        pageIndicatorView.setAnimationType(customization.getAnimationType());
        pageIndicatorView.setOrientation(customization.getOrientation());
        pageIndicatorView.setRtlMode(customization.getRtlMode());
        pageIndicatorView.setInteractiveAnimation(customization.isInteractiveAnimation());
        pageIndicatorView.setAutoVisibility(customization.isAutoVisibility());
        pageIndicatorView.setFadeOnIdle(customization.isFadeOnIdle());
    }
}
