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

package com.zftlive.android.sample.basic;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zftlive.android.R;
import com.zftlive.android.common.WebPageActivity;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.tools.ToolToast;

/**
 * 网页加载示例
 *
 * @author 曾繁添
 * @version 1.0
 */
public class LoadHTMLActivity extends CommonActivity implements View.OnClickListener {

    private EditText et_web_url;
    private Button btn_go;

    @Override
    public int bindLayout() {
        return R.layout.activity_load_html;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        et_web_url = (EditText) findViewById(R.id.et_loadurl);
        btn_go = (Button) findViewById(R.id.btn_go);
        btn_go.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

        //初始化值
        et_web_url.setText("http://git.oschina.net/zftlive/");

        //初始化带返回按钮的标题栏
        String strCenterTitle = getResources().getString(R.string.LoadHTMLActivity);
//      ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
        mWindowTitle.initBackTitleBar(strCenterTitle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go:
                String strLinkURL = et_web_url.getText().toString();
                if (TextUtils.isEmpty(strLinkURL)) {
                    ToolToast.showShort(getContext(), "请输入要打开的网址");
                }
                if (!strLinkURL.startsWith("http://")) {
                    strLinkURL = "http://" + strLinkURL;
                }
                getOperation().addParameter(WebPageActivity.LINK_URL, strLinkURL);
                getOperation().addParameter(WebPageActivity.SHOW_SHARE, true);
                getOperation().forward(WebPageActivity.class.getName(), LEFT_RIGHT);
                break;
            default:
                break;
        }
    }
}
