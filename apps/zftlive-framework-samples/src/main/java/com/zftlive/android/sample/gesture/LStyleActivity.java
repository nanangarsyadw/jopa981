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
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zftlive.android.R;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.widget.gesture.Lock9View;

/**
 * 九宫格解锁示例-5.0风格样式
 * 
 * @author 曾繁添
 * @version 1.0
 *
 */
public class LStyleActivity extends CommonActivity {

    protected Lock9View lock9View;

    @Override
    public int bindLayout() {
      return R.layout.activity_lock_l_style;
    }

    @Override
    public void initParams(Bundle parms) {
      
    }

    @Override
    public void initView(View view) {
      lock9View = (Lock9View) findViewById(R.id.lock_9_view);
      lock9View.setCallBack(new Lock9View.CallBack() {

          @Override
          public void onFinish(String password) {
              Toast.makeText(LStyleActivity.this, password, Toast.LENGTH_SHORT).show();
          }

      });      
    }

    @Override
    public void doBusiness(Context mContext) {
      //初始化带返回按钮的标题栏
      String strCenterTitle = getResources().getString(R.string.Lock9DemoActivity);
      mWindowTitle.initBackTitleBar(strCenterTitle);
    }
}
