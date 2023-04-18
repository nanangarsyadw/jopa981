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

package com.zftlive.android.sample.animation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zftlive.android.R;
import com.zftlive.android.library.Logger;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.tools.ToolUnit;
import com.zftlive.android.library.widget.viewpager.docker.ClipViewPager;
import com.zftlive.android.library.widget.viewpager.docker.RecyclingPagerAdapter;
import com.zftlive.android.library.widget.viewpager.docker.ScalePageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager缩放切换动画效果样例
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ScaleViewPagerActivity extends CommonActivity {

    private ClipViewPager mViewPager;
    private AdapterCycle mPagerAdapter;

    @Override
    public int bindLayout() {
        return R.layout.activity_scale_viewpager;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        mViewPager = (ClipViewPager) findViewById(R.id.viewpager);
        mViewPager.setPageTransformer(true, new ScalePageTransformer());
        findViewById(R.id.page_container).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });

        //初始化带返回按钮的标题栏
        String strCenterTitle = getResources().getString(R.string.ScaleViewPagerActivity);
        mWindowTitle.initBackTitleBar(strCenterTitle);
    }

    List<String> list = new ArrayList<>();
    @Override
    public void doBusiness(Context mContext) {

        list.add("http://7sbkek.com1.z0.glb.clouddn.com/style_jianyue.jpg");
        list.add("http://7sbkek.com1.z0.glb.clouddn.com/style_dny.jpg");
        list.add("http://7sbkek.com1.z0.glb.clouddn.com/style_dzh.jpg");
        list.add("http://7sbkek.com1.z0.glb.clouddn.com/style_meishi.jpg");
        list.add("http://7sbkek.com1.z0.glb.clouddn.com/style_oushi.jpg");
        list.add("http://7sbkek.com1.z0.glb.clouddn.com/style_rishi.jpg");
        list.add("http://7sbkek.com1.z0.glb.clouddn.com/style_zhongshi.jpg");
        list.add("http://7sbkek.com1.z0.glb.clouddn.com/style_xiandai.jpg");

        mPagerAdapter = new  AdapterCycle(this,mViewPager,list);
        mViewPager.setAdapter(mPagerAdapter);
//        int index = 0;
//        for (String imageUrl : list) {
//            ImageView imageView = imageView = new ImageView(mContext);
//            imageView.setImageResource(R.drawable.anl_shape_common_default);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            ImageLoader.getInstance().displayImage(list.get(index), imageView);
//            mPagerAdapter.addItem(imageView);
//            index++;
//        }

        //设置OffscreenPageLimit
        mViewPager.setOffscreenPageLimit(list.size());
        mViewPager.setPageMargin(ToolUnit.dipToPx(mContext, 15));
        mViewPager.setOnPageChangeListener(mPagerAdapter);
        mPagerAdapter.notifyDataSetChanged();
    }

//    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
//
//        @Override
//        public void onPageScrolled(int i, float v, int i1) {
//
//        }
//
//        @Override
//        public void onPageSelected(int position) {
//            View mItem = null;
//            String imageURL = "";
//            if(position == 0){
//                mItem = mPagerAdapter.getItem(position);
//                imageURL = list.get(list.size() - 1);
//            }else if(position == mPagerAdapter.getCount() - 1){
//                mItem = mPagerAdapter.getItem(position);
//                imageURL = list.get(0);
//            }else{
//                mItem = mPagerAdapter.getItem(position);
//                imageURL = list.get(position - 1);
//            }
//            ImageLoader.getInstance().displayImage(imageURL,((ImageView) mItem));
//
//            int pageIndex = position;
//            if(position == 0){
//                pageIndex = mPagerAdapter.getCount();
//            }else if(position == mPagerAdapter.getCount() + 1){
//                pageIndex = 1;
//            }
//            if(position != pageIndex){
//                mViewPager.setCurrentItem(pageIndex, false);
//                return;
//            }
//
//            String text = "";
//            int count = mPagerAdapter.getCount() - 2;
//            if(mPagerAdapter != null && count > 0){
//                int index = (position);
//                text = index + "/" + count;
//            }else{
//                text = 0 + " / " + 0;
//            }
//
//            ToolAlert.showCenterText(getContext(),text);
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int i) {
//
//        }
//    }

    class MyPagerAdapter extends PagerAdapter {
        private final List<View> pageViews;
        private final Context mContext;

        public MyPagerAdapter(Context context) {
            pageViews = new ArrayList<>();
            mContext = context;
        }

        @Override
        public Object instantiateItem(View container, int position) {
            View view = pageViews.get(position);
            ((ViewPager) container).addView(view);
            return view;

//            if (position == 0) {
//                pageViews.get(position).setImageResource(pics[2]);
//            } else if (i == (listviews.size() - 1)) {
//                listviews.get(i).setImageResource(pics[0]);
//            } else {
//                listviews.get(i).setImageResource(pics[i - 1]);
//            }
//            container.addView(listviews.get(i));
//            return listviews.get(i);
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            View view = pageViews.get(position % pageViews.size());
            ((ViewPager) container).removeView(view);
//            view.setImageBitmap(null);
        }

        @Override
        public int getCount() {
            return pageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

//        @Override
//        public int getItemPosition(Object object) {
//            return POSITION_NONE;
//        }

        public void addAll(List<View> list) {
            pageViews.addAll(list);
            notifyDataSetChanged();
        }

        public void addItem(View mView) {
            pageViews.add(mView);
        }

        public View getItem(int postion){
            return pageViews.get(postion);
        }
    }

    public class TubatuAdapter extends RecyclingPagerAdapter {

        private final List<String> mList;
        private final Context mContext;

        public TubatuAdapter(Context context) {
            mList = new ArrayList<>();
            mContext = context;
        }

        public void addAll(List<String> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            Logger.e(TAG,"绘制次数："+position);
            ImageView imageView = null;
            if (convertView == null) {
                imageView = new ImageView(mContext);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(R.drawable.anl_shape_common_default);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setTag(position);
            ImageLoader.getInstance().displayImage(mList.get(position),imageView);
            return imageView;
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
