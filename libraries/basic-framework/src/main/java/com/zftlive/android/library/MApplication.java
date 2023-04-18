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
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.zftlive.android.library.exception.AppCrashHandler;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 整个应用程序Applicaiton
 *
 * @author 曾繁添
 * @version 1.0
 */
public abstract class MApplication extends Application {

    /**
     * 对外提供整个应用生命周期的Context
     **/
    private static Context instance;
    /**ImageLoader**/
//	private ImageLoader mImageLoader;
    /**
     * 渠道ID
     **/
    public static String channelId = "Ajava";
    /**
     * 应用程序版本versionName
     **/
    public static String version = "error";
    /**
     * 设备ID
     **/
    public static String deviceId = "error";
    /**
     * 整个应用全局可访问数据集合
     **/
    private static Map<String, Object> gloableData = new HashMap<String, Object>();
    /***寄存整个应用Activity**/
    private final Stack<WeakReference<Activity>> activitys = new Stack<WeakReference<Activity>>();
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 内存泄露对象监测
     */
//	private RefWatcher refWatcher;

    /**
     * 对外提供Application Context
     *
     * @return
     */
    public static Context gainContext() {
        return instance;
    }

    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }

    /**
     * 初始化工作
     */
    protected void init() {
//		//图片加载器
//		mImageLoader = ToolImage.init(this);
//		//注册崩溃异常处理类
        AppCrashHandler.getInstance().register(this);
        try {
            //应用程序版本
            version = AppEnvironment.getVersionName();
            //设备ID
//			deviceId = AppEnvironment.DEVICE_ID;
            //获取渠道号
//			channelId = ToolChannel.gainChannel(this, ToolChannel.CHANNEL_KEY,"Ajava");

        } catch (Exception e) {
            Logger.e(TAG, "初始化设备ID、获取应用程序版本失败，原因：" + e.getMessage());
        }

        resetResources();

//		installLeakCanary(this);
    }

//	/**
//	 * 获取内存泄露监测对象
//	 * @param context
//	 * @return
//     */
//	public static RefWatcher getRefWatcher(Context context) {
//		MApplication application = (MApplication) context.getApplicationContext();
//		return application.refWatcher;
//	}
//
//	/**
//	 * 初始化内存泄露监控
//	 * @param context
//     */
//	protected void installLeakCanary(Application context){
//		if (LeakCanary.isInAnalyzerProcess(context)) {
//			// This process is dedicated to LeakCanary for heap analysis.
//			// You should not init your app in this process.
//			return;
//		}
//		refWatcher = LeakCanary.install(context);
//	}

    /**
     * 保证APP字体不受用户调整手机字体大小而影响，确保布局不会变形，或者sp使用dp替代
     */
    protected void resetResources() {
        Resources res = super.getResources();
        if (res != null) {
            android.content.res.Configuration cfg = res.getConfiguration();
            if (cfg != null && cfg.fontScale != 1.0f) {
                cfg.fontScale = 1.0f;
                res.updateConfiguration(cfg, res.getDisplayMetrics());
            }
        }
    }

//	/**
//	 * 获取ImageLoader
//	 * @return
//	 */
//	public ImageLoader getImageLoader(){
//		return mImageLoader;
//	}

    /**
     * 获取网络是否已连接
     *
     * @return
     */
    public static boolean isNetworkReady() {
        try {
            ConnectivityManager connectivity =
                    (ConnectivityManager) instance.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /*******************************************************Application数据操作API（开始）********************************************************/

    /**
     * 往Application放置数据（最大不允许超过5个）
     *
     * @param strKey   存放属性Key
     * @param strValue 数据对象
     */
    public static void assignData(String strKey, Object strValue) {
        if (gloableData.size() > 5) {
            throw new RuntimeException("超过允许最大数");
        }
        gloableData.put(strKey, strValue);
    }

    /**
     * 从Applcaiton中取数据
     *
     * @param strKey 存放数据Key
     * @return 对应Key的数据对象
     */
    public static Object gainData(String strKey) {
        return gloableData.get(strKey);
    }

    /*
     * 从Application中移除数据
     */
    public static void removeData(String key) {
        if (gloableData.containsKey(key)) gloableData.remove(key);
    }

    /*******************************************************Application数据操作API（结束）********************************************************/


    /*******************************************Application中存放的Activity操作（压栈/出栈）API（开始）*****************************************/

    /**
     * 将Activity压入Application栈
     *
     * @param task 将要压入栈的Activity对象
     */
    public void pushTask(WeakReference<Activity> task) {
        activitys.push(task);
    }

    /**
     * 将传入的Activity对象从栈中移除
     *
     * @param task
     */
    public void removeTask(WeakReference<Activity> task) {
        activitys.remove(task);
    }

    /**
     * 根据指定位置从栈中移除Activity
     *
     * @param taskIndex Activity栈索引
     */
    public void removeTask(int taskIndex) {
        if (activitys.size() > taskIndex)
            activitys.remove(taskIndex);
    }

    /**
     * 将栈中Activity移除至栈顶
     */
    public void removeToTop() {
        int end = activitys.size();
        int start = 1;
        for (int i = end - 1; i >= start; i--) {
            Activity mActivity = activitys.get(i).get();
            if (null != mActivity && !mActivity.isFinishing()) {
                mActivity.finish();
            }
        }
    }

    /**
     * 移除全部（用于整个应用退出）
     */
    public void removeAll() {
        //finish所有的Activity
        for (WeakReference<Activity> task : activitys) {
            Activity mActivity = task.get();
            if (null != mActivity && !mActivity.isFinishing()) {
                mActivity.finish();
            }
        }
    }

    /**
     * 退出整个APP，关闭所有activity/清除缓存等等
     */
    public void exit() {

    }

    /*******************************************Application中存放的Activity操作（压栈/出栈）API（结束）*****************************************/
}
