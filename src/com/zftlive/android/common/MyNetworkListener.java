package com.zftlive.android.common;

import com.zftlive.android.GlobalApplication;
import com.zftlive.android.library.common.NetworkStateService;
import com.zftlive.android.library.tools.ToolAlert;

/**
 * 网络监听Service
 * @author 曾繁添
 * @version 1.0
 *
 */
public class MyNetworkListener extends NetworkStateService {

	@Override
	public void onNoNetwork() {
		ToolAlert.toastShort(GlobalApplication.gainContext(), "OMG 木有网络了~~");
	}

	@Override
	public void onNetworkChange(String networkType) {
		ToolAlert.toastShort(GlobalApplication.gainContext(), "当前网络："+networkType);
	}

}
