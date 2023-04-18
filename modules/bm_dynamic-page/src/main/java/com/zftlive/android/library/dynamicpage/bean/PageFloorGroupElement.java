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

import java.util.ArrayList;

/**
 * 动态页面-[楼层-元素分组]数据模型
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class PageFloorGroupElement extends BaseBean {

    private static final long serialVersionUID = -3470324942169410285L;

    public PageFloorGroupElement() {

    }

    public PageFloorGroupElement(PageFloorGroup group) {
        this.group = group;
    }

    /**
     * 所属分组
     */
    public PageFloorGroup group;

    /**
     * 文章资讯item数据
     */
    public ElementArticleBean article;

    /**
     * 自动适应宽高图片item数据
     */
    public ElementImageBannerBean picBanner;

    /**
     * 按钮链接item数据
     */
    public ElementButtonLinkBean buttonLink;

    /**
     * 横向滑动卡片item数据
     */
    public ArrayList<ElementBannerCardItemBean> horScrollDataList;

    /**
     * 横向滑动卡片+带蒙层item数据
     */
    public ElementViewPagerBean vpData;

    /**
     * 跑马灯item数据
     */
    public ArrayList<ElementMarqueeBean> marqueeData;

}
