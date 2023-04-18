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
import android.support.v4.widget.Space;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zftlive.android.library.Logger;
import com.zftlive.android.library.base.templet.AbsViewTemplet;
import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.imageloader.CommonImageLoaderListener;
import com.zftlive.android.library.imageloader.ToolImage;
import com.zftlive.android.library.tools.ToolString;
import com.zftlive.android.library.tools.ToolToast;
import com.zftlive.android.library.dynamicpage.Forward;
import com.zftlive.android.library.dynamicpage.IPageConstant;
import com.zftlive.android.library.dynamicpage.R;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 动态页面基础视图模板
 *
 * @author 曾繁添
 * @version 1.0
 */
public abstract class AbsPageViewTemplet extends AbsViewTemplet implements IPageConstant {

    /**
     * 屏幕宽度
     */
    protected int mScreenWidth = 1080;

    /**
     * 1dp对应的px值
     */
    protected float mDensity = 3.0f;

    /**
     * 间隙的LayoutParams
     */
    protected ViewGroup.LayoutParams mSpaceLp;

    /**
     * 图片显示option
     */
    protected DisplayImageOptions mExactlyOption;

    /**
     * 图片下载监听器
     */
    protected ImageLoadingListener mCommonLoadListener;

    public AbsPageViewTemplet(Context mContext) {
        super(mContext);
        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        mDensity = mContext.getResources().getDisplayMetrics().density;
        mSpaceLp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getPxValueOfDp(10.0f));
        mExactlyOption = ToolImage.getInstance().gainExactlyOption(R.drawable.dynamic_common_default_picture);
        mCommonLoadListener = new CommonImageLoaderListener(true);
    }

    @Override
    public void itemClick(View view, int postion, IAdapterModel rowData) {
        super.itemClick(view, postion, rowData);
        Logger.d(TAG, "view-->" + view + " postion=" + postion + " rowData=" + rowData);
        ToolToast.showShort(mContext, "点击我了-->" + " rowData=" + rowData);
//        if (view.getId() == R.id.dynamic_id_floor_title) {
//            //楼层标题点击
//            Forward mForward = (Forward) view.getTag(R.id.dynamic_tag_floor_title);
//            processFloorTitleClickEvent(view, mForward);
//        } else if (view.getId() == R.id.dynamic_id_floor_title) {
//
//        } else {
//
//        }
    }

    /**
     * 转换dp单位
     *
     * @param dpValue
     * @return
     */
    protected int getPxValueOfDp(float dpValue) {
        return (int) (mDensity * dpValue + 0.5f);
    }

    /**
     * 获取楼层/元素组之间的上下间隙View
     *
     * @return
     */
    protected View getSpace() {
        Space mMarginSpace = new Space(mContext);
        mMarginSpace.setLayoutParams(mSpaceLp);
        return mMarginSpace;
    }

    /**
     * 获取楼层/元素组之间的上下间隙View
     *
     * @return
     */
    protected View getFloorDividerSpace() {
        Space mMarginSpace = new Space(mContext);
        mMarginSpace.setBackgroundColor(ToolString.getColor(COLOR_FFFFFF));
        mMarginSpace.setLayoutParams(mSpaceLp);
        return mMarginSpace;
    }

    /**
     * 处理点击事件
     *
     * @param target
     * @param mForward
     */
    protected void processFloorTitleClickEvent(View target, Forward mForward) {

    }

    /**
     * 处理元素的点击事件
     *
     * @param target
     * @param mForward
     */
    protected void processElementClickEvent(View target, Forward mForward) {

    }

    /**
     * 是否第一次加载集合
     */
    protected final static List<String> displayedImages = Collections
            .synchronizedList(new LinkedList<String>());
}
