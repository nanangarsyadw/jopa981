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

package com.zftlive.android.common;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.zftlive.android.R;
import com.zftlive.android.library.Alert;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.tools.ToolString;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * 意见反馈Activity
 *
 * @author 曾繁添
 * @version 1.0
 */
public class FeedbackActivity extends CommonActivity {

    private EditText et_message;
    private final static String FEED_BACK_URL = "";

    @Override
    public int bindLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @SuppressLint("NewApi")
    @Override
    public void initView(View view) {
        et_message = (EditText) findViewById(R.id.et_message);

        //初始化返回按钮
        String strCenterTitle = getResources().getString(R.string.FeedbackActivity);
//      ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
        mWindowTitle.initBackTitleBar(strCenterTitle);
        mWindowTitle.initRightDoneBtn("提交", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //提交表单
                if (validateForm()) {
                    //需要设置表单元素控件的tag属性，作为表单提交的key
//              DTO<String,Object> form = ToolData.gainForm(root, new DTO<String,Object>());
//              ToolHTTP.post(FEED_BACK_URL, form, getFeedBackHandler());
                }
            }
        });
    }

    /**
     * 表单验证
     *
     * @return
     */
    private boolean validateForm() {
        String strMessage = et_message.getText().toString();
        if (ToolString.isNoBlankAndNoNull(strMessage)) {
            return true;
        } else {
            Alert.toastShort(getContext(), "请输入意见内容再提交，谢谢^_^");
            return false;
        }
    }

    /*
     * 提交意见反馈Handler
     */
    private JsonHttpResponseHandler getFeedBackHandler() {
        return new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Alert.toastShort(getContext(), "反馈意见失败，原因：" + throwable.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Alert.toastShort(getContext(), "感谢您的意见!");
                finish();
            }
        };
    }
}
