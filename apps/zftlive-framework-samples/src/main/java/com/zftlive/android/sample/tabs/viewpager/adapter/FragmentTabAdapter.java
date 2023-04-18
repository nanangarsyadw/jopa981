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

package com.zftlive.android.sample.tabs.viewpager.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentManager;

import com.zftlive.android.library.common.adapter.BasicFragmentPagerAdapter;

/**
 * 用于固定数量tab的标签页情况
 * 
 * @author 曾繁添
 * @version 1.0
 *
 */
public class FragmentTabAdapter extends BasicFragmentPagerAdapter {

  public FragmentTabAdapter(FragmentManager fm, Activity mActivity) {
    super(fm, mActivity);
  }

}
