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

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;

import com.zftlive.android.library.Alert;
import com.zftlive.android.library.base.BaseBroadcastReceiver;
import com.zftlive.android.library.base.BaseService;

public class SMSInterceptService extends BaseService {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		filter.setPriority(Integer.MAX_VALUE-1);
		registerReceiver(new SmsReceiver(), filter);
	}

	/**
	 * 订阅短信广播
	 * 
	 */
	protected class SmsReceiver extends BaseBroadcastReceiver 
	{

		@Override
		public void onReceive(Context context, Intent intent) 
		{
			Bundle bundle = intent.getExtras();
			if (bundle != null) 
			{
				Object[] pdusObjects = (Object[]) bundle.get("pdus");
				SmsMessage[] messages = new SmsMessage[pdusObjects.length];
				for (int i = 0; i < pdusObjects.length; i++) {
					messages[i] = SmsMessage.createFromPdu((byte[]) pdusObjects[i]);
				}

				for (SmsMessage message : messages) {
					System.out.println("SMSInterceptService-->来信号码："+ message.getDisplayOriginatingAddress()+ "\n短信内容：" + message.getDisplayMessageBody());
					Alert.toastLong(context,"SMSInterceptService.SmsReceiver-->拦截到来自【" + message.getDisplayOriginatingAddress()+ "】的短信-->"+ message.getDisplayMessageBody());
				}
			}
		}

	}

}
