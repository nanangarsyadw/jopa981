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

package com.zftlive.android.sample.map.location;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.zftlive.android.R;
import com.zftlive.android.library.base.ui.CommonActivity;

/**
 * 百度地图定位演示DEMO
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class BaiduLoctionActivity extends CommonActivity implements SensorEventListener {

	private MapView mMapView = null;
	private BaiduMap mBaiduMap;
	private boolean isFirstLoc = true; // 是否首次定位
	private MyLocationData locData;
	// 定位相关
	private LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private SensorManager mSensorManager;
	private Double lastX = 0.0;
	private int mCurrentDirection = 0;
	private double mCurrentLat = 0.0;
	private double mCurrentLon = 0.0;
	private float mCurrentAccracy;

	@Override
	public int bindLayout() {
		//在使用SDK各组件之前初始化context信息，传入ApplicationContext
		//注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		return R.layout.activity_baidu_map_loc;
	}

	@Override
	public void initView(View view) {
		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// 隐藏logo
		View child = mMapView.getChildAt(1);
		if (child != null && (child instanceof ImageView || child instanceof ZoomControls)){
			child.setVisibility(View.INVISIBLE);
		}
		//地图上比例尺
		mMapView.showScaleControl(false);
		// 隐藏缩放控件
		mMapView.showZoomControls(false);
		//普通模式
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
	}

	@Override
	public void doBusiness(Context mContext) {
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.BaiduLocationActivity);
		mWindowTitle.initBackTitleBar(strCenterTitle);

		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		double x = sensorEvent.values[SensorManager.DATA_X];
		if (Math.abs(x - lastX) > 1.0) {
			mCurrentDirection = (int) x;
			locData = new MyLocationData.Builder()
					.accuracy(mCurrentAccracy)
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(mCurrentDirection).latitude(mCurrentLat)
					.longitude(mCurrentLon).build();
			mBaiduMap.setMyLocationData(locData);
		}
		lastX = x;

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
		//为系统的方向传感器注册监听器
		mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onStop() {
		//取消注册传感器监听
		mSensorManager.unregisterListener(this);
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null) {
				return;
			}
			mCurrentLat = location.getLatitude();
			mCurrentLon = location.getLongitude();
			mCurrentAccracy = location.getRadius();
			locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(mCurrentDirection).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatus.Builder builder = new MapStatus.Builder();
				builder.target(ll).zoom(18.0f);
				mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
}
