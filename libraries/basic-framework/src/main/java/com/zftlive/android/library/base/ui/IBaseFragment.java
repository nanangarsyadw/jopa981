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
 */

package com.zftlive.android.library.base.ui;

import android.os.Bundle;
import android.view.View;

import com.zftlive.android.library.base.IBaseConstant;

/**
 * Fragment接口
 *
 * @author 曾繁添
 * @version 1.0
 */
 public interface IBaseFragment extends IBaseConstant {

    /**
     * 绑定渲染视图的布局文件
     *
     * @return 布局文件资源id
     */
     int bindLayout();

    /**
     * 绑定渲染View
     *
     * @return
     */
     View bindView();

    /**
     * 初始化界面参数
     *
     * @param params
     */
     void initParams(Bundle params);

    /**
     * 初始化控件
     */
     void initView(final View view);

    /**
     * 当Fragment在屏幕可见时加载数据
     */
     void loadDataOnce();

    /**
     * 初始化Activity标题栏的标题文案
     *
     * @return 标题字符串
     */
     String initTitle();

}
