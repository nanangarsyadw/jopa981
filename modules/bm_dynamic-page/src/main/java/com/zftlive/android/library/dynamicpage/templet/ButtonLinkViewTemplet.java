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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zftlive.android.library.Logger;
import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.imageloader.ToolImage;
import com.zftlive.android.library.tools.ToolString;
import com.zftlive.android.library.dynamicpage.R;
import com.zftlive.android.library.dynamicpage.bean.ElementButtonLinkBean;
import com.zftlive.android.library.dynamicpage.bean.PageListElementBean;
import com.zftlive.android.library.dynamicpage.bean.PageFloorGroupElement;

/**
 * 按钮基础视图模板
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ButtonLinkViewTemplet extends AbsPageViewTemplet {

    /**
     * 图标
     */
    private ImageView mIcon;

    /**
     * 按钮文案
     */
    private TextView mButtonText;


    public ButtonLinkViewTemplet(Context mContext) {
        super(mContext);
    }

    @Override
    public int bindLayout() {
        return R.layout.dynamic_element_item_button_link;
    }

    @Override
    public void initView() {
        mIcon = (ImageView)findViewById(R.id.iv_button_icon);
        mButtonText = (TextView) findViewById(R.id.tv_button_text);
    }

    @Override
    public void fillData(IAdapterModel model, int postion) {
        PageListElementBean rowBean = (PageListElementBean) model;
        if (null == rowBean || rowBean.pageFloorGroupElement == null || rowBean.pageFloorGroupElement.buttonLink == null) {
            Logger.e(TAG, postion + "-->数据为空");
            return;
        }
        ElementButtonLinkBean itemData = rowBean.pageFloorGroupElement.buttonLink;
        //设置背景色
//        mLayoutView.setBackgroundColor(ToolString.getColor(itemData.group.floor.backgroundColor,COLOR_FFFFFF));

        if(!TextUtils.isEmpty(itemData.iconURL)){
            ToolImage.getInstance().displayImage(itemData.iconURL,mIcon,mExactlyOption);
        }
        mIcon.setVisibility(TextUtils.isEmpty(itemData.iconURL)?View.GONE:View.VISIBLE);
        mButtonText.setText(itemData.buttonText);
        mButtonText.setTextColor(ToolString.getColor(itemData.buttonTextColor,itemData.COLOR_999999));
    }

    @Override
    public void itemClick(View view, int postion, IAdapterModel rowData) {
        super.itemClick(view, postion, rowData);
        ElementButtonLinkBean data = ((PageFloorGroupElement)rowData).buttonLink;
        if(null != data.jumpData){
            Logger.d(TAG,"点击-->"+data.buttonText);
        }
    }
}
