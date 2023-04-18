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

package com.zftlive.android.sample.tabs.viewpager.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zftlive.android.R;
import com.zftlive.android.library.base.ui.BaseFragmentV4;

/**
 * [搜索]tab界面
 * 
 * @author 曾繁添
 * @version 1.0
 *
 */
public class TabSearchFragment extends BaseFragmentV4 {

  TextView mText;
  
  @Override
  public int bindLayout() {
    return R.layout.fragment_tabs_fav;
  }

  @Override
  public void initParams(Bundle params) {

  }

  @Override
  public void initView(View view) {
    mText = (TextView) findViewById(R.id.text);
  }

  @Override
  public void loadDataOnce() {
    super.loadDataOnce();
//    mText.setText("搜索tab界面");
  }

}
