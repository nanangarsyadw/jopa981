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

package com.zftlive.android.sample.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.zftlive.android.R;
import com.zftlive.android.library.Alert;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.model.DTO;
import com.zftlive.android.library.widget.form.Option;
import com.zftlive.android.library.widget.form.FormHelper;
import com.zftlive.android.library.widget.form.SingleSpinner;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动获取表单示例
 * <per>
 * 自动获取输入框、单选框、下拉框表单元素值：
 * 1、使用com.zftlive.android.view.SingleSpinner/RadioButton/CheckBox自定义控件
 * 2、设置android:tag为封装表单键值对的key
 * 3、声明命名空间xmlns:custom="http://schemas.android.com/apk/res/com.zftlive.android"，并追加自定义属性custom:value="male"
 * 4、如下代码即可获取表单数据，复选框选项结果已##连接				
 * 		DTO<String, Object> formData = new DTO<String, Object>();
 * 		formData = ToolData.gainForm((RelativeLayout) findViewById(R.id.frame_root),formData);
 * </per>
 * 
 * @author 曾繁添
 * @version 1.0
 *
 */
public class AutoGainFormActivity extends CommonActivity {
	private SingleSpinner sp_school;
	private Button btn_login;

	@Override
	public int bindLayout() {
		return R.layout.activity_auto_gain_form;
	}
	
	@Override
	public void initParams(Bundle parms) {

	}

	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {
		btn_login = (Button)findViewById(R.id.btn_login);
		sp_school = (SingleSpinner) findViewById(R.id.sp_school);
		List<Option> data = new ArrayList<Option>();
		data.add(new Option("tjdx","天津大学"));
		data.add(new Option("nkdx","南开大学"));
		data.add(new Option("bjdx","北京大学"));
		data.add(new Option("qhdx","清华大学"));
//		SpinnerAdapter mSpinnerAdapter = new SpinnerAdapter(this, R.drawable.view_spinner_drop_list_hover, data);
//		mSpinnerAdapter.setDropDownViewResource(R.drawable.view_spinner_drop_list_ys);
//		sp_school.setAdapter(mSpinnerAdapter);
		
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.AutoGainFormActivity);
//      ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
		mWindowTitle.initBackTitleBar(strCenterTitle);
	}


	@Override
	public void doBusiness(Context mContext) {
		btn_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DTO<String, Object> formData = new DTO<String, Object>();
				formData = (DTO<String, Object>)FormHelper.gainForm((RelativeLayout) findViewById(R.id.frame_root),formData);

				Alert.dialog(AutoGainFormActivity.this,"自动获取表单数据结果", formData.toString(),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,int which) {
								dialog.dismiss();
							}
						}, 
						null);
			}
		});
	}
}
