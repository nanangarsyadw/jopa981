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

package com.zftlive.android.sample.dynamic;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zftlive.android.R;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.dynamicpage.ui.DynamicPageActivity;

/**
 * 动态构建示例
 *
 * @author 曾繁添
 * @version 1.0
 */
public class DynamicPageDemoActivity extends CommonActivity implements View.OnClickListener {

    private Button mScrollviewPageBtn, mListviewPageBtn, mPartDynamicBtn;

    @Override
    public int bindLayout() {
        return R.layout.activity_dynamic_pages;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        mScrollviewPageBtn = (Button) findViewById(R.id.btn_dynamic_scrollview);
        mListviewPageBtn = (Button) findViewById(R.id.btn_dynamic_listview);
        mPartDynamicBtn = (Button) findViewById(R.id.btn_dynamic_part);
        mScrollviewPageBtn.setOnClickListener(this);
        mListviewPageBtn.setOnClickListener(this);
        mPartDynamicBtn.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        //初始化带返回按钮的标题栏
        String strCenterTitle = getResources().getString(R.string.DynamicPageDemoActivity);
        mWindowTitle.initBackTitleBar(strCenterTitle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dynamic_scrollview:
                getOperation().forward(DynamicPageActivity.class);
                break;
            case R.id.btn_dynamic_listview:
                getOperation().forward(DynamicPageActivity.class);
                break;
            case R.id.btn_dynamic_part:
                getOperation().forward(DynamicPageActivity.class);
                break;
        }
    }
}
