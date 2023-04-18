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

package com.zftlive.android.sample.tabs.viewpager.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;

import com.zftlive.android.R;
import com.zftlive.android.library.base.bean.TabBean;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.sample.tabs.viewpager.adapter.FragmentTabAdapter;

/**
 * tab主界面实现示例（基于ViewPager+Fragment实现，支持滑动与点击切换，主流实现方式）
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class MainVPTabActivity extends CommonActivity
    implements
      View.OnClickListener,
      ViewPager.OnPageChangeListener {

  /**
   * Tab容器ViewPager
   */
  private ViewPager mTabViewPager;

  /**
   * tab适配器
   */
  private FragmentTabAdapter mTabAdapter;

  /**
   * tab切换按钮
   */
  private RadioButton mHomeRb, mFavrateRb, mMessageRb, mSearchRb, mSettingRb;

  @Override
  public int bindLayout() {
    return R.layout.activity_tabs_viewpager;
  }

  @Override
  public void initParams(Bundle parms) {

  }

  @Override
  public void initView(View view) {
    // tab切换按钮
    mHomeRb = (RadioButton) findViewById(R.id.rb_home);
    mHomeRb.setOnClickListener(this);
    mFavrateRb = (RadioButton) findViewById(R.id.rb_favrate);
    mFavrateRb.setOnClickListener(this);
    mMessageRb = (RadioButton) findViewById(R.id.rb_message);
    mMessageRb.setOnClickListener(this);
    mSearchRb = (RadioButton) findViewById(R.id.rb_search);
    mSearchRb.setOnClickListener(this);
    mSettingRb = (RadioButton) findViewById(R.id.rb_setting);
    mSettingRb.setOnClickListener(this);

    mTabViewPager = (ViewPager) findViewById(R.id.vp_tabs);
    mTabAdapter = new FragmentTabAdapter(getSupportFragmentManager(), getContext());
  }

  @Override
  public void doBusiness(Context mContext) {
    // 初始化标题栏
    mWindowTitle.initCenterTitleBar("主界面");

    // 构建tab数据
    mTabAdapter.addItem(new TabBean("1",TabHomeFragment.class));
    mTabAdapter.addItem(new TabBean("2",TabFavrateFragment.class));
    mTabAdapter.addItem(new TabBean("3",TabMessageFragment.class));
    mTabAdapter.addItem(new TabBean("4",TabSearchFragment.class));
    mTabAdapter.addItem(new TabBean("5",TabSettingFragment.class));
    mTabViewPager.setAdapter(mTabAdapter);
    mTabAdapter.notifyDataSetChanged();
    //当前tab两边各一个，内存中保留三个，默认设置
    mTabViewPager.setOffscreenPageLimit(1);
    mTabViewPager.setCurrentItem(0);
    mTabViewPager.setOnPageChangeListener(this);
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override
  public void onPageSelected(int position) {
    // 滑动设置标题、RB选中
    // CharSequence strTitle = mTabAdapter.getPageTitle(position);
    // setWindowTitle(strTitle, Gravity.CENTER);

    //同步RB的状态
    switch (position) {
      case 0:
        mHomeRb.setChecked(true);
        break;
      case 1:
        mFavrateRb.setChecked(true);
        break;
      case 2:
        mMessageRb.setChecked(true);
        break;
      case 3:
        mSearchRb.setChecked(true);
        break; 
      case 4:
        mSettingRb.setChecked(true);
        break;         
      default:
        break;
    }
  }

  @Override
  public void onPageScrollStateChanged(int state) {

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
    // [首页]按钮
      case R.id.rb_home:
        mTabViewPager.setCurrentItem(0);
        break;
      // [收藏]按钮
      case R.id.rb_favrate:
        mTabViewPager.setCurrentItem(1);
        break;
      // [消息]按钮
      case R.id.rb_message:
        mTabViewPager.setCurrentItem(2);
        break;
      // [搜索]按钮
      case R.id.rb_search:
        mTabViewPager.setCurrentItem(3);
        break;
      // [设置]按钮
      case R.id.rb_setting:
        mTabViewPager.setCurrentItem(4);
        break;
      default:
        break;
    }
  }

}
