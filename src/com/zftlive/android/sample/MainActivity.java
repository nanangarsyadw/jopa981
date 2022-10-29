package com.zftlive.android.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
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
import com.zftlive.android.library.base.BaseActivity;
import com.zftlive.android.library.base.BaseMAdapter;
import com.zftlive.android.library.common.ActionBarManager;
import com.zftlive.android.library.config.SysEnv;
import com.zftlive.android.library.tools.ToolAlert;

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
public class MainActivity extends BaseActivity {

	private ListView mListView;
	public final static String SAMPLE_CODE = "com.zftlive.android.SAMPLE_CODE";
	
	@Override
	public int bindLayout() {
		return R.layout.activity_main;
	}
	
	@Override
	public View bindView() {
		return null;
	}

	@Override
	public void initParms(Bundle parms) {
		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {
		mListView = (ListView)findViewById(R.id.lv_demos);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				 Map<String, Object> map = (Map<String, Object>)parent.getItemAtPosition(position);
			     Intent intent = (Intent) map.get("intent");
			     getOperation().forward(intent.getComponent().getClassName(), LEFT_RIGHT);
			}
		});
		
		//构造适配器
		DemoActivityAdapter mAdapter = new DemoActivityAdapter(this);
		mAdapter.addItem(getListData());
		mListView.setAdapter(mAdapter);
		
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.MainActivity);
		ActionBarManager.initMenuListTitle(getContext(), getActionBar(), strCenterTitle);
	}

	@Override
	public void doBusiness(Context mContext) {
		try {
			//获取运行环境
			boolean isEmulator = SysEnv.isEmulator(getContext());
			ToolAlert.toastLong("当前运行环境："+(isEmulator? "模拟器"+"("+SysEnv.OS_VERSION+")" :(SysEnv.MODEL_NUMBER+"("+SysEnv.OS_VERSION+")") ));
			
			//获取渠道号
//			String manifestChannel = ToolData.gainMetaData(mContext, GlobalApplication.class,"InstallChannel");
//			manifestChannel = ToolData.gainMetaData(mContext, MainActivity.class,"InstallChannel");
//			manifestChannel = ToolData.gainMetaData(mContext, SMSInterceptService.class,"InstallChannel");
//			manifestChannel = ToolData.gainMetaData(mContext, SMSBroadcastReceiver.class,"InstallChannel");
			
			ToolAlert.toastShort("应用渠道号："+GlobalApplication.channelId);
			
		} catch (Exception e) {
		}
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void destroy() {
		
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
	protected List<Map<String, Object>> getListData(){
		List<Map<String, Object>> mListViewData = new ArrayList<Map<String, Object>>();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(SAMPLE_CODE);
        List<ResolveInfo> mActivityList = getPackageManager().queryIntentActivities(mainIntent, 0);
        for (int i = 0; i < mActivityList.size(); i++) 
        {
            ResolveInfo info = mActivityList.get(i);
            String label = info.loadLabel(getPackageManager()) != null? info.loadLabel(getPackageManager()).toString() : info.activityInfo.name;
        
            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("title", label);
            temp.put("intent", buildIntent(info.activityInfo.applicationInfo.packageName,info.activityInfo.name));
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
			mHolder.label.setText((String)((Map<String,Object>)getItem(position)).get("title"));
			
			return convertView;
		}
		
		class Holder{
			TextView label;
		}
	}

}
