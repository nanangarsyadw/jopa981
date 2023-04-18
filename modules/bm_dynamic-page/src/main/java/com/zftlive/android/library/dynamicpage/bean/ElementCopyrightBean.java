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
 * 版权信息数据模型
 *
 * @author 曾繁添
 * @version 1.0
 */
public class ElementCopyrightBean extends AbsElementBasicBean {

    public ElementCopyrightBean() {
    }

    public ElementCopyrightBean(String copyright) {
        this(copyright, 60);
    }

    public ElementCopyrightBean(String copyright, int heightDP) {
        this(copyright, COLOR_EFEFF4, heightDP);
    }

    public ElementCopyrightBean(String copyright, String backgroudColor, int heightDP) {
        this.copyright = copyright;
        this.backgroudColor = backgroudColor;
        this.heightDP = heightDP;
    }

    /**
     * 背景色
     */
    public String copyright = "- Ajava 攻 城 师 -";

    /**
     * 背景色
     */
    public String backgroudColor = COLOR_EFEFF4;

    /**
     * 高度-单位dp
     */
    public int heightDP = 60;

}
