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
import com.zftlive.android.library.dynamicpage.IPageConstant;

import java.util.ArrayList;

/**
 * 动态页面-[楼层-元素分组]数据模型
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class PageFloorGroup extends BaseBean implements IPageConstant {

    private static final long serialVersionUID = -6749440518415650079L;

    /**
     * 所属楼层
     */
    public PageFloor floor;

    /**
     * 组id
     */
    public String groupId = "0";

    /**
     * 组索引
     */
    public int index = 0;

    /**
     * 元素组类型
     */
    public int groupType = 0;

    /**
     * 是否含有顶部间隙
     */
    public boolean hasTopMargin = false;

    /**
     * 是否含有底部间隙
     */
    public boolean hasButtomMargin = false;

    /**
     * 元素集合
     */
    public ArrayList<PageFloorGroupElement> elementList;
}
