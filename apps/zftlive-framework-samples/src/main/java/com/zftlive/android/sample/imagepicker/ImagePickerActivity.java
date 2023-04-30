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

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;

import com.zftlive.android.R;
import com.zftlive.android.library.Logger;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.common.adapter.SingleTypeAdapter;
import com.zftlive.android.library.tools.ToolDateTime;

import java.util.ArrayList;
import java.util.Date;

/**
 * 加载本地图片和视频展示+预览
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ImagePickerActivity extends CommonActivity implements LoaderManager.LoaderCallbacks<Cursor>{

	private GridView mListView;
	private SingleTypeAdapter mMyListViewAdapter;
	private static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION = 123;
    private String strCenterTitle = "";
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
        strCenterTitle = getResources().getString(R.string.ImagePickerActivity);
//      ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
		mWindowTitle.initBackTitleBar(strCenterTitle);
	}

	@Override
	public void doBusiness(Context mContext) {
		mMyListViewAdapter = new SingleTypeAdapter(this);
		mMyListViewAdapter.registeViewTemplet(MyGridViewTemplet.class);
		mListView.setAdapter(mMyListViewAdapter);
		initData();
	}

	private void initData(){
		// 检测读取、写入权限
		if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
				== PackageManager.PERMISSION_GRANTED &&
				ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
						== PackageManager.PERMISSION_GRANTED) {
			startLoader();
		} else if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
				|| ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
			// empty code
		} else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_READ_EXTERNAL_STORAGE_PERMISSION);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		// 权限授权结果
		if (requestCode == REQUEST_READ_EXTERNAL_STORAGE_PERMISSION &&
				grantResults[0] == PackageManager.PERMISSION_GRANTED &&
				grantResults[1] == PackageManager.PERMISSION_GRANTED) {
			startLoader();
		}
	}

	private void startLoader() {
		mOperation.showLoading("正在查询数据");
		mActivity.getSupportLoaderManager().initLoader(0, null, this);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@NonNull
	@Override
	public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle bundle) {
		String selection = null;
		String[] mimeTypes = new String[]{MimeType.MP4, MimeType.JPEG, MimeType.PNG};
		if (mimeTypes != null) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < mimeTypes.length; j++) {
				sb.append("mime_type=?");
				if (j + 1 < mimeTypes.length) {
					sb.append(" or ");
				}
			}
			selection = sb.toString();
		}

		Uri uri = MediaStore.Files.getContentUri("external");
//		uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//		uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

		return new CursorLoader(
				mActivity,
				uri,
				COLUMNS_NAME,
				selection,
				mimeTypes,
				DATE_ADDED + " DESC"
		);
	}

	@Override
	public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

		ArrayList result = new ArrayList();

		try {
			int countVideo = 0;
			if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
				do {
					String _id = cursor.getString(cursor.getColumnIndex(_ID));
					String storage_id = cursor.getString(cursor.getColumnIndex(STORAGE_ID));
					String title =cursor.getString(cursor.getColumnIndex(TITLE));
					String _data = cursor.getString(cursor.getColumnIndex(DATA));
					String _display_name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
					String mime_type = cursor.getString(cursor.getColumnIndex(MIME_TYPE));
					String media_type = cursor.getString(cursor.getColumnIndex(MEDIA_TYPE));
					String bucket_display_name = cursor.getString(cursor.getColumnIndex(BUCKET_DISPLAY_NAME));
					long size = cursor.getLong(cursor.getColumnIndex(SIZE)); // 大小
					long addDate = cursor.getLong(cursor.getColumnIndex(DATE_ADDED)); // 添加日期

					String originalUri =
							MediaStore.Images.Media.EXTERNAL_CONTENT_URI
									.buildUpon()
									.appendPath(_id)
									.build()
									.toString();

					ImageFileBean bean = new ImageFileBean();
					bean.title = title;
					bean.originUri = originalUri;
					bean.originPath = _data;
					// 这里不用缩略图测试下
					bean.thumbnailUri = originalUri;//getThumbnailPathForLocalFile(mActivity,Uri.parse(path));
					bean.isVideo = (MimeType.MP4.equals(mime_type) ? true : false);
					if(bean.isVideo){
						countVideo++;
					}
                    bean.id = _id;
                    bean.displayName = _display_name;
//                    bean.album = album;
//                    bean.artist = artist;
                    bean.title = title;
                    bean.mimeType = mime_type;
                    bean.path = _data;
                    bean.imageURL = _data;
                    bean.durationText = (bean.isVideo?getMediaDuring(_data):"");
                    bean.duration = getMediaDuringLong(bean.durationText);
                    bean.size = size;
//                    bean.resolution = resolution;
					bean.addDate = ToolDateTime.formatDateTime(new Date(addDate * 1000L),ToolDateTime.DF_YYYY_MM_DD_HH_MM_SS);
					result.add(bean);

					Logger.d(TAG,"id="+_id +" storage_id="+storage_id+ " addDate="+bean.addDate+ " durationText="+bean.durationText+" size="+size +  " mimeType="+mime_type +" title="+title + " path="+_data +" _display_name="+_display_name );

				} while (cursor.moveToNext());
			}

			mWindowTitle.getTitleTextView().setText(strCenterTitle+"(总计："+result.size()+" 视频："+countVideo +"其余："+(result.size()-countVideo)+")");
			mMyListViewAdapter.clear();
			mMyListViewAdapter.addItem(result);
			mMyListViewAdapter.notifyDataSetChanged();

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			if(null != cursor && !cursor.isClosed()){
				cursor.close();
			}
		}

		//关闭loading
		mOperation.closeLoading();
	}

	@Override
	public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mMyListViewAdapter.clear();
        mMyListViewAdapter.notifyDataSetChanged();
	}

	private static final String _ID = MediaStore.Files.FileColumns._ID;
	private static final String STORAGE_ID = "storage_id";
	private static final String SIZE = MediaStore.Files.FileColumns.SIZE;
	private static final String TITLE = MediaStore.Files.FileColumns.TITLE;
	private static final String DATE_ADDED = MediaStore.Files.FileColumns.DATE_ADDED;
	private static final String DATA = MediaStore.Files.FileColumns.DATA;
	private static final String DISPLAY_NAME = MediaStore.Files.FileColumns.DISPLAY_NAME;
	private static final String MIME_TYPE = MediaStore.Files.FileColumns.MIME_TYPE;
	private static final String MEDIA_TYPE = MediaStore.Files.FileColumns.MEDIA_TYPE;
	private static final String BUCKET_DISPLAY_NAME = "bucket_display_name";

	private static final String[] COLUMNS_NAME = {
			_ID,
			STORAGE_ID,
			TITLE,
			DATE_ADDED,
			SIZE,
			DATA,
			DISPLAY_NAME,
			MIME_TYPE,
			MEDIA_TYPE,
			BUCKET_DISPLAY_NAME
	};

	@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
	public static String getMediaDuring(String mUri) {
		String duration = "";
		android.media.MediaMetadataRetriever mmr = new android.media.MediaMetadataRetriever();
		try {
			if (mUri != null) {
				mmr.setDataSource(mUri);
			}
			duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);
		} catch (Exception ex) {
		} finally {
			mmr.release();
		}
		return duration;
	}

	public static Long getMediaDuringLong(String strDuration) {
		Long duration = 0L;
		try {
			if(!TextUtils.isEmpty(strDuration)){
				duration = Long.parseLong(strDuration);
			}
		} catch (Throwable ex) {
		}
		return duration;
	}
}
