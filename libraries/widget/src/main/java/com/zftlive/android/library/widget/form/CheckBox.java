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
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;

import com.zftlive.android.library.widget.R;

/**
 * 自定义CheckBox,增加属性key/value
 * @author 曾繁添
 * @version 1.0
 *
 */
public class CheckBox extends AppCompatCheckBox {

	private String mKey;
	private String mValue;

	public CheckBox(Context context) {
		this(context,null);
	}
	
	public CheckBox(Context context, AttributeSet attrs) {
		this(context, attrs,android.R.attr.checkboxStyle);
	}
	
	public CheckBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		// 获取自定义属性和默认值
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,R.styleable.Widget_TextView);
		mKey = mTypedArray.getString(R.styleable.Widget_TextView_anl_key);
		mValue = mTypedArray.getString(R.styleable.Widget_TextView_anl_value);
		mTypedArray.recycle();
	}
	
	public String getKey(){
		return mKey;
	}
	
	public void setKey(String key){
		this.mKey = key;
	}
	
	public String getValue() {
		return mValue;
	}

	public void setValue(String value) {
		this.mValue = value;
	}

}
