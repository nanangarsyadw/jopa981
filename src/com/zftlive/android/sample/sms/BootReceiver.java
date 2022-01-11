package com.zftlive.android.sample.sms;

import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import com.zftlive.android.base.BaseBroadcastReceiver;
import com.zftlive.android.tools.ToolAlert;

/**
 * 开机自启动订阅广播
 * @author 曾繁添
 * @version 1.0
 *
 */
public class BootReceiver extends BaseBroadcastReceiver {

	public final static String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";
	
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
            	ToolAlert.showLong(context, "BootReceiver-->拦截到来自【"+message.getDisplayOriginatingAddress()+"】的短信-->"+message.getDisplayMessageBody());
            }
			//取消广播，系统将收不到短信）   
            //abortBroadcast();
		}
	}

}
