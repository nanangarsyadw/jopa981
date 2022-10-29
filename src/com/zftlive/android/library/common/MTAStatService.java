package com.zftlive.android.library.common;

import java.util.Map;
import java.util.Properties;

import android.content.Context;
import android.util.Log;

import com.tencent.stat.MtaSDkException;
import com.tencent.stat.StatAppMonitor;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;
import com.tencent.stat.common.StatConstants;

/**
 * MTA统计相关共通方法,点击次数、耗时时长、网速测试等相关API
 * @author 曾繁添
 * @version 1.0
 *
 */
public class MTAStatService {
	
	/**日志输出标志**/
	protected static final String TAG = MTAStatService.class.getSimpleName();
	
	/**
	 * 初始化MTA云监控配置
	 */
	public static void initMTA(Context mContext,String channel,String appkey){
		
		//设置渠道号
		StatConfig.setInstallChannel(channel);
		//设置APPKEY
		StatConfig.setAppKey(appkey);
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
		
		//上报native层异常
		StatConfig.initNativeCrashReport(mContext, null);
		// 根据情况，决定是否开启MTA对app未处理异常的捕获
		StatConfig.setAutoExceptionCaught(true);
		
		// 数据上报策略
		/**
		 * 6种上报策略
			1 INSTANT 实时发送，app每产生一条消息都会发送到服务器。
			2 ONLY_WIFI 只在wifi状态下发送，非wifi情况缓存到本地。
			3 BATCH 批量发送，默认当消息数量达到30条时发送一次。
			4 APP_LAUNCH 只在启动时发送，本次产生的所有数据在下次启动时发送。
			5 DEVELOPER 开发者模式，只在app调用void commitEvents(Context)时发送，否则缓存消息到本地。
			6 PERIOD 间隔一段时间发送，每隔一段时间一次性发送到服务器。
			
			SDK默认为APP_LAUNCH+wifi下实时上报，对于响应要求比较高的应用，比如竞技类游戏，可关闭wifi实时上报，并选择APP_LAUNCH或PERIOD上报策略。
			考虑到wifi上报数据的代价比较小，为了更及时获得用户数据，SDK默认在WIFI网络下实时发送数据。可以调用下面的接口禁用此功能（在wifi条件下仍使用原定策略）。
			void StatConfig.setEnableSmartReporting (boolean isEnable)
			通过在Web界面配置，开发者可以在线更新上报策略，替换app内原有的策略，替换后的策略立即生效并存储在本地，app后续启动时会自动加载该策略。
			上面3种方式的优先级顺序：wifi条件下智能实时发送>web在线配置>本地默认。 
		 */
		//StatConfig.setStatSendStrategy(StatReportStrategy.PERIOD);
		
		try {
			//开启统计
			StatService.startStatService(mContext,appkey,StatConstants.VERSION);
		} catch (MtaSDkException e) {
			Log.e(TAG, "初始化MTA统计失败，原因："+e.getMessage());
		}
	}
	
	/**
	 * [次数统计] 带任意参数的事件
	 * @param ctx 页面的设备上下文
	 * @param event_id 事件标识
	 * @param args 事件参数
	 */
	public static void trackCustomKVEvent(Context ctx, String event_id, String args){
		try {
			StatService.trackCustomEvent(ctx, event_id, args);
		} catch (Exception e) {
			Log.e(TAG, "trackCustomKVEvent-->"+e.getMessage());
		}
	}
	
	/**
	 * [次数统计] 带key-value参数的事件
	 * @param ctx 页面的设备上下文
	 * @param event_id 事件标识
	 * @param properties Key-Value参数对，key和value都是String类型
	 */
	public static void trackCustomKVEvent(Context ctx, String event_id,Properties properties){
		try {
			StatService.trackCustomKVEvent(ctx, event_id, properties);
		} catch (Exception e) {
			Log.e(TAG, "trackCustomKVEvent-->"+e.getMessage());
		}
	}
	
	/**
	 * [次数统计] 带key-value参数的事件
	 * @param ctx 页面的设备上下文
	 * @param event_id 事件标识
	 * @param propKey 事件参数key
	 * @param propKey 事件参数value
	 */
	public static void trackCustomKVEvent(Context ctx, String event_id,String propKey,String propValue){
		try {
			Properties properties = new Properties();
			properties.setProperty(propKey, propValue);
			StatService.trackCustomKVEvent(ctx, event_id, properties);
		} catch (Exception e) {
			Log.e(TAG, "trackCustomKVEvent-->"+e.getMessage());
		}
	}
	
	/**
	 * [时长统计] 带key-value参数的事件
	 * 可以指定事件的开始和结束时间，来上报一个带有统计时长的事件
	 * 注意：trackCustomBeginKVEvent 和trackCustomEndKVEvent 必须成对出现
	 * @param ctx 页面的设备上下文
	 * @param event_id 事件标识
	 * @param propKey 事件参数key
	 * @param propKey 事件参数value
	 */
	public static void trackCustomBeginKVEvent(Context ctx, String event_id, String propKey,String propValue){
		try {
			Properties properties = new Properties();
			properties.setProperty(propKey, propValue);
			
			StatService.trackCustomBeginKVEvent(ctx, event_id, properties);
		} catch (Exception e) {
			Log.e(TAG, "trackCustomBeginKVEvent-->"+e.getMessage());
		} 
	}
	
	/**
	 * [时长统计] 带key-value参数的事件
	 * 可以指定事件的开始和结束时间，来上报一个带有统计时长的事件
	 * 注意：trackCustomBeginKVEvent 和trackCustomEndKVEvent 必须成对出现
	 * @param ctx 页面的设备上下文
	 * @param event_id 事件标识
	 * @param properties Key-Value参数对，key和value都是String类型
	 */
	public static void trackCustomBeginKVEvent(Context ctx, String event_id, Properties properties){
		try {
			StatService.trackCustomBeginKVEvent(ctx, event_id, properties);
		} catch (Exception e) {
			Log.e(TAG, "trackCustomBeginKVEvent-->"+e.getMessage());
		} 
	}
	
	/**
	 * [时长统计] 带key-value参数的事件
	 * 可以指定事件的开始和结束时间，来上报一个带有统计时长的事件
	 * 注意：trackCustomBeginKVEvent 和trackCustomEndKVEvent 必须成对出现
	 * @param ctx 页面的设备上下文
	 * @param event_id 事件标识
	 * @param propKey 事件参数key
	 * @param propKey 事件参数value
	 */
	public static void trackCustomEndKVEvent(Context ctx, String event_id,String propKey,String propValue){
		try {
			Properties properties = new Properties();
			properties.setProperty(propKey, propValue);
			StatService.trackCustomEndKVEvent(ctx, event_id, properties);
		} catch (Exception e) {
			Log.e(TAG, "trackCustomEndKVEvent-->"+e.getMessage());
		}
	}
	
	/**
	 * [时长统计] 带key-value参数的事件
	 * 可以指定事件的开始和结束时间，来上报一个带有统计时长的事件
	 * 注意：trackCustomBeginKVEvent 和trackCustomEndKVEvent 必须成对出现
	 * @param ctx 页面的设备上下文
	 * @param event_id 事件标识
	 * @param properties Key-Value参数对，key和value都是String类型
	 */
	public static void trackCustomEndKVEvent(Context ctx, String event_id,Properties properties){
		try {
			StatService.trackCustomEndKVEvent(ctx, event_id, properties);
		} catch (Exception e) {
			Log.e(TAG, "trackCustomEndKVEvent-->"+e.getMessage());
		}
	}
	
	/**
	 * 接口测试
	 * 统计应用对某个外部接口（特别是网络类的接口，如连接、登陆、下载等）的调用情况。
	 * 当开发者用到某个外部接口，可调用该函数将一些指标进行上报，MTA将统计出每个接口的调用情况，并在接口可用性发生变化时进行告警通知; 
	 * 对于调用量很大的接口，也可以采样上报，云监控统计将根据sampling参数在展现页面进行数量的还原
  		// 新建监控接口对象
		StatAppMonitor monitor = new StatAppMonitor("ping:www.qq.com");
		// 接口开始执行
		String ip = "www.qq.com";
		Runtime run = Runtime.getRuntime();
		java.lang.Process proc = null;
		try {
			String str = "ping -c 3 -i 0.2 -W 1 " + ip;
			long starttime = System.currentTimeMillis();
			proc = run.exec(str);
			int retCode = proc.waitFor();
			long difftime = System.currentTimeMillis() - starttime;
			// 设置接口耗时
			monitor.setMillisecondsConsume(difftime);
			// 设置接口返回码
			monitor.setReturnCode(retCode);
			// 设置请求包大小，若有的话
			monitor.setReqSize(1000);
			// 设置响应包大小，若有的话
			monitor.setRespSize(2000);
			// 设置抽样率，默认为1，表示100%。如果是50%，则填2(100/50)，如果是25%，则填4(100/25)，以此类推。
			// monitor.setSampling(2);
			if (retCode == 0) {
				Log.d("StatAppMonitor", "ping连接成功");
				// 标记为成功
				monitor.setResultType(StatAppMonitor.SUCCESS_RESULT_TYPE);
			} else {
				Log.d("StatAppMonitor", "ping测试失败");
				// 标记为逻辑失败，可能由网络未连接等原因引起的，但对于业务来说不是致命的，是可容忍的
				monitor.setResultType(StatAppMonitor.LOGIC_FAILURE_RESULT_TYPE);
			}
		} catch (Exception e) {
			Log.e(TAG, "StatAppMonitor-->"+e.getMessage());
			// 接口调用出现异常，致命的，标识为失败
			monitor.setResultType(StatAppMonitor.FAILURE_RESULT_TYPE);
		} finally {
			proc.destroy();
		}
	 * @param ctx
	 * @param monitor
	 */
	public static void reportAppMonitorStat(Context ctx, StatAppMonitor monitor){
		try {
			// 上报接口监控的信息
			StatService.reportAppMonitorStat(ctx, monitor);
		} catch (Exception e) {
			Log.e(TAG, "trackCustomEndKVEvent-->"+e.getMessage());
		}
	}
	
	/**
	 * 前台指定域名测速
	 * 开发者在前台腾讯移动分析前台网速监控--测速地址管理页面配置待监控的域名和端口列表，由服务器下发到SDK，然后在app需要测速的地方调用以下接口，便会对配置的所有域名进行测速监控。
	 * 通常SDK在app启动时会主动测速，如果要在特定的地方测速，需要开发者主动调用本接口
	 * 注意：主动调用本接口产生网络I/O，可能会影响用户体验，请慎重使用
	 * @param ctx
	 */
	public static void testSpeed(Context ctx){
		try {
			StatService.testSpeed(ctx);
		} catch (Exception e) {
			Log.e(TAG, "testSpeed-->"+e.getMessage());
		}
	}
	
	/**
	 * 本地指定域名端口测速
	 * 在APP本地指定测速的域名和列表，由开发者决定何时进行测速
	 * @param ctx 页面的设备上下文
	 * @param domainMap 待测速的域名和端口列表
	 */
	public static void testSpeed(Context ctx,Map<String, Integer> domainMap){
		try {
			StatService.testSpeed(ctx, domainMap);
		} catch (Exception e) {
			Log.e(TAG, "testSpeed-->"+e.getMessage());
		}
	}
	
	/**
	 * 上报QQ号
	 * @param ctx 页面的设备上下文
	 * @param qq QQ号码
	 */
	public static void reportQQ(Context ctx, String qq){
		try {
			StatService.reportQQ(ctx, qq);
		} catch (Exception e) {
			Log.e(TAG, "reportQQ-->"+e.getMessage());
		}
	}
	
	/**
	 * 主动上报APP异常
	 * @param ctx 上下文
	 * @param exp 异常
	 */
	public static void reportException (Context ctx, Exception exp){
		try {
			StatService.reportException(ctx, exp);
		} catch (Exception e) {
			Log.e(TAG, "reportError-->"+e.getMessage());
		}
	}
	
	/**
	 * 主动上报APP错误
	 * @param ctx 上下文
	 * @param error 异常
	 */
	public static void reportError (Context ctx, String error){
		try {
			StatService.reportError(ctx, error);
		} catch (Exception e) {
			Log.e(TAG, "reportError-->"+e.getMessage());
		}
	}
}
