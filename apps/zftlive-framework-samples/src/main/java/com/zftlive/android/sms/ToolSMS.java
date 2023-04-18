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

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.zftlive.android.library.MApplication;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


/**
 * 发送短信验证码工具类
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ToolSMS {
	
	public static String APPKEY = "843e7d4ff9a9";
	public static String APPSECRET = "f2eabd82678cdf3ad78aa3e5f726b3f8";
	public static String CHINA = "86";
	private static IValidateSMSCode mIValidateSMSCode;
	private static Handler mSMSHandle = new MySMSHandler();
	
	/**
	 * 初始化ShareSDK发送短信验证码实例
	 * @param appkey
	 * @param appSecrect
	 */
	public static void initSDK(String appkey, String appSecrect){
		initSDK(MApplication.gainContext(),appkey,appSecrect);
	}
	
	/**
	 * 初始化ShareSDK发送短信验证码实例
	 * @param mContext 上下文
	 * @param appkey
	 * @param appSecrect
	 */
	public static void initSDK(Context mContext,String appkey, String appSecrect){
		// 初始化短信SDK
		SMSSDK.initSDK(mContext, appkey, appSecrect);
		//注册回调监听接口
		SMSSDK.registerEventHandler(new EventHandler() {
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				mSMSHandle.sendMessage(msg);
			}
		});
	}
	
	/**
	 * 请求获取短信验证码
	 * @param phone 手机号
	 */
	public static void getVerificationCode(String phone){
		SMSSDK.getVerificationCode (CHINA, phone);
	}
	
	/**
	 * 请求获取语音验证码
	 * @param phone 手机号
	 */
	public static void getVoiceVerifyCode(String phone){
		SMSSDK.getVoiceVerifyCode(CHINA, phone);
	}
	
	/**
	 * 提交短信验证码，校验是否正确
	 * @param phone 手机号
	 * @param validateCode 手机短信验证码
	 */
	public static void submitVerificationCode(String phone, String validateCode,IValidateSMSCode callback){
		mIValidateSMSCode = callback;
		SMSSDK.submitVerificationCode(CHINA, phone, validateCode);
	}
	
	/**
	 * 释放资源
	 */
	public static void release(){
		// 销毁回调监听接口
		SMSSDK.unregisterAllEventHandler();
		mIValidateSMSCode = null;
	}
	
    /**
     * 消息处理Handle
     */
	private static class MySMSHandler extends Handler{
    	@Override
		public void handleMessage(Message msg) {
    		super.handleMessage(msg);
    		
    		int event = msg.arg1;
    		int result = msg.arg2;
    		Object data = msg.obj;
			
			if (result == SMSSDK.RESULT_COMPLETE) {
				//提交验证码成功
				if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
					//验证成功回调
					if(null != mIValidateSMSCode){
						mIValidateSMSCode.onSucced();
					}
				} 
			} else {
				Throwable exption = ((Throwable) data);
				//验证成功回调
				if(null != mIValidateSMSCode){
					mIValidateSMSCode.onFailed(exption);
				}
			}
		}
    }
	
    /**
     * 提交短信验证码回调接口
     */
    public interface IValidateSMSCode{
    	void onSucced();
    	void onFailed(Throwable e);
    }
}
