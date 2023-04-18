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

package com.zftlive.android.sample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zftlive.android.GlobalApplication;
import com.zftlive.android.R;
import com.zftlive.android.library.Alert;
import com.zftlive.android.library.AppEnvironment;
import com.zftlive.android.library.base.adapter.BaseMAdapter;
import com.zftlive.android.library.base.bean.AdapterModelBean;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.tools.ToolPhone;

import java.util.ArrayList;
import java.util.List;

/**
 * Sample列表集合界面--自动收集AndroidManifest.xml配置
 * <per>
 *	<intent-filter>
 *		<action android:name="android.intent.action.MAIN" />
 *		<category android:name="com.zftlive.android.SAMPLE_CODE" />
 *	</intent-filter>
 *</per>
 * 的Activity
 * @author 曾繁添
 * @version 1.0
 *
 */
public class MainActivity extends CommonActivity {

	private ListView mListView;
	public final static String SAMPLE_CODE = "com.zftlive.android.SAMPLE_CODE";

	@Override
	public void config(Bundle savedInstanceState) {
		super.config(savedInstanceState);
//		ToolPhone.setTransulcentStatusBar(this,mWindowTitle);
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_main;
	}

	@Override
	public void initParams(Bundle parms) {
		//设置tag
//		BuglyReport.setUserSceneTag(getContext(), 17459);
	}
	
	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {
		mListView = (ListView)findViewById(R.id.lv_demos);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				ActivityItem data = (ActivityItem)parent.getItemAtPosition(position);
			     Intent intent = data.intent;
			     getOperation().forward(intent.getComponent().getClassName(), LEFT_RIGHT);
			}
		});
		
		//构造适配器
		DemoActivityAdapter mAdapter = new DemoActivityAdapter(this);
		mAdapter.addItem(getListData());
		mListView.setAdapter(mAdapter);
		
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.MainActivity);
//		ActionBarManager.initMenuListTitle(getContext(), getActionBar(), strCenterTitle);
		mWindowTitle.initBackTitleBar(strCenterTitle, Gravity.CENTER);
		mWindowTitle.getDoneImageButton().setVisibility(View.INVISIBLE);
		mWindowTitle.getBackImageButton().setImageResource(R.drawable.anl_common_nav_menu_white_n);

		//是否Root
		Toast.makeText(mActivity,"当前手机root状态："+ToolPhone.isRootSystem(),Toast.LENGTH_SHORT).show();
	}

	@Override
	public void doBusiness(Context mContext) {
		try {
			//获取运行环境
			boolean isEmulator = AppEnvironment.isEmulator(getContext());
			Alert.toastLong("当前运行环境："+(isEmulator? "模拟器"+"("+ AppEnvironment.OS_VERSION+")" :(AppEnvironment.MODEL_NUMBER+"("+ AppEnvironment.OS_VERSION+")") ));
			
			//获取渠道号
//			String manifestChannel = ToolData.gainMetaData(mContext, GlobalApplication.class,"InstallChannel");
//			manifestChannel = ToolData.gainMetaData(mContext, MainActivity.class,"InstallChannel");
//			manifestChannel = ToolData.gainMetaData(mContext, SMSInterceptService.class,"InstallChannel");
//			manifestChannel = ToolData.gainMetaData(mContext, SMSBroadcastReceiver.class,"InstallChannel");

			Alert.toastShort("应用渠道号："+GlobalApplication.channelId);

			ToolPhone.setStatusBarBackgroundColor(this,getResources().getColor(R.color.common_module_actionbar_bg));

		} catch (Exception e) {
		}
	}

	/**
	 * Actionbar点击[左侧图标]关闭事件
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
//				finish();
				break;
		}
		return true;
	}
	
	private boolean isQuit;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if (isQuit == false) {
				isQuit = true;
				Toast.makeText(getBaseContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						isQuit = false;
					}
				}, 2000);
			} else {
				((GlobalApplication)getApplication()).exit();
			}
		}
		return true;
	}
	
	/**
	 * 初始化列表数据
	 * @return
	 */
	protected List<ActivityItem> getListData(){
		List<ActivityItem> mListViewData = new ArrayList<>();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(SAMPLE_CODE);
        List<ResolveInfo> mActivityList = getPackageManager().queryIntentActivities(mainIntent, 0);
        for (int i = 0; i < mActivityList.size(); i++) 
        {
            ResolveInfo info = mActivityList.get(i);
            String label = info.loadLabel(getPackageManager()) != null? info.loadLabel(getPackageManager()).toString() : info.activityInfo.name;
			ActivityItem temp = new ActivityItem();
            temp.title = label;
            temp.intent = buildIntent(info.activityInfo.applicationInfo.packageName,info.activityInfo.name);
            mListViewData.add(temp);
        }
        
        return mListViewData;
	}
	
	/**
	 * 构建每一个Item点击Intent
	 * @param packageName
	 * @param componentName
	 * @return
	 */
    protected Intent buildIntent(String packageName, String componentName) {
        Intent result = new Intent();
        result.setClassName(packageName, componentName);
        return result;
    }
	
	/**
	 * 列表适配器
	 */
	protected class DemoActivityAdapter extends BaseMAdapter{

		public DemoActivityAdapter(Activity mContext) {
			super(mContext);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			Holder mHolder = null;
			if(null == convertView){
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_main_list_item, null);
				mHolder = new Holder();
				mHolder.label = (TextView)convertView.findViewById(R.id.tv_label);
				convertView.setTag(mHolder);
			}else{
				mHolder = (Holder) convertView.getTag();
			}
			
			//设置隔行变色背景
//			if(position%2==0){
//				convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
//			}else{
//				convertView.setBackgroundColor(Color.parseColor("#CCCCCC"));
//			}
			
			//设置数据
			ActivityItem data = (ActivityItem) getItem(position);
			mHolder.label.setText(data.title);
			
			return convertView;
		}
		
		class Holder{
			TextView label;
		}
	}

	class ActivityItem extends AdapterModelBean {
		String title;
		Intent intent;

		public ActivityItem() {
		}

		public ActivityItem(String title, Intent intent) {
			this.title = title;
			this.intent = intent;
		}
	}

}
