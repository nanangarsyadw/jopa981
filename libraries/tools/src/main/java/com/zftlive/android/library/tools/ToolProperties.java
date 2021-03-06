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

package com.zftlive.android.library.tools;

import java.io.InputStream;
import java.util.Properties;

import android.content.Context;

/**
 * 配置文件工具类
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public final class ToolProperties extends ToolBase {

	private static Properties property = new Properties();

	public static String readAssetsProp(Context mContext,String fileName, String key) {
		String value = "";
		try {
			InputStream in = mContext.getAssets().open(fileName);
			property.load(in);
			value = property.getProperty(key);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return value;
	}

	public static String readAssetsProp(Context context,String fileName, String key,String defaultValue) {
		String value = "";
		try {
			InputStream in = context.getAssets().open(fileName);
			property.load(in);
			value = property.getProperty(key, defaultValue);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return value;
	}
}
