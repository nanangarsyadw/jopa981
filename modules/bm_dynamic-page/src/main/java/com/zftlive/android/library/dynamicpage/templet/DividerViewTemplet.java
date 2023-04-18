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
import android.view.View;
import android.view.ViewGroup;

import com.zftlive.android.library.Logger;
import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.tools.ToolString;
import com.zftlive.android.library.tools.ToolUnit;
import com.zftlive.android.library.dynamicpage.R;
import com.zftlive.android.library.dynamicpage.bean.ElementDividerBean;
import com.zftlive.android.library.dynamicpage.bean.PageListElementBean;

/**
 * 楼层间隙视图模板
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class DividerViewTemplet extends AbsPageViewTemplet {

    /**
     * 楼层割线
     */
    private View mSplit;

    public DividerViewTemplet(Context mContext) {

        super(mContext);
    }

    @Override
    public int bindLayout() {
        return R.layout.dynamic_floor_divider;
    }

    @Override
    public void initView() {
        mSplit = findViewById(R.id.view_floor_split);
    }

    /**
     * 主要处理创建楼层标题、楼层标题点击、楼层内容元素组
     * @param model 楼层数据模型
     * @param postion 楼层索引
     */
    @Override
    public void fillData(IAdapterModel model, int postion) {
        PageListElementBean rowBean = (PageListElementBean) model;
        if (null == rowBean || rowBean.floorDivider == null) {
            Logger.e(TAG, postion + "-->数据为空");
            return;
        }

        ElementDividerBean divider = rowBean.floorDivider;

        //1、设置背景色
        mSplit.setBackgroundColor(ToolString.getColor(divider.backgroudColor,divider.COLOR_F9F9F9));
        //2、设置高度
        if(divider.heightDP > 0){
            ViewGroup.LayoutParams mViewLP = mSplit.getLayoutParams();
            if(null == mViewLP){
                mViewLP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mViewLP.height = ToolUnit.dipToPx(mContext,divider.heightDP);
                mSplit.setLayoutParams(mViewLP);
            }
        }
    }
}
