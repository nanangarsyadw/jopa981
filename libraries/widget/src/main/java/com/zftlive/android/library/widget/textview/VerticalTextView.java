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

package com.zftlive.android.library.widget.textview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 竖着添加TextView方式实现竖排文字
 * @author 曾繁添
 * @version 1.0
 *
 */
public class VerticalTextView extends LinearLayout {

	private String text;
	private Context context;
	private int color;
	private int size;
	
	public VerticalTextView(Context context) {
		super(context);
		setOrientation(VERTICAL);
		this.context = context;
	}
	
	public VerticalTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(VERTICAL);
		this.context = context;
	}

	public void setText(String text) {
		this.text = text;
		addText();
	}

	private void addText() {
		removeAllViews();
		if (text != null) {
			char[] chara = text.toCharArray();
			for (int i = 0; i < chara.length; i++) {
				TextView item = new TextView(context);
				item.setTextColor(color);
				item.setText(chara[i]);
				if (size > 0) {
					item.setTextSize(size);
				}
				addView(item);
			}
		}
	}

	public void setTextColor(int color) {
		this.color = color;
	}

	public void setTextSize(int size) {
		this.size = size;
	}

}
