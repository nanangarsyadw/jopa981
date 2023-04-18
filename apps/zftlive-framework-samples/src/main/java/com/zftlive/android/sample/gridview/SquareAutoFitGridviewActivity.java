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

package com.zftlive.android.sample.gridview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zftlive.android.R;
import com.zftlive.android.library.Alert;
import com.zftlive.android.library.base.adapter.BaseMAdapter;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.imageloader.ToolImage;

import java.util.Map;

/**
 * 正方形Item的gridview样例
 * @author 曾繁添
 * @version 1.0
 *
 */
public class SquareAutoFitGridviewActivity extends CommonActivity {

	private GridView gv_square;
	private MyGridAdapter mAdapter;
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
			"前端开发工程师",
			"Web前端工程师(北京-海淀)",
			"更关注用户前端体验(北京)",
			"丁香园求多枚Web",
			"新华网招中高级",
			"好声音母公司梦响强音文化",
			"携程网国际业务部",
			"ideabinder",
			"海外购物公司洋码头",
			"金山软件-西山居",
			"优安鲜品招Web前端",
			"京东招聘Web前端开",
			"若邻网(上海)急聘程师",
			"腾讯广州研发线邮箱部）",
			"支付宝招募资深交互设计师",
			"华强北商城招聘前端开发工程师",
			"携程(上海)框架研发部",
			"阿里巴巴中文站招聘前端开发",
			"多途网络科端开发工程师",
			"携程无线前端团队招聘 直接"
	};
	
	@Override
	public int bindLayout() {
		return R.layout.activity_block_gridview;
	}
	
	@Override
	public void initParams(Bundle parms) {
		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {
		gv_square = (GridView) findViewById(R.id.gv_square);
		gv_square.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Map<String,Object> itemData = (Map<String,Object>)parent.getItemAtPosition(position);
				Alert.toastShort(getContext(), itemData.get("title")+"");
			}
		});
		mAdapter = new MyGridAdapter(mActivity);
		ToolImage.init(getContext());
		
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.SquareAutoFitGridviewActivity);
//      ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
		mWindowTitle.initBackTitleBar(strCenterTitle);
	}

	@Override
	public void doBusiness(Context mContext) {
		//构造数据
		for (int i = 0; i < 20; i++) {
			GridItemRowBean rowData = new GridItemRowBean();
			rowData.imageURL = "http://www.daqianduan.com/wp-content/uploads/2013/09/exmail.jpg";
			rowData.title = "Ajava攻城师";
			mAdapter.addItem(rowData);
		}
		gv_square.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onDestroy() {
		ToolImage.clearCache();
		super.onDestroy();
	}

	/**
	 * 网格适配器
	 *
	 */
	protected class MyGridAdapter extends BaseMAdapter{

		public MyGridAdapter(Context mContext) {
			super(mContext);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder mViewHolder;
			if(null == convertView){
				convertView = getLayoutInflater().inflate(R.layout.activity_block_gridview_item, null);
				mViewHolder = new ViewHolder();
				mViewHolder.iv_icon = (ImageView)convertView.findViewById(R.id.iv_icon);
				mViewHolder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
				convertView.setTag(mViewHolder);
			}else{
				mViewHolder = (ViewHolder)convertView.getTag();
			}
			//设置数据
			GridItemRowBean rowData =  (GridItemRowBean)getItem(position);
			ToolImage.getInstance().displayImage(rowData.imageURL, mViewHolder.iv_icon);
			mViewHolder.tv_title.setText(rowData.title);
			return convertView;
		}
		
		class ViewHolder{
			ImageView iv_icon;
			TextView tv_title;
		}
	}
}
