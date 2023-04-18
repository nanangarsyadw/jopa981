package com.zftlive.android.library.common;

/**
 * 登录接口回调
 *
 * @author 曾繁添
 * @version 1.0
 */
public abstract class ILoginResponseHandler {

    public abstract void onLoginSucess();

    public void onLoginFailure() {

    }

    public void onLoginCancel() {

    }
}
