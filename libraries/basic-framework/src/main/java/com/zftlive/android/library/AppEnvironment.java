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

package com.zftlive.android.library;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.hardware.Camera;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.zftlive.android.library.common.ILoginResponseHandler;
import com.zftlive.android.library.common.IUser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * 运行环境信息
 * @author 曾繁添
 * @version 1.0
 */
public class AppEnvironment {

	/***Log输出标识**/
	private static final String TAG = AppEnvironment.class.getSimpleName();
	
	/***屏幕显示材质**/
	private static final DisplayMetrics mDisplayMetrics = new DisplayMetrics();
	
	/**上下文**/
	private static final Context context = MApplication.gainContext();
	
	/**操作系统名称(GT-I9100G)***/
	public static final String MODEL_NUMBER = Build.MODEL;
	
	/**操作系统名称(I9100G)***/
	public static final String DISPLAY_NAME = Build.DISPLAY;
	
	/**操作系统版本(4.4)***/
	public static final String OS_VERSION = Build.VERSION.RELEASE;;
	
	/**应用程序版本***/
	public static final String APP_VERSION = getVersionName();
	
	/***屏幕宽度**/
	public static final int SCREEN_WIDTH = getDisplayMetrics().widthPixels;
	
	/***屏幕高度**/
	public static final int SCREEN_HEIGHT = getDisplayMetrics().heightPixels;
	
	/***本机手机号码**/
//	public static final String PHONE_NUMBER = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
	
	/***设备ID**/
//	public static final String DEVICE_ID = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	
	/***设备IMEI号码**/
//	public static final String IMEI = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();
	
	/***设备IMSI号码**/
//	public static final String IMSI = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();

	/**
	 * 静态存储回调
	 */
	public static ILoginResponseHandler mLoginResponseHandler;

	/**
	 * 登录的用户
	 */
	private static IUser mLoginUser;

	/**
	 * 判断是否登录
	 * @return
	 */
	public static boolean isLogin(){
		if(null != mLoginUser && !TextUtils.isEmpty(mLoginUser.getUserId())){
			return true;
		}
		return false;
	}

	/**
	 * 清除登录缓存信息
	 */
	public static void clearLoginCache(){
		mLoginUser = null;
	}

	/**
	 * 设置登录用户
	 * @param user
	 */
	public static void setLoginUser(IUser user){
		mLoginUser = user;
	}

	/**
	 * 获得登录用户信息
	 * @return
	 */
	public static IUser getLoginUser(){
		return mLoginUser;
	}

	/**
	 * 校验登录状态
	 * @param mContext 上下文Context
	 * @param responseHandler 登录状态监测回调
	 */
	public static void validateLoginStatus(Context mContext, Class<? extends Activity> login, ILoginResponseHandler responseHandler){
		AppEnvironment.mLoginResponseHandler = responseHandler;
		Intent mIntent = new Intent();
		mIntent.setClass(mContext,login);
		mContext.startActivity(mIntent);
	}

	/**获取系统显示材质***/
	public static DisplayMetrics getDisplayMetrics(){
		  WindowManager windowMgr = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		  windowMgr.getDefaultDisplay().getMetrics(mDisplayMetrics);
		  return mDisplayMetrics;
	}
	
	/**获取摄像头支持的分辨率***/
	public static List<Camera.Size> getSupportedPreviewSizes(Camera camera){
        Camera.Parameters parameters = camera.getParameters(); 
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();
        return sizeList;
	}
	
	/**
	 * 获取应用程序版本（versionName）
	 * @return 当前应用的版本号
	 */
	public static String getVersionName() {
		PackageManager manager = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			Log.e(TAG, "获取应用程序版本失败，原因："+e.getMessage());
			return "";
		}
		
		return info.versionName;
	}
	
	/**
	 * 获取应用程序版本（versionName）
	 * @return 当前应用的版本号
	 */
	public static int getVersionCode() {
		PackageManager manager = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			Log.e(TAG, "获取应用程序版本失败，原因："+e.getMessage());
			return -1;
		}
		
		return info.versionCode;
	}
	
	/**
	 * 获取系统内核版本
	 * @return
	 */
	public static String getKernelVersion(){
		String strVersion= "";
		FileReader mFileReader = null;
		BufferedReader mBufferedReader = null;
		try {
			mFileReader = new FileReader("/proc/version");
			mBufferedReader = new BufferedReader(mFileReader, 8192);
			String str2 = mBufferedReader.readLine();
			strVersion = str2.split("\\s+")[2];//KernelVersion
			
		} catch (Exception e) {
			Log.e(TAG, "获取系统内核版本失败，原因："+e.getMessage());
		}finally{
			try {
				mBufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return strVersion;
	}
	
	
	/***
	 * 获取MAC地址
	 * @return
	 */
	public static String getMacAddress(){
		try{
			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			if(wifiInfo != null){
				return wifiInfo.getMacAddress();
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 获取IP地址
	 * @return
	 */
	public static String getIpAdress(){
		try{
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		if(wifiInfo != null){
			return Formatter.formatIpAddress(wifiInfo.getIpAddress());
		}
		}catch (Exception e){
			e.printStackTrace();
		}

		return "";
	}
	
	/**
	 * 获取运行时间
	 * @return 运行时间(单位/s)
	 */
	public static long getRunTimes() {
		long ut = SystemClock.elapsedRealtime() / 1000;
		if (ut == 0) {
			ut = 1;
		}
		return ut;
	}

	/**
	 * 判断是否为模拟器环境需要权限
	 * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
	 * @param mContext 上下文
	 * @return
	 */
	public static boolean isEmulator(Context mContext) {
		TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceID = telephonyManager.getDeviceId();
		// 如果 运行的 是一个 模拟器
		if (deviceID == null || deviceID.trim().length() == 0
				|| deviceID.matches("0+")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取可用内存
	 * @param mContext 上下文
	 * @return
	 */
    public static long gainUnusedMemory(Context mContext) {
        long MEM_UNUSED = 0L;
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        MEM_UNUSED = mi.availMem / 1024;
        return MEM_UNUSED;
    }

    /**
     * 获取总内存
     * @return
     */
    public static long gainTotalMemory() {
        long mTotal = 0;
        // /proc/meminfo读出的内核信息进行解析
        String path = "/proc/meminfo";
        String content = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path), 8);
            String line;
            if ((line = br.readLine()) != null) {
                content = line;
            }
            // beginIndex
            int begin = content.indexOf(':');
            // endIndex
            int end = content.indexOf('k');
            // 截取字符串信息

            content = content.substring(begin + 1, end).trim();
            mTotal = Integer.parseInt(content);
        } catch (Exception e) {
        	Log.e(TAG, "获取总内存失败，原因："+e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return mTotal;
    }
}
