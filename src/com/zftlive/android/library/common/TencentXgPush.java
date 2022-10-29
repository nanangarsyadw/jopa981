package com.zftlive.android.library.common;

import android.app.Notification;
import android.content.Context;
import android.media.RingtoneManager;

import com.tencent.android.tpush.XGCustomPushNotificationBuilder;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.zftlive.android.R;
import com.zftlive.android.library.tools.ToolLog;

/**
 * 腾讯信鸽推送工具类封装
 * @author 曾繁添
 * @version 1.0
 *
 */
public class TencentXgPush {
	
	/**
	 * 打印Log开关，上线请关闭
	 */
	public static final boolean isDebug = true;
	
	/**
	 * Log输出标志
	 */
	private static final String TAG = "TencentXgPush";
	
	/**
	 * 配置信鸽信息
	 * 
	 * @param mContext 上下文
	 * @param accessId 信鸽后台创建应用生成的accessId
	 * @param accessKey 信鸽后台创建应用生成的accessKey
	 */
	public static void config(Context mContext,long accessId,String accessKey){
		
		/**
		 * 是否开启debug模式
		 * （重要：为保证数据的安全性，请在发布时确保已关闭debug模式，否则造成的一切后果自负） 
		 */
		XGPushConfig.enableDebug(mContext, isDebug);
		//如果已在AndroidManifest.xml配置过，不需要再次调用；如果2者都存在，以接口为准
		XGPushConfig.setAccessId(mContext, accessId);
		//如果已在AndroidManifest.xml配置过，不需要再次调用；如果2者都存在，以接口为准
		XGPushConfig.setAccessKey(mContext, accessKey);
	}
	
	/**
	 * 注册信鸽
	 * @param mContext 上下文
	 */
	public static void registerPush(final Context mContext){
		XGPushManager.registerPush(mContext,
				new XGIOperateCallback() {
					@Override
					public void onSuccess(Object data, int flag) {
						ToolLog.d(TAG, "信鸽注册成功,getToken-->"+XGPushConfig.getToken(mContext));
					}

					@Override
					public void onFail(Object data, int errCode, String msg) {
						ToolLog.e(TAG, "信鸽注册失败-->errCode="+errCode + " "+msg);
					}
				});
	}
	
	/**
	 * 设置通知自定义View，这样在下发通知时可以指定build_id。编号由开发者自己维护,build_id=0为默认设置
	 * 
	 * @param mContext 上下文
	 */
	public static void customPushNotifyLayout(Context mContext) {
		XGCustomPushNotificationBuilder build = new XGCustomPushNotificationBuilder();
		// 设置声音
		build.setSound(RingtoneManager.getActualDefaultRingtoneUri(mContext, RingtoneManager.TYPE_ALARM));
		//设定Raw下指定声音文件 
		//build.setSound(Uri.parse("android.resource://" + mContext.getPackageName()+ "/" + R.raw.wind));
		build.setDefaults(Notification.DEFAULT_VIBRATE); // 振动
		build.setFlags(Notification.FLAG_NO_CLEAR); // 是否可清除
		// 设置自定义通知layout,通知背景等可以在layout里设置
		build.setLayoutId(R.layout.common_push_notification);
		// 设置自定义通知内容id
		build.setLayoutTextId(R.id.content);
		// 设置自定义通知标题id
		build.setLayoutTitleId(R.id.title);
		// 设置自定义通知图片id
		build.setLayoutIconId(R.id.icon);
		// 设置自定义通知图片资源
		build.setLayoutIconDrawableId(R.drawable.ic_launcher);
		// 设置状态栏的通知小图标
		build.setIcon(R.drawable.ic_launcher);
		// 设置时间id
		build.setLayoutTimeId(R.id.time);
		// 若不设定以上自定义layout，又想简单指定通知栏图片资源
		// build.setNotificationLargeIcon(R.drawable.ic_action_search);
		// 客户端保存build_id
		// XGPushManager.setPushNotificationBuilder(this, build_id, build);
	}
}
