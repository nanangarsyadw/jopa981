/*
 *
 *     Adapter多类型视图模板分离，模板化的经典案例，每个模板进行高度封装并且支持灵活扩展,减轻后期维护成本
 *
 *     来着京东金融团队Android基础开发库积累、沉淀、封装、共同整理分享
 *
 *     Copyright (c) 2017. @ 京东金融团队
 *
 *     技术支持：曾繁添<zengfantian@jd.com>
 * /
 */

package com.zftlive.android.library.base.templet;

import com.zftlive.android.library.base.IBaseConstant;

/**
 *
 * 视图模板基础业务常量
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public interface ITempletConstant extends IBaseConstant.IColor  {
    
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
     * 指示器默认颜色
     */
    String UN_SELECTED_DOT_COLOR = "#E8E8E8";

    /**
     * 指示器选中颜色
     */
    String SELECTED_DOT_COLOR = COLOR_666666;
    
}
