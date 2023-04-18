package com.zftlive.android.library.widget.dialog.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 本地动态追加滚动区域view
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class DynamicDialogBean implements Serializable {

    public DynamicDialogBean(int type, String imageURL) {
        this.type = type;
        this.imageURL = imageURL;
    }

    public DynamicDialogBean(int type, int iconResId) {
        this.type = type;
        this.iconResId = iconResId;
    }

    public DynamicDialogBean(int type, CharSequence title, CharSequence bodyMsg) {
        this.type = type;
        this.title = title;
        this.bodyMsg = bodyMsg;
    }

    public DynamicDialogBean(int type,CharSequence title, ArrayList<Paragraph> paragraphList) {
        this.type = type;
        this.title = title;
        this.paragraphList = paragraphList;
    }

    public DynamicDialogBean(int type, CharSequence title, CharSequence bodyMsg, ArrayList<Paragraph> paragraphList) {
        this.type = type;
        this.title = title;
        this.bodyMsg = bodyMsg;
        this.paragraphList = paragraphList;
    }

    /**
     * 类型
     */
    public int type = 0;

    /**
     * 图标地址
     */
    public String imageURL = "";

    /**
     * 本地icon/图片资源
     */
    public int iconResId = 0;

    /**
     * 标题
     */
    public CharSequence title = "";

    /**
     * 消息内容
     */
    public CharSequence bodyMsg = "";

    /**
     * 段落消息
     */
    public ArrayList<Paragraph> paragraphList;

}
