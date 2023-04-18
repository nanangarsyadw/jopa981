package com.zftlive.android.library.common;

import java.io.Serializable;

/**
 * 用户信息抽象定义
 *
 * @author 曾繁添
 * @version 1.0
 *
 */

public interface IUser extends Serializable {

    /**
     * 获取用户id
     * @return
     */
    String getUserId();

    /**
     * 获取用户名称
     * @return
     */
    String getUserName();

    /**
     * 获取用户头像
     * @return
     */
    String getUserPhoto();

    /**
     * 获取用户电话号码
     * @return
     */
    String getUserTellPhone();

    /**
     * 获取用户住址
     * @return
     */
    String getUserAddress();
}
