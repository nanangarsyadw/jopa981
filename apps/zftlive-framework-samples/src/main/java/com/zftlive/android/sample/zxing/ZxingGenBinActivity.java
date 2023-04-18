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

package com.zftlive.android.sample.zxing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zftlive.android.R;
import com.zftlive.android.library.Alert;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.tools.ToolFile;
import com.zftlive.android.library.tools.ToolPicture;
import com.zftlive.android.library.tools.ToolString;
import com.zftlive.android.library.zxing.QRCode;

/**
 * 生成二维码示例
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ZxingGenBinActivity extends CommonActivity implements View.OnClickListener {

	private EditText et_qr_text;
	private Button btn_make_qr;
	private Button btn_make_bar,btn_make_qr_with_logo;
	private ImageView qr_image,validate_image;
	private Bitmap qrImage,validateCodeImage;

	@Override
	public int bindLayout() {
		return R.layout.activity_gen_qr_image;
	}

	@Override
	public void initParams(Bundle parms) {
		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {
		et_qr_text = (EditText)findViewById(R.id.et_qr_text);
		btn_make_qr = (Button)findViewById(R.id.btn_make_qr);
		btn_make_bar = (Button)findViewById(R.id.btn_make_bar);
		btn_make_qr_with_logo = (Button)findViewById(R.id.btn_make_qr_with_logo);
		qr_image = (ImageView)findViewById(R.id.qr_image);
		validate_image = (ImageView)findViewById(R.id.validate_image);
		
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.ZxingGenBinActivity);
//      ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
		mWindowTitle.initBackTitleBar(strCenterTitle);
	}

	@Override
	public void doBusiness(Context mContext) {
		//初始化值
		if("".equals(et_qr_text.getText().toString())){
			et_qr_text.setText("http://a.app.qq.com/o/simple.jsp?pkgname=com.zftlive.android");
		}
		btn_make_qr.setOnClickListener(this);
		btn_make_qr_with_logo.setOnClickListener(this);
		btn_make_bar.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		//回收bitmap
		if(null != qrImage && !qrImage.isRecycled()){
			qrImage.recycle();
			qrImage = null;
		}

		if(null != validateCodeImage && !validateCodeImage.isRecycled()){
			validateCodeImage.recycle();
			validateCodeImage = null;
		}

		System.gc();
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_make_qr:
				try {
					if("".equals(et_qr_text.getText().toString())){
						Alert.toastShort("请输入要生成二维码内容！");
						return ;
					}

					getOperation().showLoading("正在生成...");

					//回收bitmap
					if(null != qrImage && !qrImage.isRecycled()){
						qrImage.recycle();
						qrImage = null;
					}
					qrImage = QRCode.makeQRImage(et_qr_text.getText().toString(), 400, 400);
					qr_image.setImageBitmap(qrImage);

					//生成图片
					String filePath = ToolFile.gainSDCardPath() + "/MyLive/QRImage/"+ToolString.gainUUID()+".jpg";
					ToolFile.saveAsJPEG(qrImage, filePath);

					getOperation().closeLoading();

					Alert.toastShort("二维码已经保存在："+filePath);

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case R.id.btn_make_qr_with_logo:

				try {
					if("".equals(et_qr_text.getText().toString())){
						Alert.toastShort("请输入要生成二维码内容！");
						return ;
					}

					getOperation().showLoading("正在生成...");

					//回收bitmap
					if(null != qrImage && !qrImage.isRecycled()){
						qrImage.recycle();
						qrImage = null;
					}

					Bitmap  logo = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.ic_launcher);
					qrImage = QRCode.makeQRImageWithLogo(et_qr_text.getText().toString(), 400,logo, 40);
					qr_image.setImageBitmap(qrImage);

					//生成图片
					String filePath = ToolFile.gainSDCardPath() + "/zftlive/QRImage/"+ToolString.gainUUID()+".jpg";
					ToolFile.saveAsJPEG(qrImage, filePath);

					getOperation().closeLoading();

					Alert.toastShort("二维码已经保存在："+filePath);

				} catch (Exception e) {
					e.printStackTrace();
				}

				break;
			case R.id.btn_make_bar:

				try {

					getOperation().showLoading("正在生成...");

					//回收bitmap
					if(null != validateCodeImage && !validateCodeImage.isRecycled()){
						validateCodeImage.recycle();
						validateCodeImage = null;
					}

					//生成图片
					validateCodeImage = ToolPicture.makeValidateCode(200, 30);
					validate_image.setImageBitmap(validateCodeImage);

					Alert.toastShort("验证码值："+ToolPicture.gainValidateCodeValue());

					getOperation().closeLoading();

				} catch (Exception e) {
					e.printStackTrace();
				}

				break;

		}
	}
}
