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
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;

import com.zftlive.android.library.widget.R;

public class SingleSpinner extends AppCompatSpinner {

	private String mKey;
	
	public SingleSpinner(Context context) {
		this(context,null);
	}
	
	public SingleSpinner(Context context, AttributeSet attrs) {
		this(context, attrs,android.R.attr.spinnerStyle);
	}
	
	public SingleSpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//获取自定义属性和默认值
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,R.styleable.Widget_TextView);
		mKey = mTypedArray.getString(R.styleable.Widget_TextView_anl_key);
		mTypedArray.recycle();
	}

	public String getKey(){
		return mKey;
	}
	
	public void setKey(String key){
		this.mKey = key;
	}
	
	/**
	 * 获取选中的选项
	 */
	public Option getSelectedItem(){
		return (Option)super.getSelectedItem();
	}
	
	/**
	 * 获取选中的选项索引
	 */
	public int getSelectedIndex(){
		return super.getSelectedItemPosition();
	}
	
	/**
	 * 获取选中的选项编码value
	 */
	public String getSelectedValue(){
		return getSelectedItem().getValue();
	}
	
	/**
	 * 获取选中的选项编码显示文本
	 */
	public String getSelectedLabel(){
		return getSelectedItem().getLabel();
	}
	
}
