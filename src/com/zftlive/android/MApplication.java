package com.zftlive.android;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.http.RequestManager;
import com.android.volley.toolbox.ImageLoader;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.stat.MtaSDkException;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;
import com.zftlive.android.tools.ToolChannel;
import com.zftlive.android.tools.ToolData;
import com.zftlive.android.tools.ToolNetwork;
import com.zftlive.android.view.imageindicator.NetworkImageCache;

/**
 * 整个应用程序Applicaiton
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class MApplication extends Application {

	/**对外提供整个应用生命周期的Context**/
	private static Context instance;
	/**整个应用全局可访问数据集合**/
	private static Map<String, Object> gloableData = new HashMap<String, Object>();
	/***volley提供的异步图片Loader**/
	private static ImageLoader mImageLoader = null;
	/***volley提供的异步图片缓存**/
	private final NetworkImageCache imageCacheMap = new NetworkImageCache();
	/***寄存整个应用Activity**/
	private final Stack<WeakReference<Activity>> activitys = new Stack<WeakReference<Activity>>();
	/**日志输出标志**/
	protected final String TAG = this.getClass().getSimpleName();
	/**
	 * 渠道号
	 */
	private static String channelId = "Ajava";
	
	/**
	 * 设备号
	 */
	private static String deveiceId = "未知";
	
	/**
	 * MTA APPKEY
	 */
	public static final String MAT_APPKEY = "A1D5J6XB1XMY";
	
	
	/**
	 * 对外提供Application Context
	 * @return
	 */
	public static Context gainContext() {
		return instance;
	}

	public void onCreate() {
		super.onCreate();
		instance = this;
		
		//初始化请求队列
		RequestManager.getInstance().init(MApplication.this);
		mImageLoader = new ImageLoader(RequestManager.getInstance().getRequestQueue(), imageCacheMap);
		//初始化图片加载器
		initImageLoader(getApplicationContext());
		
		initMTA();
	}

	/**
	 * 获取网络是否已连接
	 * @return
	 */
	public static boolean isNetworkReady(){
		return ToolNetwork.getInstance().init(instance).isConnected();
	}
	
	/**
	 * 获取图片异步加载器
	 * @return ImageLoader
	 */ 
	public static ImageLoader getImageLoader() {
		return mImageLoader;
	}

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.discCacheSize(50 * 1024 * 1024) // 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();
		// Initialize ImageLoader with configuration.
		com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);
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
		String strManifestChannel = ToolData.gainMetaData(gainContext(), MApplication.class, "InstallChannel");
		if(!TextUtils.isEmpty(strManifestChannel)){
			channelId = strManifestChannel;
			return channelId;
		}
		
		return channelId;
	}
	
	/**
	 * 获取设备号
	 * @return
	 */
	public static String gainDeviceId(){
		return deveiceId == null?"未知":deveiceId;
	}
	
	/**
	 * 初始化MTA云监控配置
	 */
	private void initMTA(){
		
		// 根据情况，决定是否开启MTA对app未处理异常的捕获
		StatConfig.setAutoExceptionCaught(true);
		//设置渠道号
		StatConfig.setInstallChannel(gainChannelId());
		//设置APPKEY
		StatConfig.setAppKey(this,MAT_APPKEY);
		//设置统计功能开关（默认为true）
		StatConfig.setEnableStatService(true);
		//设置session内产生的消息数量（默认为0，即无限制）
		StatConfig.setMaxSessionStatReportCount(0);
		//设置每天/每个进程时间产生的会话数量（默认为20）
		StatConfig.setMaxDaySessionNumbers (20);
		//设置单个事件最大长度（默认为4k，单位：bytes）
		StatConfig.setMaxReportEventLength (4 * 1024);
		//用户自定义时间类型事件的最大并行数量（默认1024）
		StatConfig.setMaxParallelTimmingEvents(24);
		//消息失败重发次数（默认3）
		StatConfig.setMaxSendRetryCount(3);
		//会话时长（默认30000ms，30000ms回到应用的用户视为同一次会话）
		StatConfig.setSessionTimoutMillis(30000);
		
		/***数据上报相关的设置(开始)****/
		//设置最大缓存未发送消息个数（默认1024）
		StatConfig.setMaxStoreEventCount(1024);
		//缓存消息的数量超过阈值时，最早的消息会被丢弃。（默认30）
		StatConfig.setMaxBatchReportCount(30);
		//（仅在发送策略为PERIOD时有效）设置间隔时间（默认为24*60，即1天）
		StatConfig.setSendPeriodMinutes(24*60);
		//开启SDK LogCat开关（默认false）
		StatConfig.setDebugEnable(true);
		/***数据上报相关的设置(结束)****/
		
		//开启统计
		try {
			StatService.startStatService(this,StatConfig.getAppKey(this),com.tencent.stat.common.StatConstants.VERSION);
		} catch (MtaSDkException e) {
			Log.e(TAG, "初始化MTA统计失败，原因："+e.getMessage());
		}
	}
	
	/*******************************************************Application数据操作API（开始）********************************************************/
	
	/**
	 * 往Application放置数据（最大不允许超过5个）
	 * @param strKey 存放属性Key
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
	 * @param strKey 存放数据Key
	 * @return 对应Key的数据对象
	 */
	public static Object gainData(String strKey) {
		return gloableData.get(strKey);
	}
	
	/*
	 * 从Application中移除数据
	 */
	public static void removeData(String key){
		if(gloableData.containsKey(key)) gloableData.remove(key);
	}

	/*******************************************************Application数据操作API（结束）********************************************************/
	
	
	/*******************************************Application中存放的Activity操作（压栈/出栈）API（开始）*****************************************/
	
	/**
	 * 将Activity压入Application栈
	 * @param task 将要压入栈的Activity对象
	 */
	public  void pushTask(WeakReference<Activity> task) {
		activitys.push(task);
	}

	/**
	 * 将传入的Activity对象从栈中移除
	 * @param task
	 */
	public  void removeTask(WeakReference<Activity> task) {
		activitys.remove(task);
	}

	/**
	 * 根据指定位置从栈中移除Activity
	 * @param taskIndex Activity栈索引
	 */
	public  void removeTask(int taskIndex) {
		if (activitys.size() > taskIndex)
			activitys.remove(taskIndex);
	}

	/**
	 * 将栈中Activity移除至栈顶
	 */
	public  void removeToTop() {
		int end = activitys.size();
		int start = 1;
		for (int i = end - 1; i >= start; i--) {
			if (!activitys.get(i).get().isFinishing()) {     
				activitys.get(i).get().finish(); 
		    }
		}
	}

	/**
	 * 移除全部（用于整个应用退出）
	 */
	public  void removeAll() {
		//finish所有的Activity
		for (WeakReference<Activity> task : activitys) {
			if (!task.get().isFinishing()) {     
				task.get().finish(); 
		    }  
		}
	}
	
	/*******************************************Application中存放的Activity操作（压栈/出栈）API（结束）*****************************************/
}
