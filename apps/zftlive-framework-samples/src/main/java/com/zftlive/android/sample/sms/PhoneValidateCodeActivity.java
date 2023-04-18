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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zftlive.android.R;
import com.zftlive.android.library.Alert;
import com.zftlive.android.library.MApplication;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.network.ToolNetwork;
import com.zftlive.android.library.tools.ToolString;
import com.zftlive.android.sms.SMSReceiver;
import com.zftlive.android.sms.ToolSMS;

import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.SMSSDK;


/**
 * 手机短信验证码示例，支持移动/联通/电信三网大陆手机号
 * @author 曾繁添
 * @version 1.0
 *
 */
public class PhoneValidateCodeActivity extends CommonActivity {

	private EditText et_phone,et_phone_code;
	private Button btn_gain_smscode,btn_validate;
	private Timer mTimer = null;
	private TimerTask mTimerTask = null;
	private static int delay = 1 * 1000;  //1s
	private static int period = 1 * 1000;  //1s
	private static int count = 60;  
	private static final int UPDATE_TEXTVIEW = 99;
	private BroadcastReceiver smsReceiver;
	
	@Override
	public int bindLayout() {
		return R.layout.activity_phonevalidate_code;
	}

	@Override
	public void initParams(Bundle parms) {
		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {
		et_phone = (EditText)findViewById(R.id.et_phone);
		et_phone_code = (EditText)findViewById(R.id.et_phone_code);
		btn_gain_smscode = (Button)findViewById(R.id.btn_gain_smscode);
		btn_validate = (Button)findViewById(R.id.btn_validate);
		
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.PhoneValidateCodeActivity);
//      ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
		mWindowTitle.initBackTitleBar(strCenterTitle);
	}

	@Override
	public void doBusiness(final Context mContext) {
		
		//注册SMSDK，可放置Application
		ToolSMS.initSDK("843e7d4ff9a9", "f2eabd82678cdf3ad78aa3e5f726b3f8");

		//验证不可用
		et_phone_code.setEnabled(false);
		btn_validate.setEnabled(false);
		btn_gain_smscode.setText(String.format(getResources().getString(R.string.sms_timer), count));


		//获取验证码
		btn_gain_smscode.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(ToolString.isNoBlankAndNoNull(et_phone.getText().toString().trim())){

					if(ToolNetwork.getInstance().init(getContext()).isConnected()){
						ToolSMS.getVerificationCode(et_phone.getText().toString());
						startTimer();
					}else{
						Alert.toastShort(mContext, "请先开启网络测试");
					}

				}else{
					Alert.toastShort("请输入大陆的手机号码");
				}
			}
		});

		//校验验证码是否正确
		btn_validate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(et_phone_code.getText().toString() != ""){
					ToolSMS.submitVerificationCode(et_phone.getText().toString(),et_phone_code.getText().toString(),new ToolSMS.IValidateSMSCode() {

						@Override
						public void onSucced() {
							Alert.toastShort(PhoneValidateCodeActivity.this, "验证成功");
							//释放监听器
							ToolSMS.release();
						}

						@Override
						public void onFailed(Throwable e) {
							Alert.toastShort(PhoneValidateCodeActivity.this, "验证失败，原因："+e.getMessage());
						}
					});
				}else{
					Alert.toastShort("请输入手机验证码");
				}
			}
		});

		//自动读取验证码
		smsReceiver = new SMSReceiver(new SMSSDK.VerifyCodeReadListener(){

			@Override
			public void onReadVerifyCode(final String verifyCode) {

				getContext().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						et_phone_code.setText(verifyCode);
					}
				});
			}
		});
		getContext().registerReceiver(smsReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
		
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(smsReceiver);
		super.onDestroy();
	}

	/**
	 * 启动Timer
	 */
	private void startTimer(){
		
		stopTimer();
		
		//输入框不可用，获取验证码按钮不可用
		et_phone.setEnabled(false);
		btn_gain_smscode.setEnabled(false);
		btn_validate.setEnabled(true);
		et_phone_code.setEnabled(true);
		
		if (mTimer == null) {
			mTimer = new Timer();
		}
		if (mTimerTask == null) {
			mTimerTask = new TimerTask() {
				@Override
				public void run() {
					Message message = Message.obtain(handler, UPDATE_TEXTVIEW);     
		            handler.sendMessage(message);   
					count --;
				}
			};
		}

		if(mTimer != null && mTimerTask != null )
			mTimer.schedule(mTimerTask, delay, period);
	}
	
	/**
	 * 停止Timer
	 */
	private void stopTimer(){
		
		btn_gain_smscode.setEnabled(true);
		et_phone.setEnabled(true);
		
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}

		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;
		}	
		
		count = 60;
		btn_gain_smscode.setText(String.format(getResources().getString(R.string.sms_timer), count));
		
	}
	
	/**
	 * 更新倒计时
	 */
	private void updateTextView(){
		
		//停止Timer
		if(count == 0){
			stopTimer();
			return ;
		}
		
		if(count < 10){
			btn_gain_smscode.setText(String.format(getResources().getString(R.string.sms_timer), "0"+String.valueOf(count)));
		}else{
			btn_gain_smscode.setText(String.format(getResources().getString(R.string.sms_timer), count));
		}
	}
	
	/***处理UI线程更新Handle**/
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) 
		{
							
			switch (msg.what) {
			case UPDATE_TEXTVIEW:
				updateTextView();
				break;
			
			default:
				break;
			}
		};
	};
}
