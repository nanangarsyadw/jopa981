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

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;


/***下拉框适配器***/
public class SpinnerAdapter extends ArrayAdapter<Option>{

	public SpinnerAdapter(Context context, int resource,List<Option> objects) {
		super(context, resource, objects);
	}

	public SpinnerAdapter(Context context, int resource,int textViewResourceId, List<Option> objects) {
		super(context, resource, textViewResourceId, objects);
	}

	public SpinnerAdapter(Context context, int resource, Option[] objects) {
		super(context, resource, objects);
	}
	
	public SpinnerAdapter(Context context, int resource,int textViewResourceId, Option[] objects) {
		super(context, resource, textViewResourceId, objects);
	}
}
