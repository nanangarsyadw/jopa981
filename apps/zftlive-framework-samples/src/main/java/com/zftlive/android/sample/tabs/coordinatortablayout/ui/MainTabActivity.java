package com.zftlive.android.sample.tabs.coordinatortablayout.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.zftlive.android.R;
import com.zftlive.android.library.Logger;
import com.zftlive.android.library.base.bean.TabBean;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.common.adapter.BasicFragmentPagerAdapter;

/**
 * 多tab样例
 */
public class MainTabActivity extends CommonActivity {

    private CoordinatorLayout mCoordinatorTabLayout;
    private ViewPager mViewPager;
    private Context mContext;
    private Toolbar mToolbar;
    private ActionBar mActionbar;
    private TabLayout mTabLayout;
    private ImageView mImageView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    public int bindLayout() {
        return R.layout.activity_main_coordinator_tab;
    }

    @Override
    public void initView(View view) {
        //标题栏
        mWindowTitle.hiddeTitleBar();
//        mWindowTitle.initBackTitleBar("CoordinatorTabLayout样例");

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
        if (mActionbar != null) {
            mActionbar.setTitle("Demo");
            mActionbar.setDisplayHomeAsUpEnabled(true);
            mActionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        }

        //头部
        mImageView = (ImageView) findViewById(R.id.imageview);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingtoolbarlayout);
        mCoordinatorTabLayout = (CoordinatorLayout) findViewById(R.id.coordinatortablayout);

        //tab标签
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Logger.d("TAG", "onTabSelected");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        initViewPager();
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mViewPager.setOffscreenPageLimit(4);
        BasicFragmentPagerAdapter mVpAdapter = new BasicFragmentPagerAdapter(getSupportFragmentManager(), this);

        Bundle bundle = new Bundle();
        bundle.putString(MainFragment.ARG_TITLE, "Android");
        mVpAdapter.addItem(new TabBean("Android", MainFragment.class, bundle));

        bundle.putString(MainFragment.ARG_TITLE, "iOS");
        mVpAdapter.addItem(new TabBean("iOS", MainFragment.class, bundle));

        bundle.putString(MainFragment.ARG_TITLE, "Web");
        mVpAdapter.addItem(new TabBean("Web", MainFragment.class, bundle));

        bundle.putString(MainFragment.ARG_TITLE, "Other");
        mVpAdapter.addItem(new TabBean("Other", MainFragment.class, bundle));

        mViewPager.setAdapter(mVpAdapter);
        mViewPager.setOnPageChangeListener(mPageChangeListener);

        //tab和VP绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * VP滑动监听事件
     */
    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Logger.d(TAG, "onPageScrolled");
        }

        @Override
        public void onPageSelected(int position) {
            Logger.d(TAG, "onPageSelected");
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Logger.d(TAG, "onPageScrollStateChanged");
        }
    };
}
