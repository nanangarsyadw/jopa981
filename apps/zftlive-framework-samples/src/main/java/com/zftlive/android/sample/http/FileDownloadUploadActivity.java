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

package com.zftlive.android.sample.http;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.zftlive.android.R;
import com.zftlive.android.library.Alert;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.network.ToolHTTP;
import com.zftlive.android.library.tools.ToolDateTime;
import com.zftlive.android.library.tools.ToolFile;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.Date;

/**
 * HTTP带进度上传/下载文件示例
 * @author 曾繁添
 * @version 1.0
 *
 */
public class FileDownloadUploadActivity extends CommonActivity implements OnClickListener{

	private Button btn_download,btn_upload;
	private EditText et_downfile_path,et_upload_file_path;
//	public final static String UPLOAD_FILE = "http://10.8.2.86:8080/AJavaCore/cn/com/ajava/servlet/ServletUploadFile";
//	public final static String DOWNLOAD_FILE_PATH = "http://10.8.2.86:8080/AJavaCore/files/Android-Rules中文.pdf";
	
	
//	public final static String UPLOAD_FILE = "http://10.45.255.90:8080/SpringMVC_01/fileUpload.html";
//	public final static String DOWNLOAD_FILE_PATH = "http://10.8.2.101:8080/SpringMVC_01/files/Android-Rules.pdf";
	
	public final static String DOWNLOAD_FILE_PATH = "http://zftlive-images.qiniudn.com/avatar.png";
	public final static String UPLOAD_FILE = "http://10.45.255.90:8080/AjavaWeb/cn/com/ajava/servlet/ServletUploadFile";
		
	@Override
	public int bindLayout() {
		return R.layout.activity_file_upload_download;
	}

	@Override
	public void initParams(Bundle parms) {
		
	}

	@Override
	public void initView(View view) {
		btn_download = (Button) findViewById(R.id.btn_download);
		btn_download.setOnClickListener(this);
		
		btn_upload = (Button) findViewById(R.id.btn_upload);
		btn_upload.setOnClickListener(this);
		
		et_downfile_path = (EditText) findViewById(R.id.et_downfile_path);
		et_downfile_path.setText(DOWNLOAD_FILE_PATH);
		et_upload_file_path = (EditText) findViewById(R.id.et_upload_file_path);
	}

	@Override
	public void doBusiness(Context mContext) {
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.FileDownloadUploadActivity);
//      ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
		mWindowTitle.initBackTitleBar(strCenterTitle);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			
			case R.id.btn_download:
				Alert.loading(getContext(), "准备下载");
				String[] allowType = {".*"};
				
				ToolHTTP.get(DOWNLOAD_FILE_PATH, new BinaryHttpResponseHandler(allowType) {
					
					@Override
					public void onProgress(long bytesWritten, long totalSize) {
						super.onProgress(bytesWritten, totalSize);
						if(bytesWritten>0 && totalSize >0){
							String text = String.format("Progress %d  from %d (%2.0f%%)", bytesWritten, totalSize, (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1);
							Alert.updateProgressText(text);
						}
					}
					
					@Override
					public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
						try {
							String filePath = ToolFile.gainSDCardPath()+"/ajava_download/AjavaAndroidSample"+ToolDateTime.gainCurrentDate("yyyyMMddHHmmss")+".apk";
							ToolFile.write(filePath, binaryData);
							et_upload_file_path.setText(filePath);
						} catch (Exception e) {
							Alert.toastShort(getContext(), "下载失败");
						}
						Alert.closeLoading();
					}
					
					@Override
					public void onFailure(int statusCode, Header[] headers, byte[] binaryData,
							Throwable error) {
						try {
							Alert.toastShort(getContext(), "下载失败，原因："+error.getMessage());
						} catch (Exception e) {
						}
						Alert.closeLoading();
					}
				});
				
			break;
			case R.id.btn_upload:

				Alert.loading(getContext(), "开始上传");
				RequestParams parms = new RequestParams();
				try {
					String filePath = ToolFile.gainSDCardPath()+"/ajava_download/Android-Rules中文.pdf";
//					String filePath = ToolFile.gainSDCardPath()+"/ajava_download/Hydrangeas.jpg";//et_file_path.getText().toString();
					parms.put("date", new Date());
//					filePath = et_upload_file_path.getText().toString();
//					parms.put("file", new FileInputStream(new File(filePath)));

					parms.put("photo_url", new FileInputStream(new File(filePath)),"Android-Rules.pdf");
					parms.put("id", "9");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
//				Header[] header = {new BasicHeader("requestJson","strNotFileRequestJson")};
				ToolHTTP.getClient().post(getContext(), "http://60.29.178.67:8022/ssm/uploadFile/uploadFile", null, parms, "multipart/form-data", getUploadResponseHandler());//
//				ToolHTTP.post(UPLOAD_FILE,parms, getUploadResponseHandler());
				
			break;
		default:
			break;
		}
	}
	
	/**
	 * 文件上传处理Handler
	 */
	private ResponseHandlerInterface getUploadResponseHandler(){
		return new JsonHttpResponseHandler(){

			@Override
			public void onProgress(long bytesWritten, long totalSize) {
				super.onProgress(bytesWritten, totalSize);
				if(bytesWritten>0 && totalSize >0){
					BigDecimal mData1 = new BigDecimal((bytesWritten/(1024))).setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal mData2 = new BigDecimal((totalSize/(1024))).setScale(2, BigDecimal.ROUND_HALF_UP);
					Alert.updateProgressText("上传进度："+mData1 + "/" + mData2 + "kb");
				}
			}
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				try {
					Alert.toastShort(getContext(), "上传成功-->"+response);
					et_downfile_path.setText(response.optString("path"));
				} catch (Exception e) {
					Alert.toastShort(getContext(), "上传失败");
				}
				Alert.closeLoading();
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				try {
					Alert.toastShort(getContext(), "上传失败，原因："+throwable.getMessage());
				} catch (Exception e) {
				}
				Alert.closeLoading();
			}
		};
	}
}
