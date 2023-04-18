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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zftlive.android.library.base.adapter.BaseMAdapter;
import com.zftlive.android.library.imageloader.ToolImage;
import com.zftlive.android.library.tools.ToolString;
import com.zftlive.android.library.dynamicpage.R;
import com.zftlive.android.library.dynamicpage.bean.ElementBannerCardItemBean;
import com.zftlive.android.library.dynamicpage.bean.ElementViewPagerBean;

import java.util.ArrayList;

/**
 * 带分页Grid类型的ViewPager视图模板
 *
 * @author 曾繁添
 * @version 1.0
 */
public class ViewPagerGridIconViewTemplet extends ViewPagerViewTemplet {

    public ViewPagerGridIconViewTemplet(Context mContext) {
        super(mContext);
    }

    @Override
    public void initView() {
        super.initView();
        mViewPager.getLayoutParams().height = getPxValueOfDp(165);
    }

    @Override
    protected int gainPageItemCount(ElementViewPagerBean vpData) {
        if (null == vpData.pageItemList || vpData.pageItemList.isEmpty()) {
            return 0;
        }
        return vpData.pageItemList.size();
    }

    /**
     * 创建item
     *
     * @param vpData ViewPager数据
     * @param index  索引
     */
    protected View makeViewPageItem(ElementViewPagerBean vpData, int index) {
        //渲染UI
        ArrayList<ElementBannerCardItemBean> currentPage = vpData.pageItemList.get(index);
        GridView mGridViewItem = (GridView) LayoutInflater.from(mContext).inflate(R.layout.dynamic_element_item_grid, parent,false);
        //网格适配器
        ExpansionGridViewAdapter mGridAdapter = new ExpansionGridViewAdapter(mContext);
        mGridAdapter.addItem(currentPage);
        mGridViewItem.setFocusable(false);
        mGridViewItem.setAdapter(mGridAdapter);
        return mGridViewItem;
    }

    /**
     * 网格item适配器
     */
    private class ExpansionGridViewAdapter extends BaseMAdapter {

        public ExpansionGridViewAdapter(Context mContext) {
            super(mContext);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder mHolder = null;
            if (null == convertView) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.dynamic_element_item_grid_item, parent, false);
                mHolder = new Holder();
                mHolder.mItemText = (TextView) convertView.findViewById(R.id.tv_item_text);
                mHolder.mItemIcon = (ImageView) convertView.findViewById(R.id.iv_item_icon);
                mHolder.mNewsDot = (ImageView) convertView.findViewById(R.id.iv_news_dot);
                convertView.setTag(mHolder);
            } else {
                mHolder = (Holder) convertView.getTag();
            }

            //设置数据
            ElementBannerCardItemBean item = (ElementBannerCardItemBean) getItem(position);
            if (!TextUtils.isEmpty(item.imageURL)) {
                ToolImage.getInstance().displayImage(mContext, item.imageURL, mHolder.mItemIcon, mExactlyOption, mCommonLoadListener);
            }
            mHolder.mItemText.setText(item.text);
            mHolder.mItemText.setTextColor(ToolString.getColor(item.textColor, COLOR_666666));
            mHolder.mNewsDot.setVisibility(item.isNews ? View.VISIBLE : View.GONE);
            return convertView;
        }

        class Holder {
            ImageView mItemIcon, mNewsDot;
            TextView mItemText;
        }
    }
}