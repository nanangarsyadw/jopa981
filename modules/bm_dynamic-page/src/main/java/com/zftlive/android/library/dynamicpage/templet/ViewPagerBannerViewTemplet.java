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
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zftlive.android.library.imageloader.CommonImageLoaderListener;
import com.zftlive.android.library.imageloader.ToolImage;
import com.zftlive.android.library.tools.ToolString;
import com.zftlive.android.library.dynamicpage.R;
import com.zftlive.android.library.dynamicpage.bean.ElementBannerCardItemBean;
import com.zftlive.android.library.dynamicpage.bean.ElementViewPagerBean;

/**
 * 【横向滑动+蒙层】水平叠加图片Banner类型的ViewPager视图模板
 *
 * @author 曾繁添
 * @version 1.0
 */
public class ViewPagerBannerViewTemplet extends ViewPagerViewTemplet {

    public ViewPagerBannerViewTemplet(Context mContext) {
        super(mContext);
    }

    @Override
    public void initView() {
        super.initView();
    }

    /**
     * 初始化页面数量
     *
     * @param vpData
     * @return
     */
    protected int gainPageItemCount(ElementViewPagerBean vpData) {
        if (null == vpData.itemList || vpData.itemList.isEmpty()) {
            return 0;
        }
        return vpData.itemList.size();
    }

    /**
     * 创建item
     *
     * @param vpData ViewPager数据
     * @param index  索引
     */
    protected View makeViewPageItem(ElementViewPagerBean vpData, int index) {
        if (null == vpData.itemList || vpData.itemList.isEmpty()) {
            return null;
        }
        //渲染UI
        ElementBannerCardItemBean item = vpData.itemList.get(index);
        ViewGroup mItemLayout = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.dynamic_element_item_text_img_mask, parent, false);
        ImageView mProductImg = (ImageView) mItemLayout.findViewById(R.id.iv_product_img);
        TextView mText = (TextView) mItemLayout.findViewById(R.id.tv_title_text);
        View mMask = mItemLayout.findViewById(R.id.view_press_mask);
        //装填数据
        if (!TextUtils.isEmpty(item.imageURL)) {
            ToolImage.getInstance().displayImage(item.imageURL, mProductImg, mExactlyOption,getImageLoadingListener(vpData));
        }
        mText.setText(item.text);
        mText.setTextColor(ToolString.getColor(item.textColor, ElementBannerCardItemBean.COLOR_FFFFFF));
        mMask.setVisibility(vpData.hasMask ? View.VISIBLE : View.GONE);
        //点击事件
        mItemLayout.setOnClickListener(this);

        return mItemLayout;
    }

    /**
     * 缩放图片
     * @param data
     * @return
     */
    private ImageLoadingListener getImageLoadingListener(ElementViewPagerBean data) {
        //没有设置高度，等比拉伸,反之指定高度
        if (data.itemHeightDP <= 0) {
            return new CommonImageLoaderListener(true){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    if(null != view && null != loadedImage){
                        ViewGroup.LayoutParams params = view.getLayoutParams();
                        params.width = mScreenWidth;
                        params.height = params.width * loadedImage.getHeight() / loadedImage.getWidth();
                        mViewPager.getLayoutParams().height = params.height;
                    }
                }
            };
        }
        return null;
    }
}