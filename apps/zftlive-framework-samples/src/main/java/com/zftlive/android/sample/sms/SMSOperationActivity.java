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

package com.zftlive.android.sample.sms;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zftlive.android.R;
import com.zftlive.android.library.Alert;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.tools.ToolPhone;
import com.zftlive.android.library.tools.ToolString;

public class SMSOperationActivity extends CommonActivity implements
		View.OnClickListener {

	private EditText et_phonenumber, et_content;
	private Button btn_send, btn_bind, btn_choice;
	private SMSBroadcastReceiver mSMSBroadcastReceiver;
	private final static int CHOICE_PHONE = 1;

	@Override
	public int bindLayout() {
		return R.layout.activity_sms_operation;
	}

	@Override
	public void initParams(Bundle parms) {
		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {
		et_phonenumber = (EditText) findViewById(R.id.et_phonenumber);
		et_content = (EditText) findViewById(R.id.et_content);
		btn_send = (Button) findViewById(R.id.btn_send);
		btn_send.setOnClickListener(this);

		btn_bind = (Button) findViewById(R.id.btn_bind);
		btn_bind.setOnClickListener(this);

		btn_choice = (Button) findViewById(R.id.btn_choice);
		btn_choice.setOnClickListener(this);
		
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.SMSOperationActivity);
//      ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
		mWindowTitle.initBackTitleBar(strCenterTitle);
	}

	@Override
	protected void onDestroy() {
		if (mSMSBroadcastReceiver != null
						&& mSMSBroadcastReceiver.isOrderedBroadcast()) {
			// 取消订阅广播
			unregisterReceiver(mSMSBroadcastReceiver);
		}
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {

		String phoneNumber = et_phonenumber.getText().toString();
		String strContent = et_content.getText().toString();

		switch (v.getId()) {
		case R.id.btn_choice:
			// 跳转至选择联系人界面
			ToolPhone.toChooseContactsList(this, CHOICE_PHONE);
			break;
		case R.id.btn_bind:
			if (ToolString.isNoBlankAndNoNull(phoneNumber)) {
				IntentFilter filter = new IntentFilter();
				filter.addAction("android.provider.Telephony.SMS_RECEIVED");
				filter.setPriority(Integer.MAX_VALUE);
				mSMSBroadcastReceiver = new SMSBroadcastReceiver();
				registerReceiver(mSMSBroadcastReceiver, filter);
				Alert.toastShort(this, "绑定拦截成功");
			} else {
				Alert.toastShort(this, "手机号不能为空");
			}
			break;
		case R.id.btn_send:

			if (ToolString.isNoBlankAndNoNull(phoneNumber)
					&& ToolString.isNoBlankAndNoNull(strContent)) {
				ToolPhone.sendMessage(this,phoneNumber, strContent);
				// ToolSMS.sendMessage(this, phoneNumber,
				// strContent);//跳转到发送短信界面
			} else {
				Alert.toastShort(this, "手机号和短信内容两者都不能为空");
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case CHOICE_PHONE:
			if (Activity.RESULT_OK == resultCode) {
				Uri uri = data.getData();
				Cursor c = managedQuery(uri, null, null, null, null);
				c.moveToFirst();  
		        et_phonenumber.setText(ToolPhone.getChoosedPhoneNumber(this,resultCode,data));  
			}
			break;
		default:
			break;
		}
	}

}
