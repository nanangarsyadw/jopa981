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

/**
 * 横向滑动卡片item数据模型
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ElementBannerCardItemBean extends AbsElementBasicBean {

    public ElementBannerCardItemBean(){

    }

    public ElementBannerCardItemBean(PageFloorGroup group,String imageURL) {
        this(group,imageURL,"");
    }

    public ElementBannerCardItemBean(PageFloorGroup group,String imageURL, String text) {
        this(group,imageURL,text, COLOR_FFFFFF);
    }

    public ElementBannerCardItemBean(PageFloorGroup group,String imageURL, String text, String textColor) {
        this(group,imageURL,text,textColor,false);
    }

    public ElementBannerCardItemBean(PageFloorGroup group,String imageURL, String text, boolean isNews) {
        this(group,imageURL,text, COLOR_FFFFFF,isNews);
    }

    public ElementBannerCardItemBean(PageFloorGroup group,String imageURL, String text, String textColor, boolean isNews) {
        this.imageURL = imageURL;
        this.text = text;
        this.textColor = textColor;
        this.isNews = isNews;
        super.group = group;
    }

    /**
     * 图片地址URL
     */
    public String imageURL = "";

    /**
     * 标题文本
     */
    public String text = "";

    /**
     * 标题文本
     */
    public String textColor = COLOR_FFFFFF;

    /**
     * 是否新的入口
     */
    public boolean isNews = false;
}
