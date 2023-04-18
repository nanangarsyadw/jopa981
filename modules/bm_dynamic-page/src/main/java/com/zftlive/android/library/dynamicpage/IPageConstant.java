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

package com.zftlive.android.library.dynamicpage;

/**
 * 动态页面业务常量
 *
 * @author 曾繁添
 * @version 1.0
 */
public interface IPageConstant {

    /**
     * 默认分隔符高度
     */
    int DIVIDER_SPACE_HEIGHT_DP = 10;

    /**
     * 页面入参-pageId(用于请求接口)
     */
    String PARAM_PAGE_ID = "pageId";

    /**
     * 页面入参-整个页面数据
     */
    String PARAM_PAGE_MODEL = "pageModel";

    /**
     * 方向-左
     */
    int DIRECTION_LEFT = -1;

    /**
     * 方向-中间
     */
    int DIRECTION_CENTER = 0;

    /**
     * 方向-右
     */
    int DIRECTION_RIGHT = 1;

    /**
     * 白色-#FFFFFF
     */
    String COLOR_FFFFFF = "#FFFFFF";

    /**
     * 黑色-#efeff4
     */
    String COLOR_EFEFF4 = "#efeff4";

    /**
     * 透明色
     */
    String COLOR_TRANSPARENT = "#00000000";

    /**
     * 黑色-#f9f9f9
     */
    String COLOR_F9F9F9 = "#f9f9f9";

    /**
     * 黑色-#33333
     */
    String COLOR_333333 = "#333333";

    /**
     * 黑色-#666666
     */
    String COLOR_666666 = "#666666";

    /**
     * 黑色-#999999
     */
    String COLOR_999999 = "#999999";

    /**
     * 指示器默认颜色
     */
    String UN_SELECTED_DOT_COLOR = "#19000000";

    /**
     * 指示器选中颜色
     */
    String SELECTED_DOT_COLOR = "#4c000000";


//    /**
//     * listview的item类型-间隙
//     */
//    int ITEM_TYPE_FLOOR_DIVIDER = 0;

//
//    /**
//     * listview的item类型-楼层标题
//     */
//    int ITEM_TYPE_FLOOR_TITLE = 2;
//
//    /**
//     * listview的item类型-版权
//     */
//    int ITEM_TYPE_FLOOR_COPYTIGHT = 99;

    /**
     * listview的item类型-楼层
     */
    int ITEM_TYPE_FLOOR = 1;

    /**
     * 文章资讯模板
     */
    int ITEM_TYPE_TEMPLET_ARTICLE = 3;

    /**
     * 图片Banner模板
     */
    int ITEM_TYPE_TEMPLET_IMG_BANNER = 4;

    /**
     * 按钮链接模板
     */
    int ITEM_TYPE_TEMPLET_BUTTON_LINK = 5;

    /**
     * ViewPager广告Banner滑动模板
     */
    int ITEM_TYPE_TEMPLET_VP_BANNER = 6;

    /**
     * 横向滑动图文模板
     */
    int ITEM_TYPE_TEMPLET_HOR_IMG_TEXT = 7;

    /**
     * 跑马灯模板
     */
    int ITEM_TYPE_TEMPLET_MARQUEE = 8;

    /**
     * 横向滑动网格入口模板
     */
    int ITEM_TYPE_TEMPLET_VP_PAGE_GRID = 9;
}
