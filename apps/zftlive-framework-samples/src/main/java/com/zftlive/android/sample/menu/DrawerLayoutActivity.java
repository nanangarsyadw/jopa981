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

package com.zftlive.android.sample.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zftlive.android.R;
import com.zftlive.android.library.base.ui.CommonActivity;

/**
 * 抽屉菜单
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class DrawerLayoutActivity extends CommonActivity {
	private String[] mPlanetTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;

	@Override
	public int bindLayout() {
		return R.layout.activity_drawerlayout;
	}

	@Override
	public void initParams(Bundle parms) {
		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// 绑定Listview
		mPlanetTitles = getResources().getStringArray(R.array.anim_type);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, mPlanetTitles));
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
//				ActionBarManager.updateActionCenterTitle(getContext(),getActionBar(), mPlanetTitles[position]);
				mWindowTitle.setWindowTitle( mPlanetTitles[position],Gravity.CENTER);
			  mDrawerList.setItemChecked(position, true);
			  mDrawerLayout.closeDrawer(mDrawerList);
			}
		});
	}

	@SuppressLint("NewApi")
	@Override
	public void doBusiness(Context mContext) {
//		ActionBar actionBar = getActionBar();
//		actionBar.setDisplayHomeAsUpEnabled(false);
//		actionBar.setDisplayShowHomeEnabled(true);
//		actionBar.setDisplayShowTitleEnabled(false);
//		actionBar.setHomeButtonEnabled(true);
//		actionBar.setLogo(R.drawable.ic_list_white_48dp);
//		actionBar.setDisplayUseLogoEnabled(true);
		String strCenterTitle = getResources().getString(R.string.DrawerLayoutActivity);
//      ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
		mWindowTitle.initBackTitleBar(strCenterTitle, Gravity.CENTER);
		mWindowTitle.getDoneImageButton().setVisibility(View.INVISIBLE);
		mWindowTitle.getBackImageButton().setImageResource(R.drawable.anl_common_nav_menu_white_n);
		mWindowTitle.getBackImageButton().setOnClickListener(new View.OnClickListener() {
          
          @Override
          public void onClick(View v) {
            // 按钮按下，将抽屉打开
            if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }else{
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
          }
        });
	}
	

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case android.R.id.home:
//			// 按钮按下，将抽屉打开
//			if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
//				mDrawerLayout.closeDrawer(Gravity.LEFT);
//			}else{
//				mDrawerLayout.openDrawer(Gravity.LEFT);
//			}
//			break;
//		default:
//			break;
//		}
//		
//	    return true;
//	}

}