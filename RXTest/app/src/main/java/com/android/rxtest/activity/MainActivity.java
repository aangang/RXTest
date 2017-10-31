package com.android.rxtest.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.rxtest.R;
import com.android.rxtest.activity.base.DrawerActivity;
import com.android.rxtest.adapter.ViewPagerAdapter;
import com.android.rxtest.fragment.AttentionFragment;
import com.android.rxtest.fragment.DiscoveryFragment;
import com.android.rxtest.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends DrawerActivity {
    private static final int PAGE_LIMIT = 2;//4;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivity(intent);
            }
        }
    }

    @Override
    protected void loadData() {
        List<Fragment> fragments = new ArrayList<>();
        //fragments.add(new AbilityFragment());
        fragments.add(new AttentionFragment());
        fragments.add(new DiscoveryFragment());
        //fragments.add(new ArenaFragment());
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(PAGE_LIMIT);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            Drawable drawable = null;
            switch (i) {
                case 0:
                    drawable = ContextCompat.getDrawable(this, R.drawable.tab_item_home);
                    break;
                case 1:
                    drawable = ContextCompat.getDrawable(this, R.drawable.tab_item_attention);
                    break;
                case 2:
                    drawable = ContextCompat.getDrawable(this, R.drawable.tab_item_discovery);
                    break;
                case 3:
                    drawable = ContextCompat.getDrawable(this, R.drawable.tab_item_arena);
                    break;
            }
            if (tab != null) {
                tab.setIcon(drawable);
            }
        }
    }


}
