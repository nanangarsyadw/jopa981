package com.zftlive.android.library.widget.dialog;

import android.widget.ImageView;

/**
 *
 * 弹窗代理类(需要外部提供实现)
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public interface IDialogProxy {

    /**
     * 加载图片
     * @param view
     * @param imageURL
     */
    void displayImage(ImageView view, String imageURL);
}
