/*
 *     Android基础开发个人积累、沉淀、封装、整理共通
 *     Copyright (c) 2017. 曾繁添 <zftlive@163.com>
 *     Github：https://github.com/zengfantian || http://git.oschina.net/zftlive
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package com.zftlive.android.sample.animation.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.zftlive.android.R;
import com.zftlive.android.library.widget.form.Option;
import com.zftlive.android.library.widget.form.SingleSpinner;
import com.zftlive.android.library.widget.form.SpinnerAdapter;
import com.zftlive.android.sample.animation.viewpager.indicator.CirclePageIndicator;
import com.zftlive.android.sample.animation.viewpager.transfor.AccordionTransformer;
import com.zftlive.android.sample.animation.viewpager.transfor.BackgroundToForegroundTransformer;
import com.zftlive.android.sample.animation.viewpager.transfor.CubeInTransformer;
import com.zftlive.android.sample.animation.viewpager.transfor.CubeOutTransformer;
import com.zftlive.android.sample.animation.viewpager.transfor.DefaultTransformer;
import com.zftlive.android.sample.animation.viewpager.transfor.DepthPageTransformer;
import com.zftlive.android.sample.animation.viewpager.transfor.FlipHorizontalTransformer;
import com.zftlive.android.sample.animation.viewpager.transfor.FlipVerticalTransformer;
import com.zftlive.android.sample.animation.viewpager.transfor.ForegroundToBackgroundTransformer;
import com.zftlive.android.sample.animation.viewpager.transfor.RotateDownTransformer;
import com.zftlive.android.sample.animation.viewpager.transfor.RotateUpTransformer;
import com.zftlive.android.sample.animation.viewpager.transfor.ScaleInOutTransformer;
import com.zftlive.android.sample.animation.viewpager.transfor.StackTransformer;
import com.zftlive.android.sample.animation.viewpager.transfor.TabletTransformer;
import com.zftlive.android.sample.animation.viewpager.transfor.ZoomInTransformer;
import com.zftlive.android.sample.animation.viewpager.transfor.ZoomOutPageTransformer;
import com.zftlive.android.sample.animation.viewpager.transfor.ZoomOutSlideTransformer;
import com.zftlive.android.sample.animation.viewpager.transfor.ZoomOutTranformer;

import java.util.ArrayList;
import java.util.List;

public class VpAnimMainActivity extends FragmentActivity {
    /**
     * 给ViewPager设置五页的展示页
     */
    private static final int NUM_PAGES = 5;
    private ViewPager mPager;
    //底部圆点的设置
    private CirclePageIndicator mPageIndicator;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity_vp_anim);
        // 初始化
        mPager = (ViewPager) findViewById(R.id.pager);
        mPageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPageIndicator.setViewPager(mPager);
        // 在这里可以随意选择com.way.transfor包里的任意一种动画效果
        // mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.setPageTransformer(true, new DepthPageTransformer());
        //mPager.setPageTransformer(true, new ZoomOutTranformer());


        final ArrayList<ViewPager.PageTransformer> animList = new ArrayList<>();
        animList.add(new DefaultTransformer());
        animList.add(new AccordionTransformer());
        animList.add(new BackgroundToForegroundTransformer());
        animList.add(new CubeInTransformer());
        animList.add(new CubeOutTransformer());
        animList.add(new DepthPageTransformer());
        animList.add(new FlipHorizontalTransformer());
        animList.add(new FlipVerticalTransformer());
        animList.add(new ForegroundToBackgroundTransformer());
        animList.add(new RotateDownTransformer());
        animList.add(new RotateUpTransformer());
        animList.add(new ScaleInOutTransformer());
        animList.add(new StackTransformer());
        animList.add(new TabletTransformer());
        animList.add(new ZoomInTransformer());
        animList.add(new ZoomOutPageTransformer());
        animList.add(new ZoomOutSlideTransformer());
        animList.add(new ZoomOutTranformer());

        SingleSpinner sp_anim_type = (SingleSpinner) findViewById(R.id.sp_anim_type);
        final List<Option> data = new ArrayList<Option>();
        data.add(new Option(DefaultTransformer.class.getName(), "DefaultTransformer"));
        data.add(new Option(AccordionTransformer.class.getName(), "AccordionTransformer"));
        data.add(new Option(BackgroundToForegroundTransformer.class.getName(), "BackgroundToForegroundTransformer"));
        data.add(new Option(CubeInTransformer.class.getName(), "CubeInTransformer"));
        data.add(new Option(CubeOutTransformer.class.getName(), "CubeOutTransformer"));
        data.add(new Option(DepthPageTransformer.class.getName(), "DepthPageTransformer"));
        data.add(new Option(FlipHorizontalTransformer.class.getName(), "FlipHorizontalTransformer"));
        data.add(new Option(FlipVerticalTransformer.class.getName(), "FlipVerticalTransformer"));
        data.add(new Option(ForegroundToBackgroundTransformer.class.getName(), "ForegroundToBackgroundTransformer"));
        data.add(new Option(RotateDownTransformer.class.getName(), "RotateDownTransformer"));
        data.add(new Option(RotateUpTransformer.class.getName(), "RotateUpTransformer"));
        data.add(new Option(ScaleInOutTransformer.class.getName(), "ScaleInOutTransformer"));
        data.add(new Option(StackTransformer.class.getName(), "StackTransformer"));
        data.add(new Option(TabletTransformer.class.getName(), "TabletTransformer"));
        data.add(new Option(ZoomInTransformer.class.getName(), "ZoomInTransformer"));
        data.add(new Option(ZoomOutPageTransformer.class.getName(), "ZoomOutPageTransformer"));
        data.add(new Option(ZoomOutSlideTransformer.class.getName(), "ZoomOutSlideTransformer"));
        data.add(new Option(ZoomOutTranformer.class.getName(), "ZoomOutTranformer"));


        SpinnerAdapter mSpinnerAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, data);
		mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_anim_type.setAdapter(mSpinnerAdapter);
        sp_anim_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                Toast.makeText(VpAnimMainActivity.this, "你点击的是:"+data.get(pos).getValue(), Toast.LENGTH_SHORT).show();
                mPager.setPageTransformer(true, animList.get(pos));
                mPager.setCurrentItem(0);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }

    // 先一页页的退换出切换过的ViewPager页面在退出
    // @Override
    // public void onBackPressed() {
    // if (mPager.getCurrentItem() == 0) {
    //
    // super.onBackPressed();
    // } else {
    // mPager.setCurrentItem(mPager.getCurrentItem() - 1);
    // }
    // }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 使用FragmentPagerAdapter设置页面
    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TestFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

    }
}
