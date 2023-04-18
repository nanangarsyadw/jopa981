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

import java.io.File;

/**
 * 配置信息
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class Config {

  /**
   * 是否开启debug模式
   */
  private static boolean DEBUG = true;

  /**
   * 设置debug模式
   * @param isDebug
   */
  public static void isDebug(boolean isDebug){
    DEBUG = isDebug;
  }

  /**
   *日志文件大小,默认8MB
   */
  public static final int LOG_FILE_MAX_SIZE = 1024 * 1024 * 8; //8MB

  /**
   * 插件日志目录
   */
  public static final String SDCARD_PLUGIN_LOG_DIR = "zftlive";

  /**
   * 插件日志目录
   */
  public static final String SDCARD_LOG_DIR = SDCARD_PLUGIN_LOG_DIR + File.separator + "Log" + File.separator;

  /**
   * 插件崩溃日志目录
   */
  public static final String SDCARD_CRASH_LOG_DIR = SDCARD_PLUGIN_LOG_DIR + File.separator + "CrashLog" + File.separator;

}
