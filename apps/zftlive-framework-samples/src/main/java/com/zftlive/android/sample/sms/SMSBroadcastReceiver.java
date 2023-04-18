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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import com.zftlive.android.library.Alert;
import com.zftlive.android.library.base.BaseBroadcastReceiver;

/**
 * 短信广播接收器（可用于短信拦截）
 * @author 曾繁添
 * @version 1.0
 *
 */
public class SMSBroadcastReceiver extends BaseBroadcastReceiver {
	
	public final static String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";
	
	/**短信集合<电话号码,短信内容>**/
	private List<Map<String,String>> smsList = new ArrayList<Map<String,String>>();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		//短信广播
		if(intent.getAction().equals(SMS_ACTION))
		{
			//获取拦截到的短信数据
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");   
            SmsMessage[] messages = new SmsMessage[pdus.length];   
            for (int i = 0; i < pdus.length; i++)   
            {   
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);   
            }
            
            // 将送来的短信合并自定义信息于StringBuilder当中   
            for (SmsMessage message : messages)
            {   
            	Map<String,String> sms = new HashMap<String,String>();
            	sms.put(message.getDisplayOriginatingAddress(), message.getDisplayMessageBody());
            	smsList.add(sms);
            	Alert.toastLong(context, "SMSBroadcastReceiver-->拦截到来自【"+message.getDisplayOriginatingAddress()+"】的短信-->"+message.getDisplayMessageBody());
            }
			//取消广播，系统将收不到短信）   
            //abortBroadcast();
		}
	}

}
