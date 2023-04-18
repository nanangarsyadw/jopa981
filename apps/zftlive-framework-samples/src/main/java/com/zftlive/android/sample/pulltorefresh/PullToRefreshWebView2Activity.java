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
package com.zftlive.android.sample.pulltorefresh;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.zftlive.android.R;
import com.zftlive.android.library.widget.pulltorefresh.PullToRefreshBase;
import com.zftlive.android.library.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.zftlive.android.library.widget.pulltorefresh.extras.PullToRefreshWebView2;

public final class PullToRefreshWebView2Activity extends Activity implements OnRefreshListener<WebView> {

	/** Called when the activity is first created. */
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ptr_webview2);

		PullToRefreshWebView2 pullRefreshWebView = (PullToRefreshWebView2) findViewById(R.id.pull_refresh_webview2);
		pullRefreshWebView.setOnRefreshListener(this);

		WebView webView = pullRefreshWebView.getRefreshableView();
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new SampleWebViewClient());

		// We just load a prepared HTML page from the assets folder for this
		// sample, see that file for the Javascript implementation
		webView.loadUrl("file:///android_asset/ptr_webview2_sample.html");
        //初始化带返回按钮的标题栏
//  		ActionBarManager.initBackTitle(this, getActionBar(), this.getClass().getSimpleName()); 
	}

	private static class SampleWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	@Override
	public void onRefresh(final PullToRefreshBase<WebView> refreshView) {
		// This is very contrived example, we just wait 2 seconds, then call
		// onRefreshComplete()
		refreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				refreshView.onRefreshComplete();
			}
		}, 2 * 1000);
	}
}
