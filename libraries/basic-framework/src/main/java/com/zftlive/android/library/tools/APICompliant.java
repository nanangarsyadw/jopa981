package com.zftlive.android.library.tools;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;

/**
 * 低版本兼容API收集
 * @author 曾繁添
 * @version 1.0
 *
 */
public class APICompliant {

	/**
	 * 设置背景兼容API
	 * @param view
	 * @param background
	 */
	public static void setBackground(View view, Drawable background) {
		if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
			view.setBackground(background);
		} else {
			view.setBackgroundDrawable(background);
		}
	}
	
	/**
	 * activity.isDestroyed()的API兼容方法
	 * @param a
	 * @return API 17以下为defaultValue，其他情况返回a.isDestroyed()的返回结果
	 */
	public static boolean isDestroyed(Activity a, boolean defaultValue) {
		try{
			if (VERSION.SDK_INT >= 17) {
				return a.isDestroyed();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	    return  defaultValue;
	}

	/**
	 * view.isAttachedToWindow()的API兼容方法
	 * @param v
	 * @param defaultValue
	 * @return
	 */
	public static boolean isAttachedToWindow(View v, boolean defaultValue) {
	    if (VERSION.SDK_INT >= 19) {
	        return v.isAttachedToWindow();
        }
	    
	    return defaultValue;
	}
}
