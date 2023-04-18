package com.zftlive.android.library.widget.dialog.bean;

import java.io.Serializable;

/**
 * Item点击条目Bean
 */
public class ItemBean implements Serializable {

    public ItemBean() {

    }

    public ItemBean(CharSequence centerTitle) {
        this.centerTitle = centerTitle;
    }

    public ItemBean(CharSequence centerTitle, String centerTitleTextColor) {
        this.centerTitle = centerTitle;
        this.centerTitleTextColor = centerTitleTextColor;
    }

    public ItemBean(CharSequence leftMainTitle, CharSequence leftSubTitle, CharSequence rightMainTitle,
                    Boolean isShowGo) {
        this.leftMainTitle = leftMainTitle;
        this.leftSubTitle = leftSubTitle;
        this.rightMainTitle = rightMainTitle;
        this.isShowGo = isShowGo;
    }

    public ItemBean(CharSequence leftMainTitle, CharSequence leftSubTitle, CharSequence rightMainTitle,
                    Boolean isShowGo, Boolean isShowOkay) {
        this.leftMainTitle = leftMainTitle;
        this.leftSubTitle = leftSubTitle;
        this.rightMainTitle = rightMainTitle;
        this.isShowGo = isShowGo;
        this.isShowOkay = isShowOkay;
    }

    public ItemBean(CharSequence leftMainTitle, CharSequence leftSubTitle, int leftSubTitleBgResId,
                    CharSequence rightMainTitle, Boolean isShowGo) {
        this.leftMainTitle = leftMainTitle;
        this.leftSubTitle = leftSubTitle;
        this.leftSubTitleBgResId = leftSubTitleBgResId;
        this.rightMainTitle = rightMainTitle;
        this.isShowGo = isShowGo;
    }

    public ItemBean(CharSequence leftMainTitle, String leftMainTitleTextColor, CharSequence leftSubTitle,
                    int leftSubTitleBgResId, String leftSubTitleTextColor, CharSequence centerTitle,
                    String centerTitleTextColor, CharSequence rightMainTitle, String rightMainTitleTextColor,
                    Boolean isShowGo, Boolean isShowOkay) {
        this.leftMainTitle = leftMainTitle;
        this.leftMainTitleTextColor = leftMainTitleTextColor;
        this.leftSubTitle = leftSubTitle;
        this.leftSubTitleBgResId = leftSubTitleBgResId;
        this.leftSubTitleTextColor = leftSubTitleTextColor;
        this.centerTitle = centerTitle;
        this.centerTitleTextColor = centerTitleTextColor;
        this.rightMainTitle = rightMainTitle;
        this.rightMainTitleTextColor = rightMainTitleTextColor;
        this.isShowGo = isShowGo;
        this.isShowOkay = isShowOkay;
    }

    /**
     * Item左-主标题
     */
    public CharSequence leftMainTitle;

    /**
     * Item左-主标题字体颜色，默认#333333
     */
    public String leftMainTitleTextColor;

    /**
     * Item左-副标题
     */
    public CharSequence leftSubTitle;

    /**
     * Item左-副标题背景资源名称
     */
    public int leftSubTitleBgResId = 0;

    /**
     * Item左-副标题字体颜色，默认#999999
     */
    public String leftSubTitleTextColor;

    /**
     * Item中间-标题（只有中间一个标题时使用）
     */
    public CharSequence centerTitle;

    /**
     * Item中间-标题（只有中间一个标题时使用）字体颜色，默认#999999
     */
    public String centerTitleTextColor;

    /**
     * Item右-主标题
     */
    public CharSequence rightMainTitle;

    /**
     * Item右-主标题字体颜色，默认#359df5
     */
    public String rightMainTitleTextColor;

    /**
     * 是否显示右箭头图标( > )
     */
    public Boolean isShowGo = true;

    /**
     * 是否显示正确对勾图标
     */
    public Boolean isShowOkay = false;
}
