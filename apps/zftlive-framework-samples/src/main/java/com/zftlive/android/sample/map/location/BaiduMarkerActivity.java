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
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomControls;

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
import com.baidu.mapapi.model.LatLng;
import com.zftlive.android.R;
import com.zftlive.android.library.base.ui.CommonActivity;

/**
 * 百度地图标注演示DEMO
 *
 * @author 曾繁添
 * @version 1.0
 */
public class BaiduMarkerActivity extends CommonActivity {

    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private TextView tv_locInfo = null;
    private Marker mMarkerA;
    private InfoWindow mInfoWindow;
    private BitmapDescriptor bdA;

    @Override
    public int bindLayout() {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        return R.layout.activity_baidu_map_loc;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        tv_locInfo = (TextView) findViewById(R.id.loc_info);

        // 获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 隐藏logo
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        //地图上比例尺
        mMapView.showScaleControl(false);
        // 隐藏缩放控件
        mMapView.showZoomControls(false);
        //普通模式
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        try{
            //设定中心点坐标 http://api.map.baidu.com/lbsapi/getpoint/index.html（注意：此处经纬度与百度拾取坐标系统是反着的）
            // 天津水上公园(117.172323, 39.098375);new LatLng(latitude - 纬度,longitude - 经度)
//        LatLng cenpt = cenpt = new LatLng(39.098375, 117.172323);
            LatLng cenpt = new LatLng(Double.valueOf("39.098375"), Double.valueOf("117.172323"));
            //定义地图状态
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(cenpt)
                    .zoom(14)
                    .build();
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            //改变地图状态
            mBaiduMap.setMapStatus(mMapStatusUpdate);

            //添加标注
            addOverlay(cenpt);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        //初始化带返回按钮的标题栏
        mWindowTitle.initBackTitleBar("百度地图定位至指定经纬度+标注");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mBaiduMap) {
            mMapView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mBaiduMap) {
            mMapView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            // 关闭定位图层
            if (null != mBaiduMap) {
                mBaiduMap.setMyLocationEnabled(false);
            }

            if (null != mMapView) {
                mMapView.onDestroy();
                mMapView = null;
            }

            if (null != bdA) {
                bdA.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    /**
     * 添加标注
     *
     * @param cenpt
     */
    private void addOverlay(LatLng cenpt) {
        try {
            // 初始化全局 bitmap 信息，不用时及时 recycle
            bdA = BitmapDescriptorFactory
                    .fromResource(R.drawable.common_loction_gcoding);

            MarkerOptions ooA = new MarkerOptions().position(cenpt).icon(bdA)
                    .zIndex(9).draggable(true);
            // 掉下动画
            ooA.animateType(MarkerOptions.MarkerAnimateType.drop);
            mBaiduMap.addOverlay(ooA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
