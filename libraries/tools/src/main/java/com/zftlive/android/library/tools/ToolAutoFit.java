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

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * 适配不同分辨率工具类
 * @author 曾繁添
 * @version 1.0
 */
public class ToolAutoFit extends ToolBase{

	// 遍历设置字体
	public static void changeViewSize(ViewGroup viewGroup, int screenWidth,int screenHeight) {// 传入Activity顶层Layout,屏幕宽,屏幕高
		int adjustFontSize = adjustFontSize(screenWidth, screenHeight);
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			View v = viewGroup.getChildAt(i);
			if (v instanceof ViewGroup) {
				changeViewSize((ViewGroup) v, screenWidth, screenHeight);
			} else if (v instanceof Button) {// 按钮加大这个一定要放在TextView上面，因为Button也继承了TextView
				((Button) v).setTextSize(adjustFontSize + 2);
			} else if (v instanceof TextView) {
				if (v.getId() == ToolResource.getIdId("title_text")) {// 顶部标题
					((TextView) v).setTextSize(adjustFontSize + 4);
				} else {
					((TextView) v).setTextSize(adjustFontSize);
				}
			}
		}
	}

	// 获取字体大小
	public static int adjustFontSize(int screenWidth, int screenHeight) {
		screenWidth = screenWidth > screenHeight ? screenWidth : screenHeight;
		/**
		 * 1. 在视图的 onsizechanged里获取视图宽度，一般情况下默认宽度是320，所以计算一个缩放比率 rate = (float)
		 * w/320 w是实际宽度 2.然后在设置字体尺寸时 paint.setTextSize((int)(8*rate));
		 * 8是在分辨率宽为320 下需要设置的字体大小 实际字体大小 = 默认字体大小 x rate
		 */
		int rate = (int) (5 * (float) screenWidth / 320); // 我自己测试这个倍数比较适合，当然你可以测试后再修改
		return rate < 15 ? 15 : rate; // 字体太小也不好看的
	}

}
