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

package com.zftlive.android.library.common;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zftlive.android.library.R;

/**
 * ActionBar管理器
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class ActionBarManager {

  /**
   * 设置居中标题
   * 
   * @param mContext 上下文
   * @param actionBar actionbar
   * @param strCenterTitle 中间居中显示标题
   */
  public static void initTitleCenterActionBar(Context mContext, ActionBar actionBar,
      String strCenterTitle) {
    if (null == actionBar) return;
    LayoutInflater inflator =
        (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View centerTitle = inflator.inflate(R.layout.anl_common_actionbar_title, null);
    TextView title = (TextView) centerTitle.findViewById(R.id.actionbar_title);
    title.setText(strCenterTitle);
    ActionBar.LayoutParams layoutParams =
        new ActionBar.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

    actionBar.setDisplayShowCustomEnabled(true);
    actionBar.setCustomView(centerTitle, layoutParams);
  }

  /**
   * 更新ActionBar中间标题
   * 
   * @param mContext 上下文
   * @param actionBar actionbar
   * @param strCenterTitle 中间居中显示标题
   */
  public static void updateActionCenterTitle(Context mContext, ActionBar actionBar,
      String strCenterTitle) {
    if (null == actionBar) return;
    ((TextView) actionBar.getCustomView().findViewById(R.id.actionbar_title))
        .setText(strCenterTitle);
  }

  /**
   * 订制一个返回+标题的标题栏
   * 
   * @param mContext
   * @param actionBar
   * @param strCenterTitle
   */
  public static void initMenuListTitle(Context mContext, ActionBar actionBar, String strCenterTitle) {
    if (null == actionBar) return;
    actionBar.setDisplayHomeAsUpEnabled(false);
    actionBar.setDisplayShowHomeEnabled(true);
    actionBar.setDisplayShowTitleEnabled(false);
    actionBar.setHomeButtonEnabled(false);
    actionBar.setLogo(R.drawable.anl_common_nav_menu_white_n);
    actionBar.setDisplayUseLogoEnabled(true);
    initTitleCenterActionBar(mContext, actionBar, strCenterTitle);
  }

  /**
   * 订制一个返回+标题的标题栏
   * 
   * @param mContext
   * @param actionBar
   * @param strCenterTitle
   */
  public static void initBackTitle(Context mContext, ActionBar actionBar, String strCenterTitle) {
    if (null == actionBar) return;
    actionBar.setTitle("返回");
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setDisplayShowHomeEnabled(false);
    actionBar.setDisplayShowTitleEnabled(true);
    initTitleCenterActionBar(mContext, actionBar, strCenterTitle);
  }

  /**
   * 初始化【提交】右侧按钮菜单
   * 
   * @param mOptionsMenu
   */
  public static void initActionBarSubmitButton(Menu mOptionsMenu) {
    final MenuItem aboutItem = mOptionsMenu.findItem(R.id.action_about);
    if (null != aboutItem) {
      aboutItem.setVisible(false);
    }

    final MenuItem searchItem = mOptionsMenu.findItem(R.id.action_search);
    if (null != searchItem) {
      searchItem.setVisible(false);
    }

    final MenuItem favItem = mOptionsMenu.findItem(R.id.action_fav);
    if (null != favItem) {
      favItem.setVisible(false);
    }

    final MenuItem settingItem = mOptionsMenu.findItem(R.id.action_settings);
    if (null != settingItem) {
      settingItem.setVisible(false);
    }

    final MenuItem shareItem = mOptionsMenu.findItem(R.id.action_share);
    if (null != shareItem) {
      shareItem.setVisible(false);
    }

    final MenuItem submitItem = mOptionsMenu.findItem(R.id.action_submit);
    if (null != submitItem) {
      submitItem.setVisible(true);
    }
  }
}
