package com.zftlive.android.library.widget.dialog.bean;

import java.io.Serializable;

/**
 * 按钮数据模型
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ButtonBean implements Serializable {

    private static final long serialVersionUID = -6492372413064933783L;

    private static final String DEFAULT_COLOR = "#666666";

    public ButtonBean() {
        this(android.R.id.button1, "我知道了");
    }

    public ButtonBean(int id, String label) {
        this(id, label, DEFAULT_COLOR);
    }

    public ButtonBean(int id, String label, Object tag) {
        this(id, label, DEFAULT_COLOR);
        this.tag = tag;
    }

    public ButtonBean(int id, String label, String textColor) {
        this(id, label, 17, textColor);
    }

    public ButtonBean(int id, String label, String textColor, Object tag) {
        this(id, label, 17, textColor);
        this.tag = tag;
    }

    public ButtonBean(int id, String label, int textSize, String textColor) {
        super();
        this.id = id;
        this.label = label;
        this.textSize = textSize;
        this.textColor = textColor;
    }

    public ButtonBean(int id, String label, String textColor, Object tag, boolean blnTag) {
        this(id, label, 17, textColor);
        this.tag = tag;
        mBlnTag = blnTag;
    }

    /**
     * 周小龙添加，为了移植到DailogUtil_ 和 DailogUtil中，增加int型Color
     * */
    public ButtonBean(int id, String label, int nTextColor, int nTextSize , Object tag, boolean blnTag) {
        this.id = id;
        this.label = label;
        this.nTextColor = nTextColor;
        textSize = nTextSize;
        this.tag = tag;
        mBlnTag = blnTag;
    }

    public ButtonBean(int id, String label, String strTextColor, int nTextSize , Object tag, boolean blnTag) {
        this.id = id;
        this.label = label;
        this.textColor = strTextColor;
        textSize = nTextSize;
        this.tag = tag;
        mBlnTag = blnTag;
    }

    /**
     * 定义按钮的id
     */
    public int id = android.R.id.button1;

    /**
     * 按钮显示文本
     */
    public String label;

    /**
     * 按钮文本字体大小，默认17sp
     */
    public int textSize = 17;

    /**
     * 按钮文本字体颜色，默认#666666
     */
    public String textColor = DEFAULT_COLOR;

    /**
     * int型 按钮文本字体颜色
     * */
    public int nTextColor = 0;

    /**
     * 按钮默认tag
     */
    public Object tag;

    /**
     * 自定义布尔值 标签
     */
    public Object mBlnTag;

}
