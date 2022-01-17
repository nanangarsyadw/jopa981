package com.zftlive.android.sample.sms;

import android.content.Context;
import android.content.IntentFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.tools.ToolAlert;
import com.zftlive.android.tools.ToolSMS;
import com.zftlive.android.tools.ToolString;

public class SMSOperationActivity extends BaseActivity implements View.OnClickListener{

	private EditText et_phonenumber,et_content;
	private Button btn_send,btn_bind;
	private SMSBroadcastReceiver mSMSBroadcastReceiver;
	
	@Override
	public int bindLayout() {
		return R.layout.activity_sms_operation;
	}

	@Override
	public void initView(View view) {
		et_phonenumber = (EditText)findViewById(R.id.et_phonenumber);
		et_content = (EditText)findViewById(R.id.et_content);
		btn_send = (Button)findViewById(R.id.btn_send);
		btn_send.setOnClickListener(this);
		
		btn_bind = (Button)findViewById(R.id.btn_bind);
		btn_bind.setOnClickListener(this);
		
	}

	@Override
	public void doBusiness(Context mContext) {

	}

	@Override
	public void resume() {

	}

	@Override
	public void destroy() {
		
		if(mSMSBroadcastReceiver != null && mSMSBroadcastReceiver.isOrderedBroadcast()){
			//取消订阅广播
			unregisterReceiver(mSMSBroadcastReceiver);
		}
	}

	@Override
	public void onClick(View v) {
		
		String phoneNumber = et_phonenumber.getText().toString();
		String strContent = et_content.getText().toString();
		
		switch (v.getId()) {
		case R.id.btn_bind:
			if(ToolString.isNoBlankAndNoNull(phoneNumber)){
				IntentFilter filter = new IntentFilter();
				filter.addAction("android.provider.Telephony.SMS_RECEIVED");
				filter.setPriority(Integer.MAX_VALUE);
				mSMSBroadcastReceiver = new SMSBroadcastReceiver();
				registerReceiver(mSMSBroadcastReceiver, filter);
				ToolAlert.showShort(this, "绑定拦截成功");
			}else{
				ToolAlert.showShort(this, "手机号不能为空");
			}
			break;
		case R.id.btn_send:
			
			if(ToolString.isNoBlankAndNoNull(phoneNumber)&&ToolString.isNoBlankAndNoNull(strContent)){
				ToolSMS.sendMessage(phoneNumber, strContent);
//				ToolSMS.sendMessage(this, phoneNumber, strContent);//跳转到发送短信界面
			}else{
				ToolAlert.showShort(this, "手机号和短信内容两者都不能为空");
			}
			break;
		default:
			break;
		}
	}

}
