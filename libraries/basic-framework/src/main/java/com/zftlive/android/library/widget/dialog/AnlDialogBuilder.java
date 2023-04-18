package com.zftlive.android.library.widget.dialog;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;

import com.zftlive.android.library.R;
import com.zftlive.android.library.base.ui.BaseDialog;
import com.zftlive.android.library.widget.dialog.bean.ButtonBean;
import com.zftlive.android.library.widget.dialog.bean.ItemBean;
import com.zftlive.android.library.widget.dialog.bean.Paragraph;
import com.zftlive.android.library.widget.dialog.listener.ItemClickListener;
import com.zftlive.android.library.widget.dialog.listener.OperationClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 共通对话框构造器
 *
 * @author 曾繁添
 * @version 1.0
 */
public class AnlDialogBuilder implements IDialogConstant {

    protected Activity mContext;
    protected BaseDialog.CancelListener mCancelListener;
    protected int mStyleResId = 0;
    protected int mDialogAnim = 0;
    protected int mGravity = Gravity.CENTER;
    protected boolean cancelable = true;
    protected boolean canCancelOutside = true;
    protected boolean isFillScreenWith = false;
    protected int mDialogWidthDp = 280;
    protected int mBodyMsgHeight = 0;
    protected float mDimAmount = -1f;
    protected boolean isShowTitle = false;
    protected CharSequence mWinMainTitle = "";;
    protected CharSequence mWinSubTitle = "";;
    protected boolean isShowClose = true;
    protected List<ItemBean> mItemData = new ArrayList<ItemBean>();
    protected ItemClickListener mItemClickListener;
    protected Boolean isShowItemLastLine = true;
    protected CharSequence mBodyTitle = "";
    protected CharSequence mBodyMsg = "";
    protected int mIntBodyMsgColor = 0;
    protected int mOperateBtnDirection = HORIZONTAL;
    protected ArrayList<ButtonBean> mOperationBtnList = new ArrayList<ButtonBean>();
    protected OperationClickListener mOperateClickListener;
    protected View mCustomView;
    protected boolean isShowButtomBlank = false;
    protected int iconRes = 0;
    protected String iconURL = "";;
    protected boolean isTransparentBackground = false;
    protected long mDelayedTime = 0;
    protected boolean clickDismissDialog = true;
    protected ArrayList<Paragraph> mParagraphList = new ArrayList<>();
    protected IDialogProxy mDialogProxy;

    public AnlDialogBuilder(Activity mContext) {
        this.mContext = mContext;
        mStyleResId = R.style.Anl_DialogTopButtomAnimation;
    }

    public AnlDialogBuilder setIcon(int iconRes) {
        this.iconRes = iconRes;
        return this;
    }

    public AnlDialogBuilder setIconURL(String iconURL) {
        this.iconURL = iconURL;
        return this;
    }

    /**
     * 是否带动画弹出Dialog
     *
     * @param hasAnim
     * @return
     */
    public AnlDialogBuilder hasAnimation(boolean hasAnim) {
        if (!hasAnim) {
            mStyleResId = R.style.Anl_DialogNoAnimation;
        }
        return this;
    }

    /**
     * 是否显示标题栏关闭按钮
     *
     * @param isShow
     * @return
     */
    public AnlDialogBuilder showTitleClose(boolean isShow) {
        this.isShowClose = isShow;
        return this;
    }

    /**
     * 是否显示窗体标题栏Header
     *
     * @param isShow
     * @return
     */
    public AnlDialogBuilder showTopHeader(boolean isShow) {
        this.isShowTitle = isShow;
        return this;
    }

    /**
     * 是否显示底部Footer
     *
     * @param isShow
     * @return
     */
    public AnlDialogBuilder showButtomFooter(boolean isShow) {
        this.isShowButtomBlank = isShow;
        return this;
    }

    /**
     * 设置窗体主标题，支持HTML格式文本
     *
     * @param mWinMainTitle
     * @return
     */
    public AnlDialogBuilder setWinMainTitle(CharSequence mWinMainTitle) {
        this.mWinMainTitle = mWinMainTitle;
        return this;
    }

    /**
     * 设置窗体副标题，支持HTML格式文本
     *
     * @param mWinSubTitle
     * @return
     */
    public AnlDialogBuilder setWinSubTitle(CharSequence mWinSubTitle) {
        this.mWinSubTitle = mWinSubTitle;
        return this;
    }

    /**
     * 设置窗体Body区域的标题，支持HTML格式文本
     *
     * @param mBodyTitle
     * @return
     */
    public AnlDialogBuilder setBodyTitle(CharSequence mBodyTitle) {
        this.mBodyTitle = mBodyTitle;
        return this;
    }

    /**
     * 设置窗体Body区域的消息文本，支持HTML格式文本
     *
     * @param mBodyMsg
     * @return
     */
    public AnlDialogBuilder setBodyMsg(CharSequence mBodyMsg) {
        this.mBodyMsg = mBodyMsg;
        return this;
    }

    /**
     * 设置操作按钮的方向，水平/垂直,默认水平方向OperationDialog.HORIZONTAL
     *
     * @param mDirection
     * @return
     */
    public AnlDialogBuilder setOperationBtnDirection(int mDirection) {
        this.mOperateBtnDirection = mDirection;
        return this;
    }

    /**
     * 添加段落文本内容
     *
     * @param mParagraph 段落文本
     * @return
     */
    public AnlDialogBuilder addParagraph(Paragraph mParagraph) {
        mParagraphList.add(mParagraph);
        return this;
    }

    /**
     * 添加操作按钮
     *
     * @param btn 按钮属性Bean
     * @return
     */
    public AnlDialogBuilder addOperationBtn(ButtonBean btn) {
        if ( null != btn ){
            mOperationBtnList.add(btn);
        }
        return this;
    }

    /**
     * 添加操作按钮
     *
     * @param id 按钮id
     * @param label 按钮文本
     * @return
     */
    public AnlDialogBuilder addOperationBtn(int id, String label) {
        mOperationBtnList.add(new ButtonBean(id,label));
        return this;
    }

    /**
     * 添加操作按钮
     *
     * @param id 按钮id
     * @param label 按钮文本
     * @param textColor 按钮文本字体颜色
     * @return
     */
    public AnlDialogBuilder addOperationBtn(int id, String label, String textColor) {
        mOperationBtnList.add(new ButtonBean(id,label,textColor));
        return this;
    }

    /**
     * 添加操作按钮
     *
     * @param id 按钮id
     * @param label 按钮文本
     * @param textColor 按钮文本字体颜色
     * @param tag 按钮tag
     * @return
     */
    public AnlDialogBuilder addOperationBtn(int id, String label, String textColor, Object tag) {
        mOperationBtnList.add(new ButtonBean(id,label,textColor,tag));
        return this;
    }

    /**
     * 在指定的位置索引添加操作按钮
     *
     * @param btn 按钮属性Bean
     * @return
     */
    public AnlDialogBuilder addOperationBtn(int index, ButtonBean btn) {
        mOperationBtnList.add(index, btn);
        return this;
    }

    /**
     * 添加操作按钮集合
     *
     * @param btn 按钮属性Bean集合
     * @return
     */
    public AnlDialogBuilder addOperationBtn(List<ButtonBean> btn) {
        mOperationBtnList.addAll(btn);
        return this;
    }

    /**
     * 添加item行数据
     *
     * @param btn 按钮属性Bean
     * @return
     */
    public AnlDialogBuilder addItemData(ItemBean btn) {
        mItemData.add(btn);
        return this;
    }

    /**
     * 在指定的位置索引添加item行数据
     *
     * @param btn 按钮属性Bean
     * @return
     */
    public AnlDialogBuilder addItemData(int index, ItemBean btn) {
        mItemData.add(index, btn);
        return this;
    }

    /**
     * 添加Item列表数据
     *
     * @param mItemData
     * @return
     */
    public AnlDialogBuilder addItemData(List<ItemBean> mItemData) {
        if (null != mItemData) {
            this.mItemData.addAll(mItemData);
        }
        return this;
    }

    /**
     * 设置Item列表数据，推荐使用addItemData方法
     *
     * @param mItemData
     * @return
     */
    @Deprecated
    public AnlDialogBuilder setItemData(List<ItemBean> mItemData) {
        if (null != mItemData) {
            this.mItemData.addAll(mItemData);
        }
        return this;
    }

    /**
     * 设置ItemList最后一根分隔线是否显示
     *
     * @param hasLastLine
     * @return
     */
    public AnlDialogBuilder showItemListLastLine(boolean hasLastLine) {
        this.isShowItemLastLine = hasLastLine;
        return this;
    }

    /**
     * 设置Item点击事件
     *
     * @param mItemClickListener
     * @return
     */
    public AnlDialogBuilder setItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
        return this;
    }

    /**
     * 设置Dialog关闭事件
     *
     * @param mCancelListener
     * @return
     */
    public AnlDialogBuilder setCancelListener(BaseDialog.CancelListener mCancelListener) {
        this.mCancelListener = mCancelListener;
        return this;
    }

    /**
     * 设置Dialog操作按钮点击事件
     *
     * @param mOperateClickListener
     * @return
     */
    public AnlDialogBuilder setOperationClickListener(OperationClickListener mOperateClickListener) {
        this.mOperateClickListener = mOperateClickListener;
        return this;
    }

    /**
     * 设置窗体主题
     *
     * @param mStyleId style样式名称id
     * @return
     */
    public AnlDialogBuilder setTheme(int mStyleId) {
        this.mStyleResId = mStyleId;
        return this;
    }

    /**
     * 设置窗口动画
     *
     * @param mDialogAnim 窗口动画
     * @return
     */
    public AnlDialogBuilder setDialogAnim(int mDialogAnim) {
        this.mDialogAnim = mDialogAnim;
        return this;
    }

    /**
     * 设置窗体宽度
     *
     * @param mWidthDp 窗口宽度dp
     * @return
     */
    public AnlDialogBuilder setDialogWidthDpValue(int mWidthDp) {
        this.mDialogWidthDp = mWidthDp;
        return this;
    }

    /**
     * 设置窗体文本高度
     *
     * @param mHeight 窗口宽度
     * @return
     */
    public AnlDialogBuilder setBodyMsgHeight(int mHeight) {
        this.mBodyMsgHeight = mHeight;
        return this;
    }

    /**
     * 设置窗体所在位置 ，默认Gravity.BOTTOM
     *
     * @param mGravity
     * @return
     */
    public AnlDialogBuilder setGravity(int mGravity) {
        this.mGravity = mGravity;
        return this;
    }

    /**
     * 点击窗体其他地方是否可以关闭
     *
     * @param cancelable
     * @return
     */
    public AnlDialogBuilder setCanceledOnTouchOutside(boolean cancelable) {
        this.canCancelOutside = cancelable;
        return this;
    }

    /**
     * 是否屏蔽返回键
     *
     * @param cancelable
     * @return
     */
    public AnlDialogBuilder setCanceleable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    /**
     * 设置窗体宽度是否撑满屏幕宽度
     *
     * @param isFillScreenWith
     * @return
     */
    public AnlDialogBuilder setFillScreenWith(boolean isFillScreenWith) {
        this.isFillScreenWith = isFillScreenWith;
        return this;
    }

    /**
     * 设置窗体背景的黑暗度,dimAmount在0.0f和1.0f之间，0.0f完全不暗，1.0f全暗;android:backgroundDimEnabled为true有效
     *
     * @param mDimAmount
     * @return
     */
    public AnlDialogBuilder setDimAmount(float mDimAmount) {
        this.mDimAmount = mDimAmount;
        return this;
    }

    /**
     * 设置窗体自定义布局
     *
     * @param mCustomView
     * @return
     */
    public AnlDialogBuilder setCustomView(View mCustomView) {
        this.mCustomView = mCustomView;
        return this;
    }

    /**
     * 设置是否背景为透明
     *
     * @param isTransparentBackground
     * @return
     */
    public AnlDialogBuilder setTransparentBackground(boolean isTransparentBackground) {
        this.isTransparentBackground = isTransparentBackground;
        return this;
    }

    /**
     * 设置是否有关闭延迟，
     */
    public AnlDialogBuilder setCloseDelayTime(long mDelayedTime) {
        this.mDelayedTime = mDelayedTime;
        return this;
    }

    /**
     * 设置点击操作按钮是否关闭对话框
     *
     * @param clickDismissDialog
     * @return
     */
    public AnlDialogBuilder setClickDismissDialog(boolean clickDismissDialog) {
        this.clickDismissDialog = clickDismissDialog;
        return this;
    }

    /**
     * 正文颜色
     * */
    public AnlDialogBuilder setBodyMsgColor(int nBodyMsgColor ){
        mIntBodyMsgColor = nBodyMsgColor;
        return this;
    }

    /**
     * 设置弹窗协作代理类,需要外部提供具体的实现
     * @param mDialogProxy
     * @return
     */
    public AnlDialogBuilder setDialogProxy(IDialogProxy mDialogProxy){
        this.mDialogProxy = mDialogProxy;
        return this;
    }

    /**
     * 创建一个Dialog
     *
     * @return
     */
    public AnlCommonDialog build() {
        return new AnlCommonDialog(this);
    }

}
