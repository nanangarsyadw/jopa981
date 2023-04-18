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

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

/**
 * 自定义字体工具类
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ToolFont extends ToolBase{
	
	private final static String TAG = ToolFont.class.getSimpleName();
	
	/**
	 * 从assets目录创建自定义字体样式
	 * @param mContext 上下文
	 * @param fontFileName assets目录下的字体文件名称
	 * @return
	 */
	public static Typeface createTypeface(Context mContext,String fontFileName){
		Typeface type = null;
		try {
			type = Typeface.createFromAsset(mContext.getAssets(), fontFileName);
		} catch (Exception e) {
			Log.e(TAG, "创建字体失败，原因：" + e.getMessage());
		}
		return type;
	}
	
	/**
	 * 给传递的目标控件设置自定义字体样式
	 * @param mContext 上下文
	 * @param views 需要设置字体的控件
	 */
	public static void applyFontStyle(Context mContext,Typeface style,TextView... views){
		if(null == views) return;
		
		if(null != views && views.length > 0 ){
			for (TextView view : views) {
				view.setTypeface(style);
			}
		}
	}
}
