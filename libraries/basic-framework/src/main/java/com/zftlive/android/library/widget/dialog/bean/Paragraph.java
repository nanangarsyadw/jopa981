package com.zftlive.android.library.widget.dialog.bean;

import java.io.Serializable;

/**
 * 段落文本样式数据模型
 *
 * @author 曾繁添
 * @version 1.0
 */
public class Paragraph implements Serializable {

    private static final long serialVersionUID = -2393959338350108214L;

    public Paragraph() {
    }

    public Paragraph(CharSequence title, CharSequence subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public CharSequence title = "";

    public String titleTextColor = "#444444";

    public CharSequence subTitle = "";

    public String subTitleTextColor = "#666666";

}
