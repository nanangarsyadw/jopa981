/*
 * Copyright (C) 2013 Manuel Peinado
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zftlive.android.sample.fadingactionbar;

import com.zftlive.android.R;
import com.zftlive.android.common.ActionBarManager;
import com.zftlive.android.view.fadingactionbar.FadingActionBarHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;


public class NoParallaxActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FadingActionBarHelper helper = new FadingActionBarHelper()
            .actionBarBackground(R.drawable.ab_background)
            .headerLayout(R.layout.header)
            .contentLayout(R.layout.activity_scrollview)
            .parallax(false);
        setContentView(helper.createView(this));
        helper.initActionBar(this);
        
        //初始化带返回按钮的标题栏
  		ActionBarManager.initBackTitle(this, getActionBar(), this.getClass().getSimpleName());  
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
