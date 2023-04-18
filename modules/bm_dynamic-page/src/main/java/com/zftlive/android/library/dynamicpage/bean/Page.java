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
 * 动态页面-[整个页面]数据模型
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class Page extends BaseBean implements IPageConstant {

    private static final long serialVersionUID = -2622527746283594324L;

    /**
     * 页面id
     */
    public String pageId = "1";

    /**
     * 页面标题
     */
    public String title = "";

    /**
     * 楼层元素集合
     */
    public ArrayList<PageFloor> floorList;

    /**
     * 是否需要页面第一个位置的分隔符
     */
    public boolean hasFirstDivider = false;

    /**
     * 是否需要页面最后一个位置的分隔符
     */
    public boolean hasLastDivider = false;

    /**
     * 是否需要版权footer
     */
    public boolean hasCopyright = false;

}
