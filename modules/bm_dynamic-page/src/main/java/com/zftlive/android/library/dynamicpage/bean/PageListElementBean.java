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

import com.zftlive.android.library.base.bean.AdapterModelBean;

/**
 * 动态页面列表itemType一行数据模型
 *
 * @author 曾繁添
 * @version 1.0
 */
public class PageListElementBean extends AdapterModelBean {

    private static final long serialVersionUID = -7091687179008702631L;

    public PageListElementBean() {

    }

    public PageListElementBean(Page page, int itemType, ElementFloorTitleBean floorTitle) {
        this.page = page;
        this.floorTitle = floorTitle;
        super.itemType = itemType;
    }

    public PageListElementBean(Page page, int itemType, ElementCopyrightBean copyright) {
        this.page = page;
        this.copyright = copyright;
        super.itemType = itemType;
    }

    public PageListElementBean(Page page, int itemType, ElementDividerBean floorDivider) {
        this.page = page;
        this.floorDivider = floorDivider;
        super.itemType = itemType;
    }

    public PageListElementBean(Page page, int itemType, PageFloor pageFloor, PageFloorGroup pageFloorGroup, PageFloorGroupElement pageFloorGroupElement) {
        this.page = page;
        this.pageFloor = pageFloor;
        this.pageFloorGroup = pageFloorGroup;
        this.pageFloorGroupElement = pageFloorGroupElement;
        super.itemType = itemType;
    }

    /**
     * 当前页面
     */
    public Page page;

    /**
     * 当前页面楼层
     */
    public PageFloor pageFloor;

    /**
     * 当前页面楼层元素组
     */
    public PageFloorGroup pageFloorGroup;

    /**
     * 当前页面楼层元素组元素
     */
    public PageFloorGroupElement pageFloorGroupElement;

    /**
     * 楼层分隔符
     */
    public ElementDividerBean floorDivider;

    /**
     * 楼层标题
     */
    public ElementFloorTitleBean floorTitle;

    /**
     * 页面底部版权
     */
    public ElementCopyrightBean copyright;

}
