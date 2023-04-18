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

package com.zftlive.android.sample.soap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zftlive.android.R;
import com.zftlive.android.library.Alert;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.network.ToolSOAP;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

/**
 * 调用WebService接口获取天气信息Activity
 * @author 曾繁添
 * @version 1.0
 *
 */
public class WeatherActivity extends CommonActivity {

	private TextView mTextWeather;

	@Override
	public int bindLayout() {
		return R.layout.activity_soap_weather;
	}

	@Override
	public void initParams(Bundle parms) {
		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {
		mTextWeather = (TextView) findViewById(R.id.weather);
		//初始化带返回按钮的标题栏
//		ActionBarManager.initBackTitle(getContext(), getActionBar(), "天气信息");
		mWindowTitle.initBackTitleBar("天气信息");
	}

	@Override
	public void doBusiness(final Context mContext) {
		//等待对话框
		Alert.loading(getContext(), "数据加载中...");
		
		HashMap<String, String> properties = new HashMap<String, String>();
		properties.put("theCityName", String.valueOf(getOperation().getParameter("city")));
		
		ToolSOAP.callService(ProviceActivity.WEB_SERVER_URL,ProviceActivity.NAME_SPACE,"getWeatherbyCityName", properties, new ToolSOAP.WebServiceCallBack() {
			
			@Override
			public void onSucced(SoapObject result) {
				//关闭等待对话框
				Alert.closeLoading();
				if(result != null){
					SoapObject detail = (SoapObject) result.getProperty("getWeatherbyCityNameResult");
					StringBuilder sb = new StringBuilder();
					for(int i=0; i<detail.getPropertyCount(); i++){
						sb.append(detail.getProperty(i)).append("\r\n");
					}
					mTextWeather.setText(sb.toString());
				}else{
					Alert.toastShort(mContext, "呼叫WebService-->getWeatherbyCityName失败");
				}
			}

			@Override
			public void onFailure(String result) {
				//关闭等待对话框
				Alert.closeLoading();

				Alert.toastShort(mContext, "呼叫WebService-->getWeatherbyCityName失败，原因："+result);
			}
		});
	}
}
