package com.zftlive.android.library.route;

import java.io.Serializable;

/**
 *
 * 跳转模型
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ForwardBean implements Serializable {

    /**
     * 包名
     */
    public String packageName = "";

    /**
     * 跳转组件名称
     */
    public String compmentName = "";

    /**
     * 扩展参数
     */
    public String extParam = "";

}
