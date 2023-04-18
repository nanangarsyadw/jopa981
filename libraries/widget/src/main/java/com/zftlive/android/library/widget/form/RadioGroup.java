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

package com.zftlive.android.library.widget.form;

import android.content.Context;
import android.util.AttributeSet;

public class RadioGroup extends android.widget.RadioGroup {

	public RadioGroup(Context context) {
		super(context);
	}

	public RadioGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// 设置子控件的值
	public void setChildValue() {
		int n = this.getChildCount();
		for (int i = 0; i < n; i++) {
			final RadioButton radio = (RadioButton) this.getChildAt(i);
			if (radio.getValue().equals(this.mValue)) {
				radio.setChecked(true);
			} else {
				radio.setChecked(false);
			}
		}
	}

	// 获取子类的值
	public void getChildValue() {
		int n = this.getChildCount();
		for (int i = 0; i < n; i++) {
			RadioButton radio = (RadioButton) this.getChildAt(i);
			if (radio.isChecked()) {
				this.mValue = radio.getValue();
			}
		}
	}

	private String mValue;
	
	public void setValue(String value) {
		this.mValue = value;
		setChildValue();
	}

	public String getValue() {
		getChildValue();
		return this.mValue;
	}

}
