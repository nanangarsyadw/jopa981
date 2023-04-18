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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zftlive.android.library.Logger;
import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.tools.ToolString;
import com.zftlive.android.library.dynamicpage.R;
import com.zftlive.android.library.dynamicpage.bean.ElementFloorTitleBean;
import com.zftlive.android.library.dynamicpage.bean.PageListElementBean;

/**
 * 楼层视图模板
 *
 * @author 曾繁添
 * @version 1.0
 */
public class FloorTitleViewTemplet extends AbsPageViewTemplet {

    /**
     * 楼层标题容器
     */
    private ViewGroup mFloorTitle;

    /**
     * 楼层标题图标
     */
    private ImageView mMainTitleIcon, mSubTitleIcon;

    /**
     * 楼层主副标题
     */
    private TextView mMainTitle, mSubTitle;

    /**
     * 楼层标题底部分割线
     */
    private View mTitleButtomLine;

    public FloorTitleViewTemplet(Context mContext) {

        super(mContext);
    }

    @Override
    public int bindLayout() {

        return R.layout.dynamic_floor_title;
    }

    @Override
    public void initView() {
        mFloorTitle = (ViewGroup) findViewById(R.id.rl_floor_title);
        mMainTitle = (TextView) findViewById(R.id.tv_main_title);
        mMainTitleIcon = (ImageView) findViewById(R.id.iv_main_title_icon);
        mSubTitle = (TextView) findViewById(R.id.tv_sub_title);
        mSubTitleIcon = (ImageView) findViewById(R.id.iv_sub_title_icon);
        mTitleButtomLine = findViewById(R.id.floor_title_buttom_line);
    }

    /**
     * 主要处理创建楼层标题、楼层标题点击、楼层内容元素组
     *
     * @param model   楼层数据模型
     * @param postion 楼层索引
     */
    @Override
    public void fillData(IAdapterModel model, int postion) {
        PageListElementBean rowBean = (PageListElementBean) model;
        if (null == rowBean || rowBean.floorTitle == null) {
            Logger.e(TAG, postion + "-->数据为空");
            return;
        }
        ElementFloorTitleBean floorTitle = rowBean.floorTitle;

        //1、设置楼层背景色，默认白色
        mFloorTitle.setBackgroundColor(ToolString.getColor(floorTitle.backgroundColor, COLOR_FFFFFF));
        //2、设置楼层标题是否可见
        mFloorTitle.setVisibility(floorTitle.hasTitle ? View.VISIBLE : View.GONE);
        //设置楼层标题点击跳转
        if (null != floorTitle.floorTileJumpData) {
            mFloorTitle.setTag(R.id.dynamic_tag_floor_title, floorTitle.floorTileJumpData);
            mFloorTitle.setOnClickListener(this);
        }
        //设置楼层标题底部分割线
        mTitleButtomLine.setVisibility(floorTitle.hasTitleButtomLine ? View.VISIBLE : View.GONE);

        //设置主标题图标、文本、颜色
        if (!TextUtils.isEmpty(floorTitle.titleIcon)) {
            ImageLoader.getInstance().displayImage(floorTitle.titleIcon, mMainTitleIcon, mExactlyOption);
        }
        mMainTitleIcon.setVisibility(TextUtils.isEmpty(floorTitle.titleIcon) ? View.GONE : View.VISIBLE);
        mMainTitle.setText(floorTitle.title);
        mMainTitle.setTextColor(ToolString.getColor(floorTitle.titleTextColor, COLOR_999999));
        //设置副标题图标、文本、颜色
        if (!TextUtils.isEmpty(floorTitle.subTitleIcon)) {
            ImageLoader.getInstance().displayImage(floorTitle.subTitleIcon, mSubTitleIcon, mExactlyOption);
        }
        mSubTitleIcon.setVisibility(TextUtils.isEmpty(floorTitle.subTitleIcon) ? View.GONE : View.VISIBLE);
        mSubTitle.setText(floorTitle.subTitle);
        mSubTitle.setTextColor(ToolString.getColor(floorTitle.subTitleTextColor, COLOR_999999));
    }
}
