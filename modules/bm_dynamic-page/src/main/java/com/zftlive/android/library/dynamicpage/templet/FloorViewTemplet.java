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
import com.zftlive.android.library.base.templet.AbsViewTemplet;
import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.tools.ToolString;
import com.zftlive.android.library.dynamicpage.PageBusinessControl;
import com.zftlive.android.library.dynamicpage.R;
import com.zftlive.android.library.dynamicpage.bean.PageFloor;
import com.zftlive.android.library.dynamicpage.bean.PageFloorGroup;
import com.zftlive.android.library.dynamicpage.bean.PageFloorGroupElement;

import java.util.ArrayList;

/**
 * 楼层视图模板
 *
 * @author 曾繁添
 * @version 1.0
 */
public class FloorViewTemplet extends AbsPageViewTemplet {

    /**
     * 楼层标题容器
     */
    private ViewGroup mFloor, mFloorTitle, mFloorContent;

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
    private View mTitleButtomLine, mFloorTopSpace, mFloorButtomSpace;

    public FloorViewTemplet(Context mContext) {

        super(mContext);
    }

    @Override
    public int bindLayout() {
        return R.layout.dynamic_floor;
    }

    @Override
    public void initView() {
        mFloor = (ViewGroup) findViewById(R.id.ll_floor);
        mFloorTitle = (ViewGroup) findViewById(R.id.rl_floor_title);
        mFloorContent = (ViewGroup) findViewById(R.id.ll_floor_content);

        mMainTitle = (TextView) findViewById(R.id.tv_main_title);
        mMainTitleIcon = (ImageView) findViewById(R.id.iv_main_title_icon);

        mSubTitle = (TextView) findViewById(R.id.tv_sub_title);
        mSubTitleIcon = (ImageView) findViewById(R.id.iv_sub_title_icon);

        mTitleButtomLine = findViewById(R.id.floor_title_buttom_line);
        mFloorTopSpace = findViewById(R.id.floor_top_space);
        mFloorButtomSpace = findViewById(R.id.floor_buttom_space);
    }

    /**
     * 主要处理创建楼层标题、楼层标题点击、楼层内容元素组
     *
     * @param model   楼层数据模型
     * @param postion 楼层索引
     */
    @Override
    public void fillData(IAdapterModel model, int postion) {
        PageFloor floorData = (PageFloor) model;
        if(null == floorData){
            Logger.e(TAG,postion+"-->楼层数据为空");
            return;
        }
        //1、设置楼层背景色，默认白色
        mFloor.setBackgroundColor(ToolString.getColor(floorData.backgroundColor, COLOR_FFFFFF));

        //2、设置楼层标题是否可见
        mFloorTitle.setVisibility(floorData.hasTitle ? View.VISIBLE : View.GONE);
        //设置楼层标题点击跳转
        if (null != floorData.floorTileJumpData) {
            mFloorTitle.setTag(R.id.dynamic_tag_floor_title, floorData.floorTileJumpData);
            mFloorTitle.setOnClickListener(this);
        }
        //设置楼层标题底部分割线
        mTitleButtomLine.setVisibility(floorData.hasTitleButtomLine ? View.VISIBLE : View.GONE);

        //设置主标题图标、文本、颜色
        if (!TextUtils.isEmpty(floorData.titleIcon)) {
            ImageLoader.getInstance().displayImage(floorData.titleIcon, mMainTitleIcon, mExactlyOption);
        }
        mMainTitleIcon.setVisibility(TextUtils.isEmpty(floorData.titleIcon) ? View.GONE : View.VISIBLE);
        mMainTitle.setText(floorData.title);
        mMainTitle.setTextColor(ToolString.getColor(floorData.titleTextColor, COLOR_999999));
        //设置副标题图标、文本、颜色
        if (!TextUtils.isEmpty(floorData.subTitleIcon)) {
            ImageLoader.getInstance().displayImage(floorData.subTitleIcon, mSubTitleIcon, mExactlyOption);
        }
        mSubTitleIcon.setVisibility(TextUtils.isEmpty(floorData.subTitleIcon) ? View.GONE : View.VISIBLE);
        mSubTitle.setText(floorData.subTitle);
        mSubTitle.setTextColor(ToolString.getColor(floorData.subTitleTextColor, COLOR_999999));

        //3、创建楼层元素组
        mFloorContent.removeAllViews();
        ArrayList<PageFloorGroup> mGroupList = floorData.groupList;
        if (null == mGroupList || mGroupList.isEmpty()) {
            Logger.w(TAG, "楼层" + floorData.floorId + "[" + floorData.index + "]元素组集合为空");
            mFloor.setVisibility(View.GONE);
            return;
        }
        makeElementGroup(mFloorContent, mGroupList);

        //4、上下间隙
        mFloorTopSpace.setVisibility(floorData.hasTopMargin ? View.VISIBLE : View.GONE);
        mFloorButtomSpace.setVisibility(floorData.hasButtomMargin ? View.VISIBLE : View.GONE);

        //缓存UI，只执行一次
//        invokeFillDataOnce(true);
    }

    /**
     * 构建元素组
     * @param floorContent 楼层元素组容器
     * @param mGroupList 元素组集合
     */
    private void makeElementGroup(ViewGroup floorContent, ArrayList<PageFloorGroup> mGroupList) {
        if (null == mGroupList || mGroupList.isEmpty()) {
            return;
        }

        for (int i = 0; i < mGroupList.size(); i++) {
            PageFloorGroup mGroupData = mGroupList.get(i);

            //1、元素组顶部间隙
            if(mGroupData.hasTopMargin){
                floorContent.addView(getSpace());
            }
            //2、渲染当前组元素UI
            ArrayList<PageFloorGroupElement> elementList = mGroupData.elementList;
            if (null == elementList || elementList.isEmpty()) {
                Logger.w(TAG, mGroupData.groupId + "-->当前元素组的元素是空");
                return;
            }

            //2.1 绘制元素组里面的元素
            for (int j = 0; j < elementList.size(); j++) {
                if(!PageBusinessControl.mTempletMapper.containsKey(mGroupData.groupType)){
                    Logger.w(TAG, mGroupData.groupType + "-->元素组类型不存在");
                    continue;
                }
                PageFloorGroupElement item = elementList.get(j);
                AbsViewTemplet mTemplet = createViewTemplet(PageBusinessControl.mTempletMapper.get(mGroupData.groupType), mContext);
                mTemplet.inflate(mGroupData.groupType, j, floorContent);
                mTemplet.initView();
                mTemplet.fillData(item, j);
                //省去元素组容器集合，减少UI层级
                floorContent.addView(mTemplet.getItemLayoutView());
            }
            //3、元素组底部间隙
            if(mGroupData.hasButtomMargin){
                floorContent.addView(getSpace());
            }
        }
    }

}
