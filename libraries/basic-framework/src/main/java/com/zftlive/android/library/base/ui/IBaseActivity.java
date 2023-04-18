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

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.zftlive.android.library.base.IBaseConstant;

/**
 * Activity接口
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
 public interface IBaseActivity extends IBaseConstant {

  /**
   * 在setContentView之前的一些window配置
   * 
   * @param savedInstanceState
   */
   void config(Bundle savedInstanceState);

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
   * @param parms
   */
   void initParams(Bundle parms);

  /**
   * 初始化控件
   */
   void initView(final View view);

  /**
   * 业务处理操作（onCreate方法中调用）
   * 
   * @param mContext 当前Activity对象
   */
   void doBusiness(Context mContext);

}
