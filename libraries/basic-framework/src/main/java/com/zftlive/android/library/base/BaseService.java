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

package com.zftlive.android.library.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 *  android 系统中的四大组件之一Service基类<br>
 *  Android Service的生命周期
 * @author 曾繁添
 * @version 1.0
 *
 */
public abstract class BaseService extends Service implements IBaseConstant {

	/**日志输出标志**/
	protected final String TAG = this.getClass().getSimpleName();

	@Override
	public void onCreate() {
		Log.d(TAG, "BaseService-->onCreate()");
		super.onCreate();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		Log.d(TAG, "BaseService-->onStart()");
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "BaseService-->onStartCommand()");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "BaseService-->onDestroy()");
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "BaseService-->onBind()");
		return null;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(TAG, "BaseService-->onUnbind()");
		return super.onUnbind(intent);
	}
}
