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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zftlive.android.library.Logger;
import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.imageloader.ToolImage;
import com.zftlive.android.library.tools.ToolString;
import com.zftlive.android.library.dynamicpage.R;
import com.zftlive.android.library.dynamicpage.bean.PageListElementBean;
import com.zftlive.android.library.dynamicpage.bean.PageFloorGroupElement;
import com.zftlive.android.library.dynamicpage.bean.ElementBannerCardItemBean;
import com.zftlive.android.library.dynamicpage.widget.DynamicHorizontalScrollView;

/**
 * 【横向滑动+图文】上下排列视图模板
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class HorizontalViewTemplet extends AbsPageViewTemplet {

    /**
     * 横向滚动Scrollview
     */
    private DynamicHorizontalScrollView mHScorllView;

    /**
     * 元素容器
     */
    private ViewGroup mContentGroup;

    public HorizontalViewTemplet(Context mContext) {
        super(mContext);
    }

    @Override
    public int bindLayout() {
        return R.layout.dynamic_element_horizontal_scroll;
    }

    @Override
    public void initView() {
        mHScorllView = (DynamicHorizontalScrollView)findViewById(R.id.dynamic_hor_scroll);
        mContentGroup = (ViewGroup)findViewById(R.id.ll_hor_scroll_container);
    }

    @Override
    public void fillData(IAdapterModel model, int postion) {
        PageListElementBean rowBean = (PageListElementBean) model;
        if (null == rowBean || rowBean.pageFloorGroupElement == null) {
            Logger.e(TAG, postion + "-->数据为空");
            return;
        }

        PageFloorGroupElement data = rowBean.pageFloorGroupElement;
        //设置背景色
        mHScorllView.setBackgroundColor(ToolString.getColor(data.group.floor.backgroundColor,COLOR_FFFFFF));

        mContentGroup.removeAllViews();
        int count = data.horScrollDataList.size();
        for (int i = 0; i < count; i++) {
            ElementBannerCardItemBean itemData = data.horScrollDataList.get(i);
            //渲染UI
            ViewGroup mItemLayout = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.dynamic_element_item_text_img,mContentGroup,false);
            ImageView mProductImg = (ImageView) mItemLayout.findViewById(R.id.iv_product_img);
            TextView mText = (TextView) mItemLayout.findViewById(R.id.tv_title_text);
            //装填数据
            if(!TextUtils.isEmpty(itemData.imageURL)){
                ToolImage.getInstance().displayImage(itemData.imageURL,mProductImg,mExactlyOption, mCommonLoadListener);
            }
            mText.setText(itemData.text);
            mText.setTextColor(ToolString.getColor(itemData.textColor,itemData.COLOR_666666));
            //间隙
            if(i != 0){
                LinearLayout.LayoutParams mItemLp = (LinearLayout.LayoutParams)mItemLayout.getLayoutParams();
                mItemLp.leftMargin = getPxValueOfDp(5);
            }
            //点击事件
            mItemLayout.setOnClickListener(this);
            mItemLayout.setTag(R.id.dynamic_tag_forward,itemData.jumpData);

            mContentGroup.addView(mItemLayout);
        }
    }
}
