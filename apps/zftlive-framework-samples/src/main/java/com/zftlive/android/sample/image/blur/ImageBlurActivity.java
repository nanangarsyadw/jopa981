/*
 *     Android基础开发个人积累、沉淀、封装、整理共通
 *     Copyright (c) 2016. 曾繁添 <zftlive@163.com>
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
 */

package com.zftlive.android.sample.image.blur;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.zftlive.android.R;

/**
 * 图片高斯模糊效果
 * @author 曾繁添
 * @version 1.0
 *
 */
@SuppressLint("NewApi")
public class ImageBlurActivity extends FragmentActivity {

    private CustomPagerAdapter pagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageblur_main);
        
		// 初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.ImageBlurActivity);
//		ActionBarManager.initBackTitle(this, getActionBar(),strCenterTitle);
        
        pagerAdapter =
                new CustomPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);


//        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
//            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
//                viewPager.setCurrentItem(tab.getPosition(), true);
//            }
//
//            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
//                // hide the given tab
//            }
//
//            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
//                // probably ignore this event
//            }
//        };
//
//        for (int i = 0; i < pagerAdapter.getCount(); i++) {
//            getActionBar().addTab(
//                    getActionBar().newTab()
//                            .setText(pagerAdapter.getPageTitle(i))
//                            .setTabListener(tabListener)
//            );
//        }
//
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                getActionBar().setSelectedNavigationItem(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    public class CustomPagerAdapter extends FragmentStatePagerAdapter {

        private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        public CustomPagerAdapter(FragmentManager fm) {
            super(fm);
            // 使用这种方式只能在Android 4.4 及其以后版本才能使用
            // This should sdk >= 4.4
            // fragments.add(Fragment.instantiate(MainActivity.this, RSBlurFragment.class.getName()));
            fragments.add(Fragment.instantiate(ImageBlurActivity.this, FastBlurFragment.class.getName()));
            fragments.add(Fragment.instantiate(ImageBlurActivity.this, JniBlurArrayFragment.class.getName()));
            fragments.add(Fragment.instantiate(ImageBlurActivity.this, JniBlurBitMapFragment.class.getName()));
            fragments.add(Fragment.instantiate(ImageBlurActivity.this, AnimatorBlurFragment.class.getName()));
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments.get(position).toString();
        }
    }
}

