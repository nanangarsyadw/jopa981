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

import java.util.ArrayList;

/**
 * 横向滑动ViewPager数据模型
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ElementViewPagerBean extends AbsElementBasicBean {

    /**
     * VP高度
     */
    public int itemHeightDP = 0;

    /**
     * 是否有蒙层，默认无
     */
    public boolean hasMask = false;

    /**
     * 指示器方向：-1左边 0中间 1右边
     */
    public int dotDirection = DIRECTION_CENTER;

    /**
     * 选中索引
     */
    public int selectedIndex = 0;

    /**
     * 指示器默认颜色
     */
    public String unSelectedDotColor = UN_SELECTED_DOT_COLOR;

    /**
     * 指示器选中颜色
     */
    public String selectedDotColor = SELECTED_DOT_COLOR;

    /**
     * 卡片数据集合
     */
    public ArrayList<ElementBannerCardItemBean> itemList;

    /**
     * 分页的item数据集合
     */
    public ArrayList<ArrayList<ElementBannerCardItemBean>> pageItemList;
}
