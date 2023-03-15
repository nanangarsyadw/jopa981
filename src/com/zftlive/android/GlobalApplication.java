package com.zftlive.android;

import android.content.Intent;
import android.text.TextUtils;

import com.zftlive.android.common.MyNetworkListener;
import com.zftlive.android.library.MApplication;
import com.zftlive.android.library.common.MTAStatService;
import com.zftlive.android.library.common.TencentXgPush;
import com.zftlive.android.library.tools.ToolChannel;
import com.zftlive.android.library.tools.ToolData;
import com.zftlive.android.library.tools.ToolHTTP;

/**
 * AjavaSample全局的Application
 * 
 * @author 曾繁添
 * @version 1.0
 *
 */
public class GlobalApplication extends MApplication {
	
	/**
	 * MTA APPKEY
	 */
	public static final String MAT_APPKEY = "A1D5J6XB1XMY";
	
	/**
	 * XG_V2_ACCESS_ID APPKEY
	 */
	public static final long XG_V2_ACCESS_ID = 2100129668;
	
	/**
	 * XG_V2_ACCESS_KEY
	 */
	public static final String XG_V2_ACCESS_KEY = "A49F4RD3YR5T";
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		//初始化MTA云统计
		MTAStatService.initMTA(this,gainChannelId(),MAT_APPKEY);
		
		//初始化信鸽推送
		TencentXgPush.config(this, XG_V2_ACCESS_ID, XG_V2_ACCESS_KEY);
		TencentXgPush.registerPush(this);
		TencentXgPush.customPushNotifyLayout(this);
		
		//启动Service
		Intent mIntent = new Intent(this, MyNetworkListener.class);
		startService(mIntent);
				
//		//启动网络监听Service
//		NetworkStateService.startService(this, new OnNetworkChangeListener() {
//			
//			@Override
//			public void onNoNetwork() {
//				ToolAlert.toastShort(gainContext(), "OMG 木有网络了~~");
//			}
//			
//			@Override
//			public void onNetworkChange(String networkType) {
//				ToolAlert.toastShort(gainContext(), "当前网络："+networkType);
//			}
//		});
	}
	
	/**
	 * 退出APP时手动调用
	 */
	@Override
	public void exit() {
		try {
			//停止网络监听
			Intent mIntent = new Intent(this, MyNetworkListener.class);
			stopService(mIntent);
			
			//取消所有请求
			ToolHTTP.stopAllRequest();
			//关闭所有Activity
			removeAll();
			//退出进程
			System.exit(0);
		} catch (Exception e) {
		}
	}
	
	/**
	 * 获取渠道号
	 * @return
	 */
	public static String gainChannelId(){
		//META-INF文件夹写入的渠道号
		String strMetaInfChannel = ToolChannel.gainChannel(gainContext(), ToolChannel.CHANNEL_KEY);
		if(!TextUtils.isEmpty(strMetaInfChannel)){
			channelId = strMetaInfChannel;
			return channelId;
		}
		
		//AndroidManifest.xml配置的渠道号
		String strManifestChannel = ToolData.gainMetaData(gainContext(), GlobalApplication.class, "InstallChannel");
		if(!TextUtils.isEmpty(strManifestChannel)){
			channelId = strManifestChannel;
			return channelId;
		}
		
		return channelId;
	}
}
