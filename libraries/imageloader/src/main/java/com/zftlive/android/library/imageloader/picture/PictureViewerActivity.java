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

package com.zftlive.android.library.imageloader.picture;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zftlive.android.library.imageloader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 共同机能-图片查看器,跳转次界面需要传递参数：<br>
 * 1、IBaseConstant.PICTURE_VIEWER_DATASOURCE-->跳转需要传递图片(PictureBean)数据源List<PictureBean>(必须)<br>
 * 2、IBaseConstant.PICTURE_VIEWER_DEFAULT_POSTION-->默认选中索引位置int mDefaultIndex（可选）<br>
 * 
 * @author 曾繁添
 * @version 1.0
 */
public class PictureViewerActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

  private static final String STATE_POSITION = "STATE_POSITION";
  
  /**
   * 当前默认选中位置
   */
  private int mDefaultIndex = 0;

  /**
   * 图片数据源
   */
  private ArrayList<PictureBean> dataSource = new ArrayList<PictureBean>();

  /**
   * 可滑动的图片查看器
   */
  private ViewPager mImageViewPager;
  
  /**
   * 图片VP适配器
   */
  private ImagePagerAdapter mImageVpAdapter;
  
  /**
   * 指示器
   */
  private TextView indicator;

  /**
   * 查看器图片默认选中位置
   */
  public static String PICTURE_VIEWER_DEFAULT_POSTION = "defaultPostion";

  /**
   * 查看器数据源
   */
  public static String PICTURE_VIEWER_DATASOURCE = "pictureViewerDatasource",TAG = "PictureViewerActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "PictureViewerActivity-->onCreate()");
    //需要在setContentView之前配置window的一些属性
    config(savedInstanceState);

    View mContentView = (ViewGroup) LayoutInflater.from(this).inflate(bindLayout(), null);
    setContentView(mContentView);

    // 初始化参数
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
    } else {
      bundle = new Bundle();
    }
    initParams(bundle);

    // 初始化控件
    initView(mContentView);
  }

  public void config(Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      mDefaultIndex = savedInstanceState.getInt(STATE_POSITION);
    }
  }
  
  public int bindLayout() {
    return R.layout.anl_common_picture_viewer;
  }

  public void initParams(Bundle parms) {
    try {
      mDefaultIndex = parms.getInt(PICTURE_VIEWER_DEFAULT_POSTION,mDefaultIndex);
      ArrayList<PictureBean> imageData = (ArrayList<PictureBean>) parms.getSerializable(PICTURE_VIEWER_DATASOURCE);
      if (null != imageData && imageData.size() > 0) {
        dataSource.addAll(imageData);
      }
    } catch (Exception e) {
      e.printStackTrace();
      Log.e(TAG, "获取参数发生异常，原因：" + e.getMessage());
    }
  }

  public void initView(View view) {

    mImageVpAdapter = new ImagePagerAdapter(getSupportFragmentManager(), dataSource);
    
    //图片Viewpager、指示文本
    mImageViewPager = (ViewPager) findViewById(R.id.pager);
    mImageViewPager.setAdapter(mImageVpAdapter);
    mImageViewPager.setOnPageChangeListener(this);
    
    //默认初始化
    indicator = (TextView) findViewById(R.id.indicator);
    CharSequence text = getString(R.string.anl_viewpager_indicator, mDefaultIndex + 1, mImageVpAdapter.getCount());
    indicator.setText(text);
    //2017.11.14一个的时候不需要指示器
    if(mImageVpAdapter.getCount() <= 1){
      indicator.setVisibility(View.INVISIBLE);
    }

    mImageViewPager.setCurrentItem(mDefaultIndex);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
      outState.putInt(STATE_POSITION, mImageViewPager.getCurrentItem());
  }
  
  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    
  }

  @Override
  public void onPageSelected(int position) {
    CharSequence text = getString(R.string.anl_viewpager_indicator, position + 1, mImageViewPager.getAdapter().getCount());
    indicator.setText(text);
  }

  @Override
  public void onPageScrollStateChanged(int state) {

  }
  
  /**
   * 图片适配器
   *
   */
  class ImagePagerAdapter extends FragmentStatePagerAdapter {

    public List<PictureBean> fileList;

    public ImagePagerAdapter(FragmentManager fm, List<PictureBean> fileList) {
      super(fm);
      this.fileList = fileList;
    }

    @Override
    public int getCount() {
      return fileList == null ? 0 : fileList.size();
    }

    @Override
    public Fragment getItem(int position) {
      return PictureItemFragment.newInstance(fileList.get(position));
    }
  }
}
