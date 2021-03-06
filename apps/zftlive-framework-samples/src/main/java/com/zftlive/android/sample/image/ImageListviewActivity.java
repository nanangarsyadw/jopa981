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

package com.zftlive.android.sample.image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.zftlive.android.R;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.common.adapter.SingleTypeAdapter;
import com.zftlive.android.library.widget.swiperefresh.SwipeRefreshListview;

/**
 * 异步加载图片示例DEMO，防止图片错位
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ImageListviewActivity extends CommonActivity implements SwipeRefreshListview.RefreshListener {

	private SwipeRefreshListview mListView;
	private SingleTypeAdapter mMyListViewAdapter;
	private String imageURLs[] = new String[]{
			"http://www.daqianduan.com/wp-content/uploads/2014/12/kanjian.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/11/capinfo.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/11/mi-2.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/10/dxy.cn_.png",
			"http://www.daqianduan.com/wp-content/uploads/2014/10/xinhua.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/09/job.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2013/06/ctrip.png",
			"http://www.daqianduan.com/wp-content/uploads/2014/09/ideabinder.png",
			"http://www.daqianduan.com/wp-content/uploads/2014/05/ymatou.png",
			"http://www.daqianduan.com/wp-content/uploads/2014/03/west_house.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/03/youanxianpin.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/02/jd.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2013/11/wealink.png",
			"http://www.daqianduan.com/wp-content/uploads/2013/09/exmail.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2013/09/alipay.png",
			"http://www.daqianduan.com/wp-content/uploads/2013/08/huaqiangbei.png",
			"http://www.daqianduan.com/wp-content/uploads/2013/06/ctrip.png",
			"http://www.daqianduan.com/static/img/thumbnail.png",
			"http://www.daqianduan.com/wp-content/uploads/2013/06/bingdian.png",
			"http://www.daqianduan.com/wp-content/uploads/2013/04/ctrip-wireless.png"
	};
	private String titles[] = new String[]{
			"看见网络科技（上海）有限公司招前端开发工程师",
			"首都信息发展股份有限公司招Web前端工程师(北京-海淀)",
			"小米邀靠谱前端一起玩，更关注用户前端体验(北京)",
			"丁香园求多枚Web前端工程师(杭州滨江 8-15K)",
			"新华网招中高级Web前端开发工程师（北京 8-20K）",
			"好声音母公司梦响强音文化传播招前端、交互和UI设计师(上海)",
			"携程网国际业务部招靠谱前端(HTML+CSS+JS)(上海总部)",
			"ideabinder招聘Web前端开发工程师（JS方向 北京 6-12K）",
			"海外购物公司洋码头招Web前端开发工程师（上海）",
			"金山软件-西山居(珠海)招募前端开发工程师、PHP开发工程师",
			"优安鲜品招Web前端开发工程师(上海)",
			"京东招聘Web前端开发工程师(中/高/资深) 8-22K",
			"若邻网(上海)急聘资深前端工程师",
			"腾讯广州研发线邮箱部门招聘前端开发工程师（内部直招）",
			"支付宝招募资深交互设计师、视觉设计师（内部直招）",
			"华强北商城招聘前端开发工程师",
			"携程(上海)框架研发部招开发工程师(偏前端)",
			"阿里巴巴中文站招聘前端开发",
			"多途网络科技 15K 招聘前端开发工程师",
			"携程无线前端团队招聘 直接内部推荐（携程上海总部）"
	};
	
	@Override
	public int bindLayout() {
		return R.layout.activity_image_listview;
	}
	
	@Override
	public void initParams(Bundle parms) {
		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {
		mListView = (SwipeRefreshListview)findViewById(R.id.lv_list);
		mListView.setColorSchemeResources(R.color.anl_blue_359df5_60_alpha);
		mListView.setRefreshListener(this);
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.ImageListviewActivity);
//      ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
		mWindowTitle.initBackTitleBar(strCenterTitle);
	}

	@Override
	public void doBusiness(Context mContext) {
		mMyListViewAdapter = new SingleTypeAdapter(this);
		mMyListViewAdapter.registeViewTemplet(MyListViewTemplet.class);
		initData();
		mListView.getRefreshableView().setAdapter(mMyListViewAdapter);
	}

	private void initData(){
		mMyListViewAdapter.clear();
		//构造数据
		for (int i = 0; i < 20; i++) {
			mMyListViewAdapter.addItem(new ImageRowBean(i+1+"-"+titles[i],imageURLs[i]));
		}
		mListView.postDelayed(new Runnable() {
			@Override
			public void run() {
				if(mListView.isRefreshing()){
					mListView.setRefreshing(false);
				}
			}
		},200);

	}

	@Override
	public void onLoadMore() {

	}

	@Override
	public void onRefresh() {
		initData();
	}
}
