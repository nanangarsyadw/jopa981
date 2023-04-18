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

package com.zftlive.android.library.dynamicpage.bean;

import com.zftlive.android.library.base.bean.BaseBean;
import com.zftlive.android.library.dynamicpage.Forward;
import com.zftlive.android.library.dynamicpage.IPageConstant;

import java.util.ArrayList;

/**
 * 动态页面-[楼层]数据模型
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class PageFloor extends BaseBean implements IPageConstant{

    private static final long serialVersionUID = 1331228628030419484L;

    /**
     * 所属页面数据
     */
    public Page page;

    /**
     * 楼层id
     */
    public String floorId = "";

    /**
     * 楼层索引
     */
    public int index = 0;

    /**
     * 楼层标签
     */
    public String tag = "";

    /**
     * 楼层标题
     */
    public String title;

    /**
     * 楼层标题图标
     */
    public String titleIcon;

    /**
     * 楼层标题字体颜色，默认#333333
     */
    public String titleTextColor = COLOR_333333;

    /**
     * 楼层副标题
     */
    public String subTitle;

    /**
     * 楼层副标题图标
     */
    public String subTitleIcon;

    /**
     * 楼层标题字体颜色，默认#999999
     */
    public String subTitleTextColor = COLOR_999999;

    /**
     * 楼层背景色，默认白色
     */
    public String backgroundColor = COLOR_FFFFFF;

    /**
     * 是否有标题
     */
    public boolean hasTitle = true;

    /**
     * 楼层标题跳转数据
     */
    public Forward floorTileJumpData;

    /**
     * 是否含有顶部间隙
     */
    public boolean hasTopMargin = false;

    /**
     * 是否含有底部间隙
     */
    public boolean hasButtomMargin = false;

    /**
     * 是否显示底部分割线
     */
    public boolean hasTitleButtomLine = true;

    /**
     * 元素分组集合
     */
    public ArrayList<PageFloorGroup> groupList;

}
