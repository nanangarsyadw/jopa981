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
package com.zftlive.android.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import cn.smssdk.SMSSDK;
/** 短信监听接收器，用于自动获取短信验证码，然后自动填写到验证码区域*/
public class SMSReceiver extends BroadcastReceiver {

	private static final String ACTION_SMS_RECEIVER = "android.provider.Telephony.SMS_RECEIVED";

	private SMSSDK.VerifyCodeReadListener listener;
	public SMSReceiver(SMSSDK.VerifyCodeReadListener verifyCodeReadListener) {
		this.listener = verifyCodeReadListener;
	}


	/**
	 * 不要使用AndroidManifest.xml配置的方式注册Receiver,
	 * 请使用Context.registerReceiver注册监听器, 因为初始化的时候要传入监听器
	 */
	public SMSReceiver() {
		String msg = "Please dynamically register an instance of this class with Context.registerReceiver."
				+"\r\nIf not, the SMSSDK.VerifyCodeReadListener will be null!";
		Log.w("cn.smssdk.gui.SMSReceiver", msg);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if(ACTION_SMS_RECEIVER.equals(intent.getAction())) {
			Bundle bundle = intent.getExtras();
			if(bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] smsArr = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                	smsArr[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				}

                for (SmsMessage sms: smsArr) {
                	if(sms != null) {
                		SMSSDK.readVerificationCode(sms, listener);
                	}
				}
			}// END if(bundle != null)
		}
	}
}
