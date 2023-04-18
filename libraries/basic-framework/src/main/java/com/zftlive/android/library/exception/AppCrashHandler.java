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

package com.zftlive.android.library.exception;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Environment;

import com.zftlive.android.library.compat.AndroidPropCompat;
import com.zftlive.android.library.Config;
import com.zftlive.android.library.Logger;

import java.io.File;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * APP崩溃监控异常处理类
 *
 * @author 曾繁添
 * @version 1.0
 */
public class AppCrashHandler implements UncaughtExceptionHandler {

  private static final String TAG = AppCrashHandler.class.getSimpleName();
  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private static final SimpleDateFormat SIMPLE_DATE_FORMAT1 = new SimpleDateFormat("yyyyMMddHHmmss");
  private static final AppCrashHandler sMyCrashHandler = new AppCrashHandler();
  private UncaughtExceptionHandler mOldHandler;
  private Context mContext;

  public static AppCrashHandler getInstance() {
    return sMyCrashHandler;
  }

  public void register(Context context) {
    if (context != null) {
      mOldHandler = Thread.getDefaultUncaughtExceptionHandler();
      if (mOldHandler != this) {
        Thread.setDefaultUncaughtExceptionHandler(this);
      }
      mContext = context;
    }
  }

  @Override
  public void uncaughtException(Thread thread, Throwable ex) {
    Logger.e(TAG, "uncaughtException", ex);
    PrintWriter writer = null;
    try {
      Date date = new Date();
      String dateStr = SIMPLE_DATE_FORMAT1.format(date);

      File file = new File(Environment.getExternalStorageDirectory(), String.format(Config.SDCARD_CRASH_LOG_DIR + "CrashLog_%s_%s.log", dateStr, android.os.Process.myPid()));
      if (!file.getParentFile().exists()) {
        file.getParentFile().mkdirs();
      }

      if (!file.getParentFile().exists()) {
        file.getParentFile().mkdirs();
      }

      if (file.exists()) {
        file.delete();
      }

      writer = new PrintWriter(file);

      writer.println("Date:" + SIMPLE_DATE_FORMAT.format(date));
      writer.println("----------------------------------------System Infomation-----------------------------------");

      String packageName = mContext.getPackageName();
      writer.println("AppPkgName:" + packageName);
      try {
        PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(packageName, 0);
        writer.println("VersionCode:" + packageInfo.versionCode);
        writer.println("VersionName:" + packageInfo.versionName);
        writer.println("Debug:" + (0 != (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE)));
      } catch (Exception e) {
        writer.println("VersionCode:-1");
        writer.println("VersionName:null");
        writer.println("Debug:Unkown");
      }

      writer.println("PName:" + getProcessName());

      try {
        writer.println("imei:" + getIMEI(mContext));
      } catch (Exception e) {
      }

      writer.println("Board:" + AndroidPropCompat.get("ro.product.board", "unknown"));
      writer.println("ro.bootloader:" + AndroidPropCompat.get("ro.bootloader", "unknown"));
      writer.println("ro.product.brand:" + AndroidPropCompat.get("ro.product.brand", "unknown"));
      writer.println("ro.product.cpu.abi:" + AndroidPropCompat.get("ro.product.cpu.abi", "unknown"));
      writer.println("ro.product.cpu.abi2:" + AndroidPropCompat.get("ro.product.cpu.abi2", "unknown"));
      writer.println("ro.product.device:" + AndroidPropCompat.get("ro.product.device", "unknown"));
      writer.println("ro.build.display.id:" + AndroidPropCompat.get("ro.build.display.id", "unknown"));
      writer.println("ro.build.fingerprint:" + AndroidPropCompat.get("ro.build.fingerprint", "unknown"));
      writer.println("ro.hardware:" + AndroidPropCompat.get("ro.hardware", "unknown"));
      writer.println("ro.build.host:" + AndroidPropCompat.get("ro.build.host", "unknown"));
      writer.println("ro.build.id:" + AndroidPropCompat.get("ro.build.id", "unknown"));
      writer.println("ro.product.manufacturer:" + AndroidPropCompat.get("ro.product.manufacturer", "unknown"));
      writer.println("ro.product.model:" + AndroidPropCompat.get("ro.product.model", "unknown"));
      writer.println("ro.product.name:" + AndroidPropCompat.get("ro.product.name", "unknown"));
      writer.println("gsm.version.baseband:" + AndroidPropCompat.get("gsm.version.baseband", "unknown"));
      writer.println("ro.build.tags:" + AndroidPropCompat.get("ro.build.tags", "unknown"));
      writer.println("ro.build.type:" + AndroidPropCompat.get("ro.build.type", "unknown"));
      writer.println("ro.build.user:" + AndroidPropCompat.get("ro.build.user", "unknown"));
      writer.println("ro.build.version.codename:" + AndroidPropCompat.get("ro.build.version.codename", "unknown"));
      writer.println("ro.build.version.incremental:" + AndroidPropCompat.get("ro.build.version.incremental", "unknown"));
      writer.println("ro.build.version.release:" + AndroidPropCompat.get("ro.build.version.release", "unknown"));
      writer.println("ro.build.version.sdk:" + AndroidPropCompat.get("ro.build.version.sdk", "unknown"));
      writer.println("\n\n\n----------------------------------Exception---------------------------------------\n\n");
      writer.println("----------------------------Exception message:" + ex.getLocalizedMessage() + "\n");
      writer.println("----------------------------Exception StackTrace:");
      ex.printStackTrace(writer);
    } catch (Throwable e) {
      Logger.e(TAG, "记录uncaughtException", e);
    } finally {
      try {
        if (writer != null) {
          writer.flush();
          writer.close();
        }
      } catch (Exception e) {
      }

      if (mOldHandler != null) {
        mOldHandler.uncaughtException(thread, ex);
      }
    }
  }

  private String getIMEI(Context mContext) {
    return "test";
  }

  public String getProcessName() {
    ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
    List<RunningAppProcessInfo> infos = am.getRunningAppProcesses();
    for (RunningAppProcessInfo info : infos) {
      if (info.pid == android.os.Process.myPid()) {
        return info.processName;
      }
    }
    return null;
  }
}
