/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.zftlive.android.library.common.onekeyshare;

import cn.sharesdk.framework.authorize.AuthorizeAdapter;

/**
 * 一个用于演示{@link AuthorizeAdapter}的例子。
 * <p>
 * 本demo将在授权页面底部显示一个“关注官方微博”的提示框，
 *用户可以在授权期间对这个提示进行控制，选择关注或者不关
 *注，如果用户最后确定关注此平台官方微博，会在授权结束以
 *后执行关注的方法。
 */
public class MyAuthorizeAdapter extends AuthorizeAdapter {

	public void onCreate() {
		// 隐藏标题栏右部的ShareSDK Logo
		hideShareSDKLogo();

//		TitleLayout llTitle = getTitleLayout();
//		llTitle.getTvTitle().setText("xxxx");

//		String platName = getPlatformName();
//		if ("SinaWeibo".equals(platName)
//				|| "TencentWeibo".equals(platName)) {
//			initUi(platName);
//			interceptPlatformActionListener(platName);
//			return;
//		}
//
//		// 使弹出动画失效，只能在onCreate中调用，否则无法起作用
//		if ("KaiXin".equals(platName)) {
//			disablePopUpAnimation();
//		}
//
//		// 下面的代码演示如何设置自定义的授权页面打开动画
//		if ("Douban".equals(platName)) {
//			stopFinish = true;
//			disablePopUpAnimation();
//			View rv = (View) getBodyView().getParent();
//			TranslateAnimation ta = new TranslateAnimation(
//					Animation.RELATIVE_TO_SELF, -1,
//					Animation.RELATIVE_TO_SELF, 0,
//					Animation.RELATIVE_TO_SELF, 0,
//					Animation.RELATIVE_TO_SELF, 0);
//			ta.setDuration(500);
//			rv.setAnimation(ta);
//		}
	}
}
