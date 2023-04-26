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

package com.zftlive.android.sample.imagepicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.GridView;

import com.zftlive.android.R;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.common.adapter.SingleTypeAdapter;

import java.util.ArrayList;

/**
 * 异步加载图片示例DEMO，防止图片错位
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ImagePickerActivity extends CommonActivity {

	private GridView mListView;
	private SingleTypeAdapter mMyListViewAdapter;

	@Override
	public int bindLayout() {
		return R.layout.activity_image_grid;
	}
	
	@Override
	public void initParams(Bundle parms) {
		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {
		mListView = (GridView)findViewById(R.id.gv_square);
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.ImagePickerActivity);
//      ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
		mWindowTitle.initBackTitleBar(strCenterTitle);
	}

	@Override
	public void doBusiness(Context mContext) {
		mMyListViewAdapter = new SingleTypeAdapter(this);
		mMyListViewAdapter.registeViewTemplet(MyGridViewTemplet.class);
		initData();
		mListView.setAdapter(mMyListViewAdapter);
	}

	private void initData(){
		mMyListViewAdapter.clear();
		mMyListViewAdapter.addItem(getLoadMedia());

//		//构造数据
//		for (int i = 0; i < 20; i++) {
//			mMyListViewAdapter.addItem(new ImageRowBean(i+1+"-"+titles[i],imageURLs[i]));
//		}
//		mListView.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				if(mListView.isRefreshing()){
//					mListView.setRefreshing(false);
//				}
//			}
//		},200);

	}

	public ArrayList getLoadMedia() {

		ArrayList<ImageFileBean> result = new ArrayList<>();

		Cursor cursor = this.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
		try {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)); // id
				String displayName =cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
				String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM)); // 专辑
				String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST)); // 艺术家
				String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 显示名称
				String mimeType =cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
				String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)); // 路径
				long duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)); // 时长
				long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)); // 大小
				String resolution =cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION));

				ImageFileBean item = new ImageFileBean();
				item.id = id;
				item.displayName = displayName;
				item.album = album;
				item.artist = artist;
				item.title = title;
				item.mimeType = mimeType;
				item.path = path;
				item.imageURL = path;
				item.duration = duration;
				item.size = size;
				item.resolution = resolution;

				result.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}

		return result;
	}
}
