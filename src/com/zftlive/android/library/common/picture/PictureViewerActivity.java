package com.zftlive.android.library.common.picture;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.zftlive.android.R;
import com.zftlive.android.library.base.BaseActivity;
import com.zftlive.android.library.tools.ToolLog;

/**
 * 共同机能-图片查看器,跳转次界面需要传递参数：<br>
 * 1、IBaseConstant.PICTURE_VIEWER_DATASOURCE-->跳转需要传递图片(PictureBean)数据源List<PictureBean>(必须)<br>
 * 2、IBaseConstant.PICTURE_VIEWER_DEFAULT_POSTION-->默认选中索引位置int mDefaultIndex（可选）<br>
 * 
 * @author 曾繁添
 * @version 1.0
 */
public class PictureViewerActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

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

  @Override
  public void config(Bundle savedInstanceState) {
    super.config(savedInstanceState);
    if (savedInstanceState != null) {
      mDefaultIndex = savedInstanceState.getInt(STATE_POSITION);
    }
  }
  
  @Override
  public int bindLayout() {
    return R.layout.common_picture_viewer;
  }

  @Override
  public void initParms(Bundle parms) {
    try {
      mDefaultIndex = parms.getInt(PICTURE_VIEWER_DEFAULT_POSTION,mDefaultIndex);
      ArrayList<PictureBean> imageData = (ArrayList<PictureBean>) parms.getSerializable(PICTURE_VIEWER_DATASOURCE);
      if (null != imageData && imageData.size() > 0) {
        dataSource.addAll(imageData);
      }
    } catch (Exception e) {
      e.printStackTrace();
      ToolLog.e(TAG, "获取参数发生异常，原因：" + e.getMessage());
    }
  }

  @Override
  public void initView(View view) {
    //隐藏标题栏
    hiddeTitleBar();
    
    mImageVpAdapter = new ImagePagerAdapter(getSupportFragmentManager(), dataSource);
    
    //图片Viewpager、指示文本
    mImageViewPager = (ViewPager) findViewById(R.id.pager);
    mImageViewPager.setAdapter(mImageVpAdapter);
    mImageViewPager.setOnPageChangeListener(this);
    
    //默认初始化
    indicator = (TextView) findViewById(R.id.indicator);
    CharSequence text = getString(R.string.viewpager_indicator, mDefaultIndex + 1, mImageVpAdapter.getCount());
    indicator.setText(text);
    mImageViewPager.setCurrentItem(mDefaultIndex);
  }

  @Override
  public void doBusiness(Context mContext) {

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
    CharSequence text = getString(R.string.viewpager_indicator, position + 1, mImageViewPager.getAdapter().getCount());
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
