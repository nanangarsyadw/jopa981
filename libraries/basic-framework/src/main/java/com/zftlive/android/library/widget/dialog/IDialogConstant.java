package com.zftlive.android.library.widget.dialog;

import android.widget.LinearLayout;


/**
 * 弹窗业务常量
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public interface IDialogConstant {

    /**
     * 方向-水平
     */
    int HORIZONTAL = LinearLayout.HORIZONTAL;

    /**
     * 方向-垂直
     */
    int VERTICAL = LinearLayout.VERTICAL;

    /**
     * 图片类型
     */
    int TYPE_ITEM_IMAGE = 0;

    /**
     * 标题样式
     */
    int TYPE_ITEM_TITLE = 1;

    /**
     * 默认body消息文本
     */
    int TYPE_ITEM_BODY_MSG = 2;

    /**
     * 段落式消息文本
     */
    int TYPE_ITEM_PARAGRAPH_MSG = 3;

    /**
     * 对话框动画-无
     */
    int DIALOG_ANIM_NONE = 0;

    /**
     * 对话框动画-从底部-中间
     */
    int DIALOG_ANIM_BUTTOM_TOP = -1;


}
