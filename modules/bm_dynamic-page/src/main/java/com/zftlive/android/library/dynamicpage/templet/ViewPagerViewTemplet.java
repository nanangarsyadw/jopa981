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
 *
 */

package com.zftlive.android.library.dynamicpage.templet;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.zftlive.android.library.Logger;
import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.common.adapter.BasicPagerAdapter;
import com.zftlive.android.library.tools.ToolString;
import com.zftlive.android.library.dynamicpage.R;
import com.zftlive.android.library.dynamicpage.bean.ElementViewPagerBean;
import com.zftlive.android.library.dynamicpage.bean.PageFloorGroupElement;
import com.zftlive.android.library.dynamicpage.bean.PageListElementBean;

/**
 * ViewPager视图模板
 *
 * @author 曾繁添
 * @version 1.0
 */
public abstract class ViewPagerViewTemplet extends AbsPageViewTemplet {

    /**
     * 横向滚动ViewPager
     */
    protected ViewPager mViewPager;

    /**
     * VP适配器
     */
    protected BasicPagerAdapter mAdapter;

    /**
     * 元素容器
     */
    protected LinearLayout mIndicator;

    /**
     * 默认、选中指示器
     */
    protected GradientDrawable mSelectedDot, mUnSelectedDot;

    /**
     * item的数量
     */
    protected int itemCount = 0;

    public ViewPagerViewTemplet(Context mContext) {
        super(mContext);
    }

    @Override
    public int bindLayout() {
        return R.layout.dynamic_element_viewpager;
    }

    @Override
    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.cus_viewpager);
        mIndicator = (LinearLayout) findViewById(R.id.ll_indicator);
        mAdapter = new BasicPagerAdapter();
        mViewPager.setAdapter(mAdapter);

        //指示器颜色
        mSelectedDot = new GradientDrawable();
        mUnSelectedDot = new GradientDrawable();
    }

    @Override
    public void fillData(IAdapterModel model, int postion) {
        this.position = postion;
        PageListElementBean rowBean = (PageListElementBean) model;
        if (null == rowBean || rowBean.pageFloorGroupElement == null) {
            Logger.e(TAG, postion + "-->数据为空");
            return;
        }
        this.rowData = rowBean.pageFloorGroupElement;
        //初始化数据
        PageFloorGroupElement itemData = rowBean.pageFloorGroupElement ;
        itemCount = gainPageItemCount(itemData.vpData);
        if (itemCount <= 0) {
            Logger.e(TAG, position + "-->ViewPager的Item数据为空");
            return;
        }
        //设置背景颜色
        mViewPager.setBackgroundColor(ToolString.getColor(itemData.group.floor.backgroundColor,COLOR_FFFFFF));
        mAdapter.clearViewItem();
        for (int i = 0; i < itemCount; i++) {
            View mItemView = makeViewPageItem(itemData.vpData, i);
            if (null != mItemView) {
                mAdapter.addViewItem(mItemView);
            }
        }
        mAdapter.notifyDataSetChanged();
        //初始化指示器
        makeViewPagerIndicator(itemData.vpData, itemCount);
        mViewPager.setOffscreenPageLimit(1);
        // Viewpager滑动监听器
        mViewPager.setOnPageChangeListener(makePageChangeListener(itemCount));
        mViewPager.setCurrentItem(itemData.vpData.selectedIndex);
        //刷新指示器
        refreshIndicator(itemCount);
    }

    /**
     * item个数
     *
     * @param vpData
     * @return
     */
    protected abstract int gainPageItemCount(ElementViewPagerBean vpData);

    /**
     * 构建每一个item布局+绑定数据
     *
     * @param vpData
     * @param index
     * @return
     */
    protected abstract View makeViewPageItem(ElementViewPagerBean vpData, int index);

    /**
     * 初始化ViewPager指示器
     *
     * @param vpData
     * @param count
     */
    protected void makeViewPagerIndicator(ElementViewPagerBean vpData, int count) {
        int dotWHDpVaule = 6;
        int dp1PxValue = getPxValueOfDp(1.0f);
        int halfDotWHPxVaule = getPxValueOfDp(dotWHDpVaule / 2.0f);
        int dotSelectedColor = ToolString.getColor(vpData.selectedDotColor, SELECTED_DOT_COLOR);
        mSelectedDot.setCornerRadius(halfDotWHPxVaule);
        mSelectedDot.setColor(dotSelectedColor);
        mSelectedDot.setStroke(dp1PxValue, dotSelectedColor);
        int dotUnSelectedColor = ToolString.getColor(vpData.unSelectedDotColor, UN_SELECTED_DOT_COLOR);
        mUnSelectedDot.setCornerRadius(halfDotWHPxVaule);
        mUnSelectedDot.setColor(dotUnSelectedColor);
        mUnSelectedDot.setStroke(dp1PxValue, dotUnSelectedColor);

        int mDotWH = getPxValueOfDp(dotWHDpVaule);
        LinearLayout.LayoutParams dotLp = new LinearLayout.LayoutParams(mDotWH, mDotWH);
        mIndicator.removeAllViews();
        for (int i = 0; i < count; i++) {
            final View indicater = new View(mContext);
            // 第一个View不设置marginLeft
            if (i != 0) {
                dotLp.leftMargin = mDotWH;
            }
            indicater.setLayoutParams(dotLp);
            indicater.setBackgroundDrawable(i == 0 ? mSelectedDot : mUnSelectedDot);
            mIndicator.addView(indicater, i);
        }
        // 如果只有一项不显示指示器
        if (count == 1) {
            mIndicator.setVisibility(View.GONE);
        }
        //指示器方向
        if (DIRECTION_LEFT == vpData.dotDirection) {
            mIndicator.setGravity(Gravity.LEFT);
        } else if (DIRECTION_CENTER == vpData.dotDirection) {
            mIndicator.setGravity(Gravity.CENTER_HORIZONTAL);
        } else if (DIRECTION_RIGHT == vpData.dotDirection) {
            mIndicator.setGravity(Gravity.RIGHT);
        }
    }

    /**
     * 更新指示器
     */
    protected void refreshIndicator(int itemCount){
        for (int index = 0; index < itemCount; index++) {
            final View dotView =  mIndicator.getChildAt(index);
            if (mViewPager.getCurrentItem() == index) {
                dotView.setBackgroundDrawable(mSelectedDot);
            } else {
                dotView.setBackgroundDrawable(mUnSelectedDot);
            }
        }
    }

    /**
     * 获取滑动监听器
     *
     * @param itemCount
     * @return
     */
    protected ViewPager.OnPageChangeListener makePageChangeListener(int itemCount) {
        //为了防止重复建立对象
        return mPageChangeListener;
    }

    /**
     * 滑动监听器
     */
    protected ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int currentIndex) {
            //循环输出
            for (int index = 0; index < itemCount; index++) {
                final View dotView = mIndicator.getChildAt(index);
                if (currentIndex == index) {
                    dotView.setBackgroundDrawable(mSelectedDot);
                } else {
                    dotView.setBackgroundDrawable(mUnSelectedDot);
                }
            }
            //回调外界
            onViewPagerItemSelected(currentIndex);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * 滑动回调函数
     * @param currentIndex
     */
    protected void onViewPagerItemSelected(int currentIndex) {
        //缓存当前选中的索引
        if(null != rowData){
            PageFloorGroupElement itemData = (PageFloorGroupElement)rowData ;
            itemData.vpData.selectedIndex = currentIndex;
        }
    }
}