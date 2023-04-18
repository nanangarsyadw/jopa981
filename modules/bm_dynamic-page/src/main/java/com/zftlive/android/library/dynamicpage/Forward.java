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

import com.zftlive.android.library.base.bean.BaseBean;

/**
 * 跳转数据模型
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class Forward extends BaseBean {

    public Forward(){

    }

    public Forward(int jumpType, String jumpURL) {
        this(jumpType,jumpURL,"");
    }

    public Forward(int jumpType, String jumpURL, String paramJson) {
        this.jumpType = jumpType;
        this.jumpURL = jumpURL;
        this.paramJson = paramJson;
    }

    /**
     * 跳转类型
     */
    public int jumpType = 0;

    /**
     * 跳转URL
     */
    public String jumpURL = "";

    /**
     * 跳转额外参数json
     */
    public String paramJson = "";
}
