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

package com.zftlive.android.sample.gesture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zftlive.android.R;
import com.zftlive.android.library.base.ui.CommonActivity;

/**
 * 九宫格解锁示例
 * 
 * @author 曾繁添
 * @version 1.0
 *
 */
public class Lock9DemoActivity extends CommonActivity {

  @Override
  public int bindLayout() {
    return R.layout.activity_lock_main;
  }

  @Override
  public void initParams(Bundle parms) {

  }

  @Override
  public void initView(View view) {

  }

  @Override
  public void doBusiness(Context mContext) {
    //初始化带返回按钮的标题栏
    String strCenterTitle = getResources().getString(R.string.Lock9DemoActivity);
    mWindowTitle.initBackTitleBar(strCenterTitle);
  }

  public void onBtnNormalClick(View v) {
    startActivity(new Intent(this, NormalActivity.class));
  }

  public void onBtnLStyleClick(View v) {
    startActivity(new Intent(this, LStyleActivity.class));
  }

}
