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
import android.view.ViewGroup;
import android.widget.TextView;

import com.zftlive.android.library.Logger;
import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.tools.ToolString;
import com.zftlive.android.library.dynamicpage.R;
import com.zftlive.android.library.dynamicpage.bean.ElementCopyrightBean;
import com.zftlive.android.library.dynamicpage.bean.PageListElementBean;

/**
 * 版权-Copyright视图模板
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class CopyrightViewTemplet extends AbsPageViewTemplet {

    /**
     * 楼层割线
     */
    private TextView mCopyText;

    private ViewGroup mCopyrightGroup;

    public CopyrightViewTemplet(Context mContext) {

        super(mContext);
    }

    @Override
    public int bindLayout() {
        return R.layout.dynamic_floor_copyright;
    }

    @Override
    public void initView() {
        mCopyrightGroup = (ViewGroup) findViewById(R.id.ll_copyright);
        mCopyText = (TextView) findViewById(R.id.tv_copyright);
    }

    /**
     * 主要处理创建楼层标题、楼层标题点击、楼层内容元素组
     * @param model 楼层数据模型
     * @param postion 楼层索引
     */
    @Override
    public void fillData(IAdapterModel model, int postion) {
        PageListElementBean rowBean = (PageListElementBean) model;
        if (null == rowBean || rowBean.copyright == null) {
            Logger.e(TAG, postion + "-->数据为空");
            return;
        }
        ElementCopyrightBean itemData = rowBean.copyright;
        //1、设置背景色
        mCopyrightGroup.setBackgroundColor(ToolString.getColor(itemData.backgroudColor,itemData.COLOR_EFEFF4));
        //2、设置高度
        if(itemData.heightDP > 0){
            ViewGroup.LayoutParams mViewLP = mCopyrightGroup.getLayoutParams();
            if(null == mViewLP){
                mViewLP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mViewLP.height = getPxValueOfDp(itemData.heightDP);
                mCopyrightGroup.setLayoutParams(mViewLP);
            }
        }

    }
}
