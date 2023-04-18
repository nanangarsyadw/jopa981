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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zftlive.android.library.Logger;
import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.tools.ToolString;
import com.zftlive.android.library.widget.container.DirectionMarquee;
import com.zftlive.android.library.dynamicpage.R;
import com.zftlive.android.library.dynamicpage.bean.ElementMarqueeBean;
import com.zftlive.android.library.dynamicpage.bean.PageListElementBean;
import com.zftlive.android.library.dynamicpage.bean.PageFloorGroupElement;

import java.util.ArrayList;

/**
 * 跑马灯视图模板
 *
 * @author 曾繁添
 * @version 1.0
 */
public class MarqueeViewTemplet extends AbsPageViewTemplet {

    private DirectionMarquee marqueeLayout;

    public MarqueeViewTemplet(Context mContext) {
        super(mContext);
    }

    @Override
    public int bindLayout() {
        return R.layout.dynamic_element_item_marquee;
    }

    @Override
    public void initView() {
        marqueeLayout = (DirectionMarquee) findViewById(R.id.marquee_root);
    }

    @Override
    public void fillData(IAdapterModel model, int postion) {
        PageListElementBean rowBean = (PageListElementBean) model;
        if (null == rowBean || rowBean.pageFloorGroupElement == null) {
            Logger.e(TAG, postion + "-->数据为空");
            return;
        }

        PageFloorGroupElement data = rowBean.pageFloorGroupElement;

        //初始化数据
        ArrayList<ElementMarqueeBean> marquee = data.marqueeData;
        //设置背景色
        marqueeLayout.setBackgroundColor(ToolString.getColor(data.group.floor.backgroundColor,COLOR_FFFFFF));

        marqueeLayout.setDirection(DirectionMarquee.Direction.BUTTOM_TOP);
        marqueeLayout.setDuration(1000);
        marqueeLayout.setInteval(3000);
        marqueeLayout.removeAllViews();
        for (int i = 0; i < marquee.size(); i++) {
            View itemLayout = LayoutInflater.from(mContext).inflate(R.layout.dynamic_element_item_marquee_item, marqueeLayout, false);
            TextView leftTV = (TextView) itemLayout.findViewById(R.id.tv_left_text);
            TextView rightTV = (TextView) itemLayout.findViewById(R.id.tv_right_text);
            ElementMarqueeBean itemData = marquee.get(i);
            leftTV.setText(itemData.leftText);
            leftTV.setTextColor(ToolString.getColor(itemData.leftTextColor, itemData.COLOR_999999));
            rightTV.setText(itemData.rightText);
            rightTV.setTextColor(ToolString.getColor(itemData.rightTextColor, itemData.COLOR_333333));
            marqueeLayout.addView(itemLayout);
        }
        if (marquee.size() > 1) {
            marqueeLayout.startMarquee();
        }
    }
}
