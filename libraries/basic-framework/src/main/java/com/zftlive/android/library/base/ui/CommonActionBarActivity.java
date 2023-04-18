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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 具备Actionbar特性相关的Activity的基类
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public abstract class CommonActionBarActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // 显示VoerFlowMenu
    displayOverflowMenu(mActivity);
  }

  /**
   * 显示Actionbar菜单图标，需要使用ActionBar时使用
   */
  @Override
  public boolean onMenuOpened(int featureId, Menu menu) {
    if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
      if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
        try {
          Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
          m.setAccessible(true);
          m.invoke(menu, true);// 显示
        } catch (Exception e) {
          Log.e(TAG, "onMenuOpened-->" + e.getMessage());
        }
      }
    }
    return super.onMenuOpened(featureId, menu);
  }

  /**
   * Actionbar点击返回键关闭事件
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * 显示OverFlowMenu按钮，需要使用ActionBar时使用
   *
   * @param mContext 上下文Context
   */
  private void displayOverflowMenu(Context mContext) {
    try {
      ViewConfiguration config = ViewConfiguration.get(mContext);
      Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
      if (menuKeyField != null) {
        menuKeyField.setAccessible(true);
        menuKeyField.setBoolean(config, false);// 显示
      }
    } catch (Exception e) {
      Log.e("ActionBar", e.getMessage());
    }
  }

  @Override
  public void doBusiness(Context mContext) {

  }
}
