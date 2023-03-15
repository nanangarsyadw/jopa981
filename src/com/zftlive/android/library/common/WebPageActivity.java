package com.zftlive.android.library.common;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.system.email.Email;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.zftlive.android.R;
import com.zftlive.android.library.MApplication;
import com.zftlive.android.library.base.BaseActivity;
import com.zftlive.android.library.tools.ToolShareSDK;

/**
 * 展示本地/服务器网页共通界面
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class WebPageActivity extends BaseActivity implements
		View.OnClickListener {

	/**
	 * 打开的网址KEY
	 */
	public final static String LINK_URL = "linkURL";

	/**
	 * 默认网页标题KEY
	 */
	public final static String TITLE = "title";

	/**
	 * 是否显示分享按钮
	 */
	public final static String SHOW_SHARE = "isShowShare";

	/**
	 * 是否允许缩放
	 */
	public final static String IS_CAN_ZOOM = "isCanZoom";

	/**
	 * 网页地址
	 */
	protected String linkURL = "http://git.oschina.net/zftlive";

	/**
	 * 网页标题
	 */
	protected String mCurrentTile = "";

	/**
	 * 是否显示分享按钮
	 */
	private boolean isShowShare = false;
	
	/**
	 * 是否允许缩放
	 */
	private boolean isCanZoom = true;

	/**
	 * 网页加载是否发生错误
	 */
	private boolean isError = false;
	
	private ImageButton ib_webview_back, ib_webview_share;
	private TextView tv_webview_title, tv_error_msg;
	private ProgressBar pb_web_load_progress;
	private RelativeLayout rl_webview;
	private WebView mWebView;
	private LinearLayout ll_reload;
	
	/**
	 * 分享菜单对话框
	 */
	private Dialog shareDialog;
	
	/**
	 * 分享操作工具类
	 */
	private ToolShareSDK mShareSDK;

	@Override
	public int bindLayout() {
		return R.layout.activity_common_webpage;
	}

	@Override
	public View bindView() {
		return null;
	}

	@Override
	public void initParms(Bundle parms) {
		linkURL = parms.getString(LINK_URL);
		mCurrentTile = parms.getString(TITLE);
		isShowShare = parms.getBoolean(SHOW_SHARE, false);
		isCanZoom = parms.getBoolean(IS_CAN_ZOOM, true);
	}

	@Override
	public void initView(View view) {

		// 标题栏返回、分享按钮
		ib_webview_back = (ImageButton) findViewById(R.id.ib_webview_back);
		ib_webview_back.setOnClickListener(this);
		// 设置分享按钮是否显示
		ib_webview_share = (ImageButton) findViewById(R.id.ib_webview_share);
		ib_webview_share.setOnClickListener(this);
		ib_webview_share.setVisibility(isShowShare?view.VISIBLE:View.GONE);
		//初始化分享按钮
		if(isShowShare){
			shareDialog = new SharePopMenuDialog(this,R.style.ShareMenuDialog);
			mShareSDK = ToolShareSDK.gainInstance();
			mShareSDK.init(getApplicationContext());
		}

		// 标题栏、错误提示信息
		tv_webview_title = (TextView) findViewById(R.id.tv_webview_title);
		tv_error_msg = (TextView) findViewById(R.id.tv_error_msg);

		// 加载进度
		pb_web_load_progress = (ProgressBar) findViewById(R.id.pb_web_load_progress);

		// webview、重新加载
		rl_webview = (RelativeLayout) findViewById(R.id.rl_webview);
		mWebView = (WebView) findViewById(R.id.webview);
		ll_reload = (LinearLayout) findViewById(R.id.ll_reload);
		ll_reload.setOnClickListener(this);
		isError = false;
		
		//隐藏标题栏
		hiddeTitleBar();
	}

	@Override
	public void doBusiness(Context mContext) {
		
		// 初始化Webview配置
		WebSettings settings = mWebView.getSettings();
		// 设置是否支持Javascript
		settings.setJavaScriptEnabled(true);
		// 是否支持缩放
		settings.setSupportZoom(isCanZoom);
		// settings.setBuiltInZoomControls(true);
		// if (Build.VERSION.SDK_INT >= 3.0)
		// settings.setDisplayZoomControls(false);
		// 是否显示缩放按钮
		// settings.setDisplayZoomControls(false);
		// 提高渲染优先级
		settings.setRenderPriority(RenderPriority.HIGH);
		// 设置页面自适应手机屏幕
		settings.setUseWideViewPort(true);
		// WebView自适应屏幕大小
		settings.setLoadWithOverviewMode(true);
		// 加载url前，设置不加载图片WebViewClient-->onPageFinished加载图片
		// settings.setBlockNetworkImage(true);
		// 设置网页编码
		settings.setDefaultTextEncodingName("UTF-8");

		mWebView.setWebChromeClient(new UIWebChromeClient());
		mWebView.setWebViewClient(new UIWebViewClient());
		mWebView.setDownloadListener(new DownloadListener() {

			@Override
			public void onDownloadStart(String url, String userAgent,
					String contentDisposition, String mimetype,
					long contentLength) {
				Log.e(TAG, "onDownloadStart-->url:" + url);
				Log.e(TAG, "onDownloadStart-->userAgent:" + userAgent);
				Log.e(TAG, "onDownloadStart-->contentDisposition:"
						+ contentDisposition);
				Log.e(TAG, "onDownloadStart-->mimetype:" + mimetype);
				Log.e(TAG, "onDownloadStart-->contentLength:" + contentLength);

				// 调用系统浏览器下载
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
		mWebView.loadUrl(linkURL);
	}

	@Override
	public void resume() {

	}

	@Override
	public void destroy() {
		
		mWebView.stopLoading();
		// 先移除附属关系
		rl_webview.removeView(mWebView);
		mWebView.removeAllViews();
		mWebView.destroy();
	}

	@Override
	public void onClick(View v) {
		int switchId = v.getId();
		if (R.id.ib_webview_back == switchId) {
			finish();
		} else if (R.id.ib_webview_share == switchId) {
			// 分享界面
			if(null != shareDialog && !shareDialog.isShowing()){
				shareDialog.show();
			}
		} else if (R.id.ll_reload == switchId) {
			mWebView.reload();
			isError = false;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	class UIWebChromeClient extends WebChromeClient {
		/*** 页面加载进度 **/
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
			//不管正常还是发生错误都会进入，需要isError标志位区分
			if (newProgress == 100) {
				pb_web_load_progress.setVisibility(View.GONE);
				
				//如果发生错误显示错误提示
				ll_reload.setVisibility(isError?View.VISIBLE:View.GONE);
				mWebView.setVisibility(isError?View.GONE:View.VISIBLE);
				
				//设置提示信息
				if(isError){
					if(!MApplication.isNetworkReady(getContext())){
						tv_error_msg.setText("亲，网络木有打开哦~");
					}else{
						tv_error_msg.setText("点击刷新");
					}
				}
			} else {
				pb_web_load_progress.setVisibility(View.VISIBLE);
				pb_web_load_progress.setProgress(newProgress);
			}
		}

		/**
		 * 获取到网页标题回调函数
		 */
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			mCurrentTile = title;
			tv_webview_title.setText(title);
		}
	}

	class UIWebViewClient extends WebViewClient {

		/**
		 * 控制网页的链接跳转打开方式（拦截URL，当前界面打开网页所有连接）
		 */
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			linkURL = url;
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			Log.i(TAG, "onPageStarted--->url=" + url);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			// 加载完毕后，开始加载图片
			// view.getSettings().setBlockNetworkImage(false);
			Log.i(TAG, "onPageFinished--->url=" + url);
		}

		/***
		 * 让浏览器支持访问https请求
		 */
		@SuppressLint("NewApi")
		public void onReceivedSslError(WebView view, SslErrorHandler handler,
				SslError error) {
			handler.proceed();
			super.onReceivedSslError(view, handler, error);
			Log.i(TAG, "onReceivedSslError--->" + "加载数据失败");
			isError = true;
		}
		
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			Log.i(TAG, "onReceivedError--->" + "加载数据失败，错误码：" + errorCode
					+ "\n 原因描述：" + description);
			isError = true;
		}
	}
	
	/**
	 * 分享菜单dialog
	 */
	public class SharePopMenuDialog extends Dialog implements View.OnClickListener{
		
		public SharePopMenuDialog(Context context) {
			this(context, R.style.ShareMenuDialog);
		}

		public SharePopMenuDialog(Context context, int theme) {
			super(context, theme);
			this.setContentView(R.layout.common_share_dialog);
			this.getWindow().getAttributes().gravity = Gravity.BOTTOM;
			this.initView();
		}

		private void initView(){
			findViewById(R.id.btn_share_wechat_friend).setOnClickListener(this);
			findViewById(R.id.btn_share_wechat_pyq).setOnClickListener(this);
			findViewById(R.id.btn_share_qzone).setOnClickListener(this);
			findViewById(R.id.btn_share_sina_weibo).setOnClickListener(this);
			findViewById(R.id.btn_share_tencent_weibo).setOnClickListener(this);
			findViewById(R.id.btn_share_qq).setOnClickListener(this);
			findViewById(R.id.btn_share_email).setOnClickListener(this);
			findViewById(R.id.btn_share_sms).setOnClickListener(this);
			findViewById(R.id.btn_cancel).setOnClickListener(this);
		}
		
		@Override
		public void onClick(View v) {
			if(R.id.btn_cancel == v.getId()){
				this.dismiss();
				return;
			}
			
			if(null == mShareSDK) return;
			
			// 组装分享参数
			final ShareParams parms = new ShareParams();
			parms.setText(mCurrentTile);
			parms.setTitle(mCurrentTile);
			parms.setTitleUrl(linkURL);
			parms.setSite(mCurrentTile);
			parms.setSiteUrl("http://www.zftlive.com");
			parms.setUrl(linkURL);
			Bitmap imageData = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_launcher);
			parms.setImageData(imageData);
			parms.setShareType(Platform.SHARE_WEBPAGE);
			
			Platform platform = null;
			if(R.id.btn_share_wechat_friend == v.getId()){
				platform = mShareSDK.initPlatform(Wechat.NAME);
			}else if(R.id.btn_share_wechat_pyq == v.getId()){
				platform = mShareSDK.initPlatform(WechatMoments.NAME);
			}else if(R.id.btn_share_qzone == v.getId()){
				platform = mShareSDK.initPlatform(QZone.NAME);
			}else if(R.id.btn_share_qq == v.getId()){
				platform = mShareSDK.initPlatform(QQ.NAME);
			}else if(R.id.btn_share_sina_weibo == v.getId()){
				platform = mShareSDK.initPlatform(SinaWeibo.NAME);
			}else if(R.id.btn_share_tencent_weibo == v.getId()){
				platform = mShareSDK.initPlatform(TencentWeibo.NAME);
			}else if(R.id.btn_share_email == v.getId()){
				platform = mShareSDK.initPlatform(Email.NAME);
			}else if(R.id.btn_share_sms == v.getId()){
				platform = mShareSDK.initPlatform(ShortMessage.NAME);
			}
			mShareSDK.share(platform, parms, null);
		}
	}
	
	/**
	 * 横竖屏切换更改配置
	 */
	@Override
	public void onConfigurationChanged(Configuration config) {
		try {
			super.onConfigurationChanged(config);

			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				// 横屏时要处理的代码，
				// 这里的代码是当屏幕横屏时当成竖屏显示
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				// 竖屏时要处理的代码
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
		} catch (Exception ex) {
			Log.e(TAG, "onConfigurationChanged-->exception:" + ex.getMessage());
		}
	}
}
