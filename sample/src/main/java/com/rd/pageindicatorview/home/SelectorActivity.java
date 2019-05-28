package com.rd.pageindicatorview.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rd.pageindicatorview.base.BaseActivity;
import com.rd.pageindicatorview.sample.R;

public class SelectorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);
    }

    public void onViewPagerClick(View view) {
        Intent intent = HomeActivity.launchWithViewPager(this);
        startActivity(intent);
    }

    public void onViewPager2Click(View view) {
        Intent intent = HomeActivity.launchWithViewPager2(this);
        startActivity(intent);
    }

}
