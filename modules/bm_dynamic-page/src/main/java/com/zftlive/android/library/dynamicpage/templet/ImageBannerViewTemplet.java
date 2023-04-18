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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zftlive.android.library.Logger;
import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.imageloader.CommonImageLoaderListener;
import com.zftlive.android.library.imageloader.ToolImage;
import com.zftlive.android.library.tools.ToolString;
import com.zftlive.android.library.dynamicpage.R;
import com.zftlive.android.library.dynamicpage.bean.ElementImageBannerBean;
import com.zftlive.android.library.dynamicpage.bean.PageListElementBean;
import com.zftlive.android.library.dynamicpage.bean.PageFloorGroupElement;

/**
 * 自动适应等比拉伸图片banner组件
 *
 * @author 曾繁添
 * @version 1.0
 */
public class ImageBannerViewTemplet extends AbsPageViewTemplet {

    /**
     * banner图片
     */
    private ImageView mBanner;

    public ImageBannerViewTemplet(Context mContext) {
        super(mContext);
    }

    @Override
    public int bindLayout() {
        return R.layout.dynamic_element_item_img_banner;
    }

    @Override
    public void initView() {
        mBanner = (ImageView) findViewById(R.id.iv_banner);
        //点击事件
        mBanner.setOnClickListener(this);
    }

    @Override
    public void fillData(IAdapterModel model, int postion) {
        PageListElementBean rowBean = (PageListElementBean) model;
        if (null == rowBean || rowBean.pageFloorGroupElement == null) {
            Logger.e(TAG, postion + "-->数据为空");
            return;
        }

        PageFloorGroupElement itemData = rowBean.pageFloorGroupElement;
        //设置背景色
        mBanner.setBackgroundColor(ToolString.getColor(itemData.group.floor.backgroundColor, COLOR_FFFFFF));
        mBanner.setTag(R.id.dynamic_tag_forward, itemData.picBanner.jumpData);

        mBanner.setScaleType(ImageView.ScaleType.FIT_XY);
        if (itemData.picBanner.imgHeightDP > 0) {
            mBanner.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mBanner.getLayoutParams().height = getPxValueOfDp(itemData.picBanner.imgHeightDP);
        }
        //加载图片
        if (!TextUtils.isEmpty(itemData.picBanner.imageURL)) {
            ToolImage.getInstance().displayImage(itemData.picBanner.imageURL, mBanner, mExactlyOption, getImageLoadingListener(itemData));
        }
    }

    @Override
    public void itemClick(View view, int postion, IAdapterModel rowData) {
        super.itemClick(view, postion, rowData);
        ElementImageBannerBean data = (ElementImageBannerBean) rowData;
        if (null != data.jumpData) {
            Logger.d(TAG, "点击-->" + data.imageURL);
        }
    }

    private ImageLoadingListener getImageLoadingListener(PageFloorGroupElement data) {
        //没有设置高度，等比拉伸,反之指定高度
        if (data.picBanner.imgHeightDP <= 0) {
            return new CommonImageLoaderListener(true) {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    if (null != view && null != loadedImage) {
                        ViewGroup.LayoutParams params = view.getLayoutParams();
                        params.width = mScreenWidth;
                        params.height = params.width * loadedImage.getHeight() / loadedImage.getWidth();
                    }
                }
            };
        }
        return null;
    }


}
