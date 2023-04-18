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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zftlive.android.R;
import com.zftlive.android.library.Alert;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.network.ToolSOAP;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 调用WebService接口获取省份对应的城市Activity
 * @author 曾繁添
 * @version 1.0
 *
 */
public class CityActivity extends CommonActivity {
	
	/**显示省份Listview**/
	private ListView mCityListView;
	/***省份数据集合***/
	private List<String> citysList = new ArrayList<String>();

	@Override
	public int bindLayout() {
		return R.layout.activity_soap_provice_city;
	}

	@Override
	public void initParams(Bundle parms) {
		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {
		mCityListView = (ListView) findViewById(R.id.province_list);
		mCityListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				getOperation().addParameter("city", citysList.get(position));
				getOperation().forward(WeatherActivity.class);
			}
		});
		
		//初始化带返回按钮的标题栏
//		ActionBarManager.initBackTitle(getContext(), getActionBar(), "城市列表");
		mWindowTitle.initBackTitleBar("城市列表");
	}

	@Override
	public void doBusiness(final Context mContext) {
		
		//等待对话框
		Alert.loading(this, "数据加载中...");
		
		//添加参数
		HashMap<String, String> properties = new HashMap<String, String>();
		properties.put("byProvinceName", String.valueOf(getOperation().getParameter("province")));
		
		ToolSOAP.callService(ProviceActivity.WEB_SERVER_URL,ProviceActivity.NAME_SPACE,"getSupportCity", properties, new ToolSOAP.WebServiceCallBack() {
			
			@Override
			public void onSucced(SoapObject result) {
				//关闭等待对话框
				Alert.closeLoading();
				if(result != null){
					citysList = parseSoapObject(result);
					mCityListView.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, citysList));
				}else{
					Alert.toastShort(mContext, "呼叫WebService-->getSupportCity失败");
				}
			}

			@Override
			public void onFailure(String result) {
				//关闭等待对话框
				Alert.closeLoading();

				Alert.toastShort(mContext, "呼叫WebService-->getSupportProvince失败，原因："+result);
			}
		});
	}

	/**
	 * 解析SoapObject对象
	 * @param result
	 * @return
	 */
	private List<String> parseSoapObject(SoapObject result){
		List<String> list = new ArrayList<String>();
		SoapObject provinceSoapObject = (SoapObject) result.getProperty("getSupportCityResult");
		for(int i=0; i<provinceSoapObject.getPropertyCount(); i++){
			list.add(provinceSoapObject.getProperty(i).toString());
		}
		
		return list;
	}
}