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

package com.zftlive.android.library.widget.webview;
import com.zftlive.android.library.widget.webview.UIWebView.IOnReceivedTitle;

import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class UIWebChromeClient extends android.webkit.WebChromeClient {
	
	private IOnReceivedTitle mIOnReceivedTitle;

	private ProgressBar progressbar;
	
	public UIWebChromeClient() {
		this(null,null);
	}

	public UIWebChromeClient(IOnReceivedTitle mIOnReceivedTitle) {
		this(mIOnReceivedTitle,null);
	}
	
	public UIWebChromeClient(ProgressBar progressbar) {
		this(null,progressbar);
	}
	
	public UIWebChromeClient(IOnReceivedTitle mIOnReceivedTitle,ProgressBar progressbar) {
		this.mIOnReceivedTitle = mIOnReceivedTitle;
		this.progressbar = progressbar;
	}

	/***页面加载进度**/
    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress == 100) {
        	if(null != progressbar){
        		progressbar.setVisibility(View.GONE);
        	}
			//TODO
//        	ToolAlert.closeLoading();
        } else {
        	if(null != progressbar)
        	{
        		if (progressbar.getVisibility() == View.GONE){
                	progressbar.setVisibility(View.VISIBLE);
                }
                progressbar.setProgress(newProgress);
        	}
        }
        super.onProgressChanged(view, newProgress);
    }
    
    /**
     * 获取到网页标题回调函数
     */
    public void onReceivedTitle(WebView view, String title) {
    	if(null != mIOnReceivedTitle){
    		mIOnReceivedTitle.onReceivedTitle(title);
    	}
        super.onReceivedTitle(view, title);
    }

}
