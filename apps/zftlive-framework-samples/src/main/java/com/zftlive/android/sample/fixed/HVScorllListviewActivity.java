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

package com.zftlive.android.sample.fixed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zftlive.android.R;
import com.zftlive.android.library.base.adapter.BaseMAdapter;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.tools.ToolDateTime;
import com.zftlive.android.library.tools.ToolFile;
import com.zftlive.android.library.tools.ToolString;
import com.zftlive.android.library.widget.pulltorefresh.PullToRefreshBase;
import com.zftlive.android.library.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.zftlive.android.library.widget.pulltorefresh.PullToRefreshListView;
import com.zftlive.android.library.widget.scrollview.ObserverHScrollView;
import com.zftlive.android.library.widget.scrollview.ObserverHScrollView.OnScrollChangedListener;

import java.util.ArrayList;
import java.util.Date;

/**
 * 水平+垂直滚动+首行首列固定+翻页Listview样例 
 * @author 曾繁添
 * @version 1.0
 *
 */
public class HVScorllListviewActivity extends CommonActivity {

	/**列表表头容器**/
	private RelativeLayout mListviewHead;
	/**列表ListView**/
	private PullToRefreshListView mListView;
	/**列表ListView水平滚动条**/
	private HorizontalScrollView mHorizontalScrollView;
	/**列表适配器**/
	private BondSearchResultAdapter mListAdapter;
	/**总记录数**/
	private int totalRecordCount = 0;
	/**每一页请求条数**/
	private int mPerPageCount = 10;
	/***请求页码**/
	private int mPageNo = 1;
	/**是否加载完成**/
	private boolean isLoadFinished = true;
	
	@Override
	public int bindLayout() {
		return R.layout.activity_hvscorll_listview;
	}

	@Override
	public void initParams(Bundle parms) {
		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {
		
		//初始化列表表头
		mListviewHead = (RelativeLayout) findViewById(R.id.head);
		mListviewHead.setFocusable(true);
		mListviewHead.setClickable(true);
		mListviewHead.setBackgroundColor(getResources().getColor(R.color.widget_table_header));
		mListviewHead.setOnTouchListener(mHeadListTouchLinstener);
		mListviewHead.setVisibility(View.GONE);
		mHorizontalScrollView = (HorizontalScrollView) mListviewHead.findViewById(R.id.horizontalScrollView1); 
		
		//实例化listview适配器
		mListAdapter = new BondSearchResultAdapter(this);
		
		//初始化listview
		mListView = (PullToRefreshListView) findViewById(R.id.listView1);
		mListView.setMode(Mode.DISABLED);
		mListView.getLoadingLayoutProxy().setRefreshingLabel("正在加载..");
		mListView.getLoadingLayoutProxy().setReleaseLabel("释放刷新");
		mListView.setOnScrollListener(mListViewScrollListener);
		mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				mListView.getLoadingLayoutProxy(true,false).setPullLabel("下拉刷新");
				//下拉刷新
				if(isLoadFinished){
					requestData(1);
					mListView.getLoadingLayoutProxy().setLastUpdatedLabel("上次更新："+ToolDateTime.formatDateTime(new Date(), "yyyy-MM-dd HH:mm"));
				}
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				mListView.getLoadingLayoutProxy(false,true).setPullLabel("加载更多");
				//上拉加载更多
				if(mListAdapter.getCount() < totalRecordCount && isLoadFinished){
					requestData(mListAdapter.getPageNo());
				}
			}
		});
		
		//获取到Listview
		ListView listview = mListView.getRefreshableView();
		listview.setOnTouchListener(mHeadListTouchLinstener);
		listview.setAdapter(mListAdapter);
		
		//等待对话框
		requestData(1);
		mListView.getLoadingLayoutProxy().setLastUpdatedLabel("上次更新："+ToolDateTime.formatDateTime(new Date(), "yyyy-MM-dd HH:mm"));
	
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.HVScorllListviewActivity);
//      ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
		mWindowTitle.initBackTitleBar(strCenterTitle);
	}

	/**
	 * 请求数据
	 */
	private void requestData(int pageNo){
		mPageNo = pageNo;
		
		try {
			//弹出加载对话框
			getOperation().showLoading("加载中，请稍后...");
			//正在加载
			isLoadFinished = false;
			//模拟数据
			String data = ToolFile.readAssetsValue(getContext(), "hvc_data.json");
			Gson gson = new GsonBuilder().create();
			ListDataResponse response = (ListDataResponse)gson.fromJson(data,ListDataResponse.class);

			parseData(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 解析数据
	 * @param response
	 */
	private void parseData(ListDataResponse response){
		try {
			// 错误信息
			String errorMessage = response.errorMsg;
			if (!ToolString.isNoBlankAndNoNull(errorMessage)) 
			{
				// 获取数据
				ArrayList<DataListItemRowBean> dataList = response.dataList;
				//下拉刷新情况
				if(mPageNo == 1){
					mListAdapter.clear();
				}
				for (int i = 0; i < dataList.size(); i++) {
					mListAdapter.addItem(dataList.get(i));
				}
				//列头可见
				mListviewHead.setVisibility(View.VISIBLE);
				//通知更新数据
				mListAdapter.notifyDataSetChanged();
				//结果集中记录数 TODO 
				totalRecordCount = response.dataCount;
				//刷新完成
				isLoadFinished = true;
				mListView.onRefreshComplete();
			} 
		} catch (Exception e) {
			Log.e(TAG, e.getMessage().toString());
			//刷新完成
			isLoadFinished = true;
			mListView.onRefreshComplete();
		}
		
		// 关闭等待对话框
		getOperation().closeLoading();
	}
	
	/**
	 * 列头/Listview触摸事件监听器<br>
	 * 当在列头 和 listView控件上touch时，将这个touch的事件分发给 ScrollView
	 */
	private View.OnTouchListener mHeadListTouchLinstener = new View.OnTouchListener(){

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			mHorizontalScrollView.onTouchEvent(event);
			return false;
		}
	};
	
    /**
     * listView滚动监听器
     */
    private AbsListView.OnScrollListener mListViewScrollListener = new AbsListView.OnScrollListener() {
        @Override
        /**
         * firstVisibleItem 当前页第一条记录的索引   
         * visibleItemCount 当前页可见条数 
         * totalItemCount 已加载总记录数
         **/
        public void onScroll(AbsListView view, int firstVisibleItem,
                int visibleItemCount, int totalItemCount) {
            if (visibleItemCount > 0
                    && firstVisibleItem + visibleItemCount == totalItemCount
                    && totalRecordCount > totalItemCount && isLoadFinished) {
                requestData(mListAdapter.getPageNo());
            }
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
    };
	
	/**
	 * 债券查询列表适配器
	 */
	public class BondSearchResultAdapter extends BaseMAdapter{
		
		public BondSearchResultAdapter(Context mContext) {
			super(mContext);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 查找控件
			ViewHolder holder = null;
			if (null == convertView) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_hvscorll_listview_item, null);
				holder = new ViewHolder();
				
				holder.txt1 = (TextView) convertView.findViewById(R.id.textView1);
				holder.txt2 = (TextView) convertView.findViewById(R.id.textView2);
				holder.txt3 = (TextView) convertView.findViewById(R.id.textView3);
				holder.txt4 = (TextView) convertView.findViewById(R.id.textView4);
				holder.txt5 = (TextView) convertView.findViewById(R.id.textView5);
				holder.txt6 = (TextView) convertView.findViewById(R.id.textView6);
				holder.txt7 = (TextView) convertView.findViewById(R.id.textView7);
				holder.txt8 = (TextView) convertView.findViewById(R.id.textView8);
				holder.txt9 = (TextView) convertView.findViewById(R.id.textView9);
				holder.txt10 = (TextView) convertView.findViewById(R.id.textView10);
				holder.txt11 = (TextView) convertView.findViewById(R.id.textView11);
				holder.txt12 = (TextView) convertView.findViewById(R.id.textView12);
				holder.txt13 = (TextView) convertView.findViewById(R.id.textView13);
				holder.txt14 = (TextView) convertView.findViewById(R.id.textView14);
				holder.txt15 = (TextView) convertView.findViewById(R.id.textView15);
				holder.txt16 = (TextView) convertView.findViewById(R.id.textView16);
				holder.txt17 = (TextView) convertView.findViewById(R.id.textView17);
				holder.txt18 = (TextView) convertView.findViewById(R.id.textView18);
				holder.txt19 = (TextView) convertView.findViewById(R.id.textView19);
				holder.txt20 = (TextView) convertView.findViewById(R.id.textView20);
				holder.txt21 = (TextView) convertView.findViewById(R.id.textView21);
				holder.txt22 = (TextView) convertView.findViewById(R.id.textView22);
				//列表水平滚动条
				ObserverHScrollView scrollView1 = (ObserverHScrollView) convertView.findViewById(R.id.horizontalScrollView1);
				holder.scrollView = (ObserverHScrollView) convertView.findViewById(R.id.horizontalScrollView1);
				//列表表头滚动条
				ObserverHScrollView headSrcrollView = (ObserverHScrollView) mListviewHead.findViewById(R.id.horizontalScrollView1);
				headSrcrollView.AddOnScrollChangedListener(new OnScrollChangedListenerImp(scrollView1));

				convertView.setTag(holder);
				
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			// 装填数据
			DataListItemRowBean data = (DataListItemRowBean) getItem(position);
			try {
				holder.txt1.setText(data.zqmc);
				holder.txt2.setText(data.zqjc);
				holder.txt3.setText(data.zqdm);
				holder.txt4.setText(data.fxjc);
				holder.txt5.setText(data.ksr);
				holder.txt6.setText(data.fxjg);
				holder.txt7.setText(data.sjfxze);
				holder.txt8.setText(data.jxfs);
				holder.txt9.setText(data.fxzq);
				holder.txt10.setText(data.pmll);
				holder.txt11.setText(data.lc);
				holder.txt12.setText(data.zjll);
				holder.txt13.setText(data.zqqx);
				holder.txt14.setText(data.zqpj);
				holder.txt15.setText(data.ztpj);
				holder.txt16.setText(data.zqpjjg);
				holder.txt17.setText(data.ztpjjg);
				holder.txt18.setText(data.zqzwdjr);
				holder.txt19.setText(data.ltr);
				holder.txt20.setText(data.jzghr);
				holder.txt21.setText(data.qxr);
				holder.txt22.setText(data.dqr);
			} catch (Exception e) {
				Log.e(TAG, e.getMessage().toString());
			}
			
			return convertView;
		}
		
		private class OnScrollChangedListenerImp implements OnScrollChangedListener {
			ObserverHScrollView mScrollViewArg;

			public OnScrollChangedListenerImp(ObserverHScrollView scrollViewar) {
				mScrollViewArg = scrollViewar;
			}

			@Override
			public void onScrollChanged(int l, int t, int oldl, int oldt) {
				mScrollViewArg.smoothScrollTo(l, t);
			}
		};

		private class ViewHolder {
			TextView txt1;
			TextView txt2;
			TextView txt3;
			TextView txt4;
			TextView txt5;
			TextView txt6;
			TextView txt7;
			TextView txt8;
			TextView txt9;
			TextView txt10;
			TextView txt11;
			TextView txt12;
			TextView txt13;
			TextView txt14;
			TextView txt15;
			TextView txt16;
			TextView txt17;
			TextView txt18;
			TextView txt19;
			TextView txt20;
			TextView txt21;
			TextView txt22;
			HorizontalScrollView scrollView;
		}
	}
}
