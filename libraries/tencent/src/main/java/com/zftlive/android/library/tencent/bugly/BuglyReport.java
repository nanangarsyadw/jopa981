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

package com.zftlive.android.library.tencent.bugly;

import android.content.Context;
import android.webkit.WebView;

import com.tencent.bugly.crashreport.CrashReport;

/**
 *
 * bugly日志上报工具类
 *
 * 相关配置可以在代码中配置，调用相关api，也可以在AndroidManifex.xml中application配置：
 * <per>
 *          <!--bugly集成参数配置（开始） -->
 *           <!-- 配置APP ID -->
 *           <meta-data
 *           android:name="BUGLY_APPID"
 *           android:value="" />
 *           <!-- 配置APP版本号 -->
 *           <meta-data
 *           android:name="BUGLY_APP_VERSION"
 *           android:value="1.0" />
 *           <!-- 配置APP渠道号 -->
 *           <meta-data
 *           android:name="BUGLY_APP_CHANNEL"
 *           android:value="" />
 *           <!-- 配置Bugly调试模式（true或者false）-->
 *           <meta-data
 *           android:name="BUGLY_ENABLE_DEBUG"
 *           android:value="" />
 *          <!--bugly集成参数配置（结束） -->
 * </per>
 * 最终以代码配置为基准
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class BuglyReport {

    /**
     * 初始化Bugly错误上报，默认关闭debug模式
     *
     * @param mContext 上下文
     * @param appId bugly注册的appId
     */
    public static void init(Context mContext,String appId){
        init(mContext, appId, false);
    }

    /**
     * 初始化Bugly错误上报
     *
     * @param mContext 上下文
     * @param appId bugly注册的appId
     * @param isDebug 是否开启debug模式
     */
    public static void init(Context mContext,String appId,boolean isDebug){
        CrashReport.initCrashReport(mContext, appId, isDebug);
    }

    /**
     * 初始化Bugly错误上报,默认关闭debug模式
     *
     * @param mContext 上下文
     * @param appId bugly注册的appId
     * @param channel 渠道号
     * @param appVersion 应用版本号
     */
    public static void init(Context mContext,String appId,String channel,String appVersion){
        init(mContext, appId,false,channel, appVersion, 10 * 1000);
    }

    /**
     * 初始化Bugly错误上报
     *
     * @param mContext 上下文
     * @param appId bugly注册的appId
     * @param isDebug 是否开启debug模式
     * @param channel 渠道号
     * @param appVersion 应用版本号
     */
    public static void init(Context mContext,String appId,boolean isDebug,String channel,String appVersion){
        init(mContext, appId,isDebug,channel, appVersion, 10 * 1000);
    }

    /**
     * 初始化Bugly错误上报
     *
     * @param mContext 上下文
     * @param appId bugly注册的appId
     * @param isDebug 是否开启debug模式
     * @param channel 渠道号
     * @param appVersion 应用版本号
     * @param reportDelay 默认10s
     */
    public static void init(Context mContext,String appId,boolean isDebug,String channel,String appVersion,long reportDelay){
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(mContext);
        strategy.setAppChannel(channel);
        strategy.setAppVersion(appVersion);
        strategy.setAppPackageName(mContext.getPackageName());
        strategy.setAppReportDelay(reportDelay);
        CrashReport.initCrashReport(mContext, appId, isDebug, strategy);
    }

    /**
     * 自定义标签，用于标明App的某个“场景”
     * 在发生Crash时会显示该Crash所在的“场景”，以最后设置的标签为准，标签id需大于0
     *
     * @param mContext 上下文
     * @param tagId bugly后台添加的标签id
     */
    public static void setUserSceneTag(Context mContext,int tagId){
        CrashReport.setUserSceneTag(mContext, tagId);
    }

    /**
     * 自定义Map参数可以保存发生Crash时的一些自定义的环境信息<br>
     * 注意：最多可以有9对自定义的key-value（超过则添加失败），key限长50字节、value限长200字节，过长截断
     * @param mContext
     * @param key
     * @param value
     */
    public static void addUserData(Context mContext,String key,String value){
        CrashReport.putUserData(mContext, key, value);
    }

    /**
     * Javascript的异常捕获,在WebChromeClient的onProgressChanged函数中调用接口中调用
     *
     * @param mWebview 指定被监控的webView
     */
    public static void catchJSException(WebView mWebview){
        //是否自动注入Bugly.js文件,自动集成true
        CrashReport.setJavascriptMonitor(mWebview, true);
    }

    /**
     * 主动上报异常，在需要的catch块中调用
     * @param e
     */
    public static void postCatchedException(Throwable e){
        CrashReport.postCatchedException(e);
    }
}
