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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zftlive.android.R;

import java.util.LinkedList;
import java.util.List;

/**
 * 无限循环ViewPager
 */
public class AdapterCycle extends PagerAdapter implements ViewPager.OnPageChangeListener{

    private Context mContext;
    private LayoutInflater mInflater;
    private LinkedList<View> mViews;
    private List<String> mList;
    private ViewPager mViewPager;

    public AdapterCycle(Context context, ViewPager viewPager, List<String> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mViewPager = viewPager;
        mList = list;
        if (list != null) {

            //无论是否多于1个，都要初始化第一个（index:0）
            mViews = new LinkedList<View>();
            View root = mInflater.inflate(R.layout.fragment_zc_jingxuan_vp_item, null);
            ImageView view = (ImageView) root.findViewById(R.id.iv_item_picture);
            ImageLoader.getInstance().displayImage(list.get(list.size() - 1), view);
            mViews.add(view);

            //注意，如果不只1个，mViews比mList多两个（头尾各多一个）
            //假设：mList为mList[0~N-1], mViews为mViews[0~N+1]
            // mViews[0]放mList[N-1], mViews[i]放mList[i-1], mViews[N+1]放mList[0]
            // mViews[1~N]用于循环；首位之前的mViews[0]和末尾之后的mViews[N+1]用于跳转
            // 首位之前的mViews[0]，跳转到末尾（N）；末位之后的mViews[N+1]，跳转到首位（1）
            if( list.size() > 1) {
                //多于1个要循环
                for (String d : list) {
                    //中间的N个（index:1~N）
                    View rootItem = mInflater.inflate(R.layout.fragment_zc_jingxuan_vp_item, null);
                    ImageView v = (ImageView) rootItem.findViewById(R.id.iv_item_picture);
                    ImageLoader.getInstance().displayImage(d, v);
                    mViews.add(v);
                }

                //最后一个（index:N+1）
                View rootItem = mInflater.inflate(R.layout.fragment_zc_jingxuan_vp_item, null);
                view = (ImageView) rootItem.findViewById(R.id.iv_item_picture);
                ImageLoader.getInstance().displayImage(list.get(0), view);
                mViews.add(view);
            }
        }
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        if(null != view.getParent()){
            ((ViewGroup)view.getParent()).removeView(view);
        }
        container.addView(view);
        return view;
    }

    // 实现ViewPager.OnPageChangeListener接口
    @Override
    public void onPageSelected(int position) {
        if ( mViews.size() > 1) {

            //多于1，才会循环跳转
            if ( position < 1) {
                //首位之前，跳转到末尾（N）
                position = mList.size();
                //注意这里是mList，而不是mViews
                mViewPager.setCurrentItem(position, false);
            } else if ( position > mList.size()) {
                //末位之后，跳转到首位（1）
                mViewPager.setCurrentItem(1, false);
                position = 1;
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}