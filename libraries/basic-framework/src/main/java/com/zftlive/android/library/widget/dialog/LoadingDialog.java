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

package com.zftlive.android.library.widget.dialog;

import android.app.Activity;
import android.view.Gravity;
import android.widget.TextView;

import com.zftlive.android.library.R;
import com.zftlive.android.library.base.ui.BaseDialog;

/**
 * 自定义对话框
 * @author 曾繁添
 * @version 1.0
 */
public class LoadingDialog extends BaseDialog {
	
	/**消息TextView*/
	private TextView tvMsg ; 
	
	public LoadingDialog(Activity context, String strMessage) {
		this(context, R.style.Anl_CustomProgressDialog,strMessage);
	}

	public LoadingDialog(Activity context, int theme, String strMessage) {
		super(context, theme);
		this.setContentView(R.layout.anl_common_loading_dialog);
		this.getWindow().getAttributes().gravity = Gravity.CENTER;
	    tvMsg = (TextView) this.findViewById(R.id.tv_msg);
	    setMessage(strMessage);
	}

	/**
	 * 设置进度条消息
	 * @param strMessage 消息文本
	 */
	public void setMessage(String strMessage){
		if (tvMsg != null) {
			tvMsg.setText(strMessage);
		}
	}
}