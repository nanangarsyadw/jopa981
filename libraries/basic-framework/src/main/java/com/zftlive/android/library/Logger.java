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

import android.util.Log;

/**
 * 日志工具类
 * @author 曾繁添
 * @version 1.0
 *
 */
public class Logger {
	
	private static final String TAG = Logger.class.getSimpleName();
	
	/**
	 * 上线后关闭log
	 */
	private static Boolean DEBUG = true;

	/**
	 * 控制是否关闭日志输出
	 * @param isDebug
	 */
	public static void isDebug(boolean isDebug){
		DEBUG = isDebug;
	}
	
	public static void d(String tag, String msg) {
		if (DEBUG) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.d(TAG, tag + " : " + msg);
		}
	}

	public static void d(String tag, String msg, Throwable error) {
		if (DEBUG) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.d(TAG, tag + " : " + msg, error);
		}
	}

	public static void i(String tag, String msg) {
		if (DEBUG) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.i(TAG, tag + " : " + msg);
		}
	}

	public static void i(String tag, String msg, Throwable error) {
		if (DEBUG) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.i(TAG, tag + " : " + msg, error);
		}
	}

	public static void w(String tag, String msg) {
		if (DEBUG) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.w(TAG, tag + " : " + msg);
		}
	}

	public static void w(String tag, String msg, Throwable error) {
		if (DEBUG) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.w(TAG, tag + " : " + msg, error);
		}
	}

	public static void e(String tag, String msg) {
		if (DEBUG) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.e(TAG, tag + " : " + msg);
		}
	}

	public static void e(String tag, String msg, Throwable error) {
		if (DEBUG) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.e(TAG, tag + " : " + msg, error);
		}
	}
}
