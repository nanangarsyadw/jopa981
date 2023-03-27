package com.zftlive.android.sample.http;

import java.io.File;
import java.math.BigDecimal;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.zftlive.android.R;
import com.zftlive.android.library.base.BaseActivity;
import com.zftlive.android.library.common.ActionBarManager;
import com.zftlive.android.library.third.asynchttp.BinaryHttpResponseHandler;
import com.zftlive.android.library.third.asynchttp.JsonHttpResponseHandler;
import com.zftlive.android.library.third.asynchttp.RequestParams;
import com.zftlive.android.library.third.asynchttp.ResponseHandlerInterface;
import com.zftlive.android.library.tools.ToolAlert;
import com.zftlive.android.library.tools.ToolDateTime;
import com.zftlive.android.library.tools.ToolFile;
import com.zftlive.android.library.tools.ToolHTTP;

/**
 * HTTP带进度上传/下载文件示例
 * @author 曾繁添
 * @version 1.0
 *
 */
public class FileDownloadUploadActivity extends BaseActivity implements OnClickListener{

	private Button btn_download,btn_upload;
	private EditText et_downfile_path,et_upload_file_path;
//	public final static String UPLOAD_FILE = "http://10.8.2.86:8080/AJavaCore/cn/com/ajava/servlet/ServletUploadFile";
//	public final static String DOWNLOAD_FILE_PATH = "http://10.8.2.86:8080/AJavaCore/files/Android-Rules中文.pdf";
	
	
//	public final static String UPLOAD_FILE = "http://10.8.2.101:8080/SpringMVC_01/requestUpload.html";
//	public final static String DOWNLOAD_FILE_PATH = "http://10.8.2.101:8080/SpringMVC_01/files/Android-Rules.pdf";
	
	public final static String DOWNLOAD_FILE_PATH = "http://7jpo65.com1.z0.glb.clouddn.com/AjavaAndroidSample.apk";
	public final static String UPLOAD_FILE = "http://10.45.255.90:8080/AjavaWeb/cn/com/ajava/servlet/ServletUploadFile";
		
	@Override
	public int bindLayout() {
		return R.layout.activity_file_upload_download;
	}

	@Override
	public View bindView() {
		return null;
	}

	@Override
	public void initParms(Bundle parms) {
		
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
        initBackTitleBar(strCenterTitle);
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void destroy() {
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			
			case R.id.btn_download:
				ToolAlert.loading(getContext(), "准备下载");
				String[] allowType = {".*"};
				
				ToolHTTP.get(DOWNLOAD_FILE_PATH, new BinaryHttpResponseHandler(allowType) {
					
					@Override
					public void onProgress(int bytesWritten, int totalSize) {
						super.onProgress(bytesWritten, totalSize);
						if(bytesWritten>0 && totalSize >0){
							String text = String.format("Progress %d  from %d (%2.0f%%)", bytesWritten, totalSize, (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1);
							ToolAlert.updateProgressText(text);
						}
					}
					
					@Override
					public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
						try {
							String filePath = ToolFile.gainSDCardPath()+"/ajava_download/AjavaAndroidSample"+ToolDateTime.gainCurrentDate("yyyyMMddHHmmss")+".apk";
							ToolFile.write(filePath, binaryData);
							et_upload_file_path.setText(filePath);
						} catch (Exception e) {
							ToolAlert.toastShort(getContext(), "下载失败");
						}
						ToolAlert.closeLoading();
					}
					
					@Override
					public void onFailure(int statusCode, Header[] headers, byte[] binaryData,
							Throwable error) {
						try {
							ToolAlert.toastShort(getContext(), "下载失败，原因："+error.getMessage());
						} catch (Exception e) {
						}
						ToolAlert.closeLoading();
					}
				});
				
			break;
			case R.id.btn_upload:
				
				ToolAlert.loading(getContext(), "开始上传");
				RequestParams parms = new RequestParams();
				try {
					String filePath = ToolFile.gainSDCardPath()+"/ajava_download/Android-Rules中文.pdf";
//					String filePath = ToolFile.gainSDCardPath()+"/ajava_download/Hydrangeas.jpg";//et_file_path.getText().toString();
//					parms.put("date", new Date());
					if(!TextUtils.isEmpty(et_upload_file_path.getText().toString())){
					  filePath = et_upload_file_path.getText().toString();
					}
					parms.put("file", new File(filePath));
//					parms.put("file2", new File(filePath));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
//				Header[] header = {new BasicHeader("requestJson","strNotFileRequestJson")};
				ToolHTTP.getClient().post(getContext(), UPLOAD_FILE, null, parms, "multipart/form-data", getUploadResponseHandler());//
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
			public void onProgress(int bytesWritten, int totalSize) {
				super.onProgress(bytesWritten, totalSize);
				if(bytesWritten>0 && totalSize >0){
					BigDecimal mData1 = new BigDecimal((bytesWritten/(1024))).setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal mData2 = new BigDecimal((totalSize/(1024))).setScale(2, BigDecimal.ROUND_HALF_UP);
					ToolAlert.updateProgressText("上传进度："+mData1 + "/" + mData2 + "kb");
				}
			}
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				try {
					ToolAlert.toastShort(getContext(), "上传成功-->"+response);
					et_downfile_path.setText(response.optString("path"));
				} catch (Exception e) {
					ToolAlert.toastShort(getContext(), "上传失败");
				}
				ToolAlert.closeLoading();
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				try {
					ToolAlert.toastShort(getContext(), "上传失败，原因："+throwable.getMessage());
				} catch (Exception e) {
				}
				ToolAlert.closeLoading();
			}
		};
	}
}
