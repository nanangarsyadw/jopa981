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

package com.zftlive.android.sample.db;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.zftlive.android.R;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.widget.listview.SwipeListView;

public class DBOpreationActivity extends CommonActivity {

	private SwipeListView mListView;
	private MyFavDataAdapter mAdapter;
	
	@Override
	public int bindLayout() {
		return R.layout.activity_db_fav_list;
	}
	
	@Override
	public void initParams(Bundle parms) {
		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {
		mListView = (SwipeListView) findViewById(R.id.fav_listview);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
//				//没有显示删除时进行跳转
//				if(!mListView.isShowRight()){
//					Intent intent = new Intent(getActivity(),EssayDetailActivity.class);
//					intent.putExtra("url", ((Favorite)mAdapter.getItem(position)).getLinkURL());
//					getActivity().startActivity(intent);
//				}
			}
		});
	}

}
