package com.zftlive.android.sample.sms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.tools.ToolAlert;
import com.zftlive.android.tools.ToolSMS;
import com.zftlive.android.tools.ToolString;

public class SMSOperationActivity extends BaseActivity implements
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
	public void initView(View view) {
		et_phonenumber = (EditText) findViewById(R.id.et_phonenumber);
		et_content = (EditText) findViewById(R.id.et_content);
		btn_send = (Button) findViewById(R.id.btn_send);
		btn_send.setOnClickListener(this);

		btn_bind = (Button) findViewById(R.id.btn_bind);
		btn_bind.setOnClickListener(this);

		btn_choice = (Button) findViewById(R.id.btn_choice);
		btn_choice.setOnClickListener(this);

	}

	@Override
	public void doBusiness(Context mContext) {

	}

	@Override
	public void resume() {

	}

	@Override
	public void destroy() {

		if (mSMSBroadcastReceiver != null
				&& mSMSBroadcastReceiver.isOrderedBroadcast()) {
			// 取消订阅广播
			unregisterReceiver(mSMSBroadcastReceiver);
		}
	}

	@Override
	public void onClick(View v) {

		String phoneNumber = et_phonenumber.getText().toString();
		String strContent = et_content.getText().toString();

		switch (v.getId()) {
		case R.id.btn_choice:
			// 跳转至选择联系人界面
			Intent intent = new Intent(Intent.ACTION_PICK,
					ContactsContract.Contacts.CONTENT_URI);
			this.startActivityForResult(intent, CHOICE_PHONE);
			break;
		case R.id.btn_bind:
			if (ToolString.isNoBlankAndNoNull(phoneNumber)) {
				IntentFilter filter = new IntentFilter();
				filter.addAction("android.provider.Telephony.SMS_RECEIVED");
				filter.setPriority(Integer.MAX_VALUE);
				mSMSBroadcastReceiver = new SMSBroadcastReceiver();
				registerReceiver(mSMSBroadcastReceiver, filter);
				ToolAlert.showShort(this, "绑定拦截成功");
			} else {
				ToolAlert.showShort(this, "手机号不能为空");
			}
			break;
		case R.id.btn_send:

			if (ToolString.isNoBlankAndNoNull(phoneNumber)
					&& ToolString.isNoBlankAndNoNull(strContent)) {
				ToolSMS.sendMessage(phoneNumber, strContent);
				// ToolSMS.sendMessage(this, phoneNumber,
				// strContent);//跳转到发送短信界面
			} else {
				ToolAlert.showShort(this, "手机号和短信内容两者都不能为空");
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
		        et_phonenumber.setText(getContactPhone(c));  
			}
			break;
		default:
			break;
		}
	}

	// 获取联系人电话
	private String getContactPhone(Cursor cursor) {

		int phoneColumn = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
		int phoneNum = cursor.getInt(phoneColumn);
		String phoneResult = "";
		// System.out.print(phoneNum);
		if (phoneNum > 0) {
			// 获得联系人的ID号
			int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
			String contactId = cursor.getString(idColumn);
			// 获得联系人的电话号码的cursor;
			Cursor phones = getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ contactId, null, null);
			// int phoneCount = phones.getCount();
			// allPhoneNum = new ArrayList<String>(phoneCount);
			if (phones.moveToFirst()) {
				// 遍历所有的电话号码
				for (; !phones.isAfterLast(); phones.moveToNext()) {
					int index = phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
					int typeindex = phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
					int phone_type = phones.getInt(typeindex);
					String phoneNumber = phones.getString(index);
					switch (phone_type) {
					case 2:
						phoneResult = phoneNumber;
						break;
					}
					// allPhoneNum.add(phoneNumber);
				}
				if (!phones.isClosed()) {
					phones.close();
				}
			}
		}
		return phoneResult;
	}

}
