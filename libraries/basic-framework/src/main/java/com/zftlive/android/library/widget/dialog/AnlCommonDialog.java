package com.zftlive.android.library.widget.dialog;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zftlive.android.library.R;
import com.zftlive.android.library.base.ui.BaseDialog;
import com.zftlive.android.library.widget.dialog.bean.ButtonBean;
import com.zftlive.android.library.widget.dialog.bean.DynamicDialogBean;
import com.zftlive.android.library.widget.dialog.bean.Paragraph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 共通对话框
 *
 * @author 曾繁添
 * @version 1.0
 */
public class AnlCommonDialog extends BaseDialog implements IDialogConstant, View.OnClickListener {

    /**
     * 主标题、副标题,窗体Body消息标题、消息文本
     */
    private TextView mMinTitleTV, mSubTitleTV;

    /**
     * 标题栏关闭按钮
     */
    private ImageButton mTitleCloseBtn;

    /**
     * 标题栏、底部占位View
     */
    private ViewGroup mDialogWindowTitle, mScrollGroup, mHBtnContainer, mVBtnContainer, mRootViewContainer, mMainRoot;

    /**
     * 弹窗构建器
     */
    private AnlDialogBuilder mBuilder;

    /**
     * 24dp的像素值
     */
    private int dp24PxValue = 48, dp12PxValue = 24, sp17LineSpace = 9,sp14PxLinSpace = 7;;

    protected AnlCommonDialog(AnlDialogBuilder mBuilder) {
        super(mBuilder.mContext, mBuilder.mStyleResId);
        this.mBuilder = mBuilder;
        super.mCancelListener = mBuilder.mCancelListener;
        setContentView(R.layout.common_jr_dialog_container);
        dp12PxValue = getPxValueOfDp(12.0f);
        dp24PxValue = getPxValueOfDp(24.0f);
        sp14PxLinSpace = getPxValueOfDp(7.0f);
        sp17LineSpace = getPxValueOfDp(9.0f);
        initView(mBuilder);
    }

    /**
     * 初始化控件
     */
    private void initView(final AnlDialogBuilder mBuilder) {
        //窗体背景
        mRootViewContainer = (LinearLayout) findViewById(R.id.ll_jr_dialog_root);
        mMainRoot = (ViewGroup) findViewById(R.id.rl_dialog_main);
        mMainRoot.setOnClickListener(this);

        mRootViewContainer.setBackgroundDrawable(getDialogBackground());
        if (mBuilder.isTransparentBackground) {
            mRootViewContainer.setBackgroundResource(android.R.color.transparent);
        }
        // Footer-->占位View
        View mButtomBlank = findViewById(R.id.space_buttom);
        mButtomBlank.setVisibility(mBuilder.isShowButtomBlank ? View.VISIBLE : View.GONE);
        // Header-标题栏-->标题栏容器
        mDialogWindowTitle = (RelativeLayout) findViewById(R.id.rl_title);
        mDialogWindowTitle.setVisibility(mBuilder.isShowTitle ? View.VISIBLE : View.GONE);
        // Header-标题栏-->主标题
        mMinTitleTV = (TextView) findViewById(R.id.tv_main_title);
        mMinTitleTV.setText(mBuilder.mWinMainTitle);
        mMinTitleTV.setVisibility(TextUtils.isEmpty(mBuilder.mWinMainTitle) ? View.GONE : View.VISIBLE);
        // Header-标题栏-->副标题
        mSubTitleTV = (TextView) findViewById(R.id.tv_sub_title);
        mSubTitleTV.setText(mBuilder.mWinSubTitle);
        mSubTitleTV.setVisibility(TextUtils.isEmpty(mBuilder.mWinSubTitle) ? View.GONE : View.VISIBLE);
        // Header-标题栏-->关闭按钮
        mTitleCloseBtn = (ImageButton) findViewById(R.id.ib_close);
        mTitleCloseBtn.setVisibility(mBuilder.isShowClose ? View.VISIBLE : View.GONE);
        mTitleCloseBtn.setOnClickListener(this);

        //对话框滚动内容
        mScrollGroup = (ViewGroup) findViewById(R.id.ll_dialog_content);
        mScrollGroup.removeAllViews();
        mScrollGroup.addView(getSpaceView());
        //动态添加内容
        makeScrollContent();
        mScrollGroup.addView(getSpaceView());
        //水平垂直操作按钮
        mHBtnContainer = (ViewGroup) findViewById(R.id.ll_h_opreation_btns);
        mVBtnContainer = (ViewGroup) findViewById(R.id.ll_v_opreation_btns);
        buildOpreationButton(mBuilder);

        //窗体window配置
        configWindow();
    }

    /**
     * 配置窗体属性
     */
    private void configWindow() {
        //弹窗动画
        if (DIALOG_ANIM_NONE != mBuilder.mDialogAnim) {
            getWindow().setWindowAnimations(mBuilder.mDialogAnim);
        }
        // 设置窗体显示的位置
        getWindow().setGravity(mBuilder.mGravity);
        if(Gravity.BOTTOM == mBuilder.mGravity){
            RelativeLayout.LayoutParams mDialogLp = (RelativeLayout.LayoutParams)mRootViewContainer.getLayoutParams();
//            mDialogLp.addRule();
        }
        // 初始化窗体属性
        WindowManager.LayoutParams windowparams = getWindow().getAttributes();
        if (mBuilder.mDialogWidthDp > 0) {
            mRootViewContainer.getLayoutParams().width = getPxValueOfDp(mBuilder.mDialogWidthDp);
            windowparams.width = getPxValueOfDp(mBuilder.mDialogWidthDp);
        }
        // 是否撑满屏幕宽度
        if (mBuilder.isFillScreenWith) {
            windowparams.width = mActivity.getResources().getDisplayMetrics().widthPixels;
            mRootViewContainer.getLayoutParams().width = windowparams.width;
            mBuilder.mDialogWidthDp = (int) (windowparams.width * 1.0f / mActivity.getResources().getDisplayMetrics().density);
        }
        // 设置背景黑暗度
        if (mBuilder.mDimAmount >= 0f && mBuilder.mDimAmount <= 1f) {
            windowparams.dimAmount = mBuilder.mDimAmount;
        }
        windowparams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        windowparams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        // 设置属性
        getWindow().setAttributes(windowparams);
        // 点击其他区域是否关闭窗体
        setCanceledOnTouchOutside(mBuilder.canCancelOutside);
        //是否屏蔽返回键
        setCancelable(mBuilder.cancelable);
    }

    /**
     * 构建滚动区域内容
     */
    private void makeScrollContent() {

        ArrayList<DynamicDialogBean> mScrollContent = new ArrayList<>();
        //1、图片类型
        if (!TextUtils.isEmpty(mBuilder.iconURL)) {
            mScrollContent.add(new DynamicDialogBean(TYPE_ITEM_IMAGE, mBuilder.iconURL));
        }
        if (TextUtils.isEmpty(mBuilder.iconURL) && mBuilder.iconRes != 0) {
            mScrollContent.add(new DynamicDialogBean(TYPE_ITEM_IMAGE, mBuilder.iconRes));
        }
        //2、主标题
        if (!TextUtils.isEmpty(mBuilder.mBodyTitle)) {
            mScrollContent.add(new DynamicDialogBean(TYPE_ITEM_TITLE, mBuilder.mBodyTitle, ""));
        }
        //3、消息文本
        if (!TextUtils.isEmpty(mBuilder.mBodyMsg)) {
            mScrollContent.add(new DynamicDialogBean(TYPE_ITEM_BODY_MSG, "", mBuilder.mBodyMsg));
        }
        //4、段落样式
        if (null != mBuilder.mParagraphList && !mBuilder.mParagraphList.isEmpty()) {
            mScrollContent.add(new DynamicDialogBean(TYPE_ITEM_PARAGRAPH_MSG, "", mBuilder.mParagraphList));
        }

        //开始动态生成UI
        final int mItemTopMargin = dp12PxValue;
        final int mLRMargin = dp24PxValue;
        for (int i = 0; i < mScrollContent.size(); i++) {
            DynamicDialogBean mItem = mScrollContent.get(i);
            LinearLayout.LayoutParams mItemLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mItemLp.leftMargin = mLRMargin;
            mItemLp.rightMargin = mLRMargin;
            if (i != 0) {
                mItemLp.topMargin = mItemTopMargin;
            }

            switch (mItem.type) {
                case TYPE_ITEM_IMAGE:
                    ImageView mIcon = new ImageView(mActivity);
                    mItemLp.leftMargin = 0;
                    mItemLp.rightMargin = 0;
                    mIcon.setMinimumHeight(getPxValueOfDp(50));
                    mIcon.setImageResource(R.drawable.anl_common_default_picture);
                    mIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    mIcon.setLayoutParams(mItemLp);
                    mIcon.setAdjustViewBounds(true);
                    if (!TextUtils.isEmpty(mItem.imageURL)) {
                        if(mBuilder.mDialogProxy != null){
                            mBuilder.mDialogProxy.displayImage(mIcon,mItem.imageURL);
                        }
                    } else if (TextUtils.isEmpty(mItem.imageURL) && mItem.iconResId != 0) {
                        mItemLp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                        mIcon.setImageResource(mItem.iconResId);
                    } else {
                        mIcon.setVisibility(View.GONE);
                    }
                    mScrollGroup.addView(mIcon);
                    break;
                case TYPE_ITEM_TITLE:
                    TextView mTitle = (TextView) LayoutInflater.from(mActivity).inflate(R.layout.common_jr_dialog_item_title, mScrollGroup, false);
//                    TextView mTitle = new TextView(mActivity);
//                    mTitle.getPaint().setFakeBoldText(true);
                    mTitle.setTextColor(mActivity.getResources().getColor(R.color.anl_black_333333));
                    mTitle.setTextSize(17);
                    mTitle.setGravity(Gravity.CENTER);
                    mTitle.setLineSpacing(sp17LineSpace, 1.0f);
                    mTitle.setText(mItem.title);
                    mTitle.setLayoutParams(mItemLp);
                    mScrollGroup.addView(mTitle);
                    break;
                case TYPE_ITEM_BODY_MSG:
                    TextView mMsgText = (TextView) LayoutInflater.from(mActivity).inflate(R.layout.common_jr_dialog_item_msg_haspadding, mScrollGroup, false);
                    mMsgText.setTextColor(mBuilder.mIntBodyMsgColor == 0 ? mActivity.getResources().getColor(R.color.anl_black_666666) : mBuilder.mIntBodyMsgColor);
                    mMsgText.setTextSize(14);
                    mMsgText.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                    mMsgText.setLineSpacing(sp14PxLinSpace, 1.0f);
                    mMsgText.setText(mItem.bodyMsg);
                    mMsgText.setLayoutParams(mItemLp);
                    mScrollGroup.addView(mMsgText);
                    break;
                case TYPE_ITEM_PARAGRAPH_MSG:
                    ArrayList<Paragraph> paragraph = mBuilder.mParagraphList;
                    if (null == paragraph || paragraph.isEmpty()) return;
                    LinearLayout.LayoutParams mSubTitleLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    mSubTitleLp.leftMargin = mLRMargin;
                    mSubTitleLp.rightMargin = mLRMargin;
                    mSubTitleLp.topMargin = getPxValueOfDp(2.0f);
                    for (int j = 0; j < paragraph.size(); j++) {
                        Paragraph mItemParagraph = paragraph.get(j);

                        //主标题空+副标题不为空
                        if ((j == 0) && TextUtils.isEmpty(mItemParagraph.title) && TextUtils.isEmpty(mItemParagraph.subTitle)) {
                            makeParagraphText(j,mScrollGroup, mItemParagraph, mItemLp, mItemLp);
                        }
                        else if(j > 0 && TextUtils.isEmpty(mItemParagraph.title) && !TextUtils.isEmpty(mItemParagraph.subTitle)) {
                            makeParagraphText(j,mScrollGroup, mItemParagraph, mItemLp, mItemLp);
                        }
                        else{
                            makeParagraphText(j,mScrollGroup, mItemParagraph, mItemLp, mSubTitleLp);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 创建段落格式的文本
     *
     * @param mScrollGroup   追加的容器
     * @param mItemParagraph 段落文本数据
     * @param mItemLp        当前item标题的布局参数
     * @param mSubTitleLp    当前item副标题的布局参数
     */
    private void makeParagraphText(int index,ViewGroup mScrollGroup, Paragraph mItemParagraph, ViewGroup.LayoutParams mItemLp, ViewGroup.LayoutParams mSubTitleLp) {
        int layoutResId = R.layout.common_jr_dialog_item_msg_haspadding;
        if(index > 0 && index < (mBuilder.mParagraphList.size() -1)){
            Log.d(TAG,"R.layout.common_jr_dialog_item_msg_nopadding");
            layoutResId = R.layout.common_jr_dialog_item_msg_nopadding;
        }else{
            Log.d(TAG,"R.layout.common_jr_dialog_item_msg_haspadding");
        }
        if (!TextUtils.isEmpty(mItemParagraph.title)) {
            TextView mPTitle = (TextView) LayoutInflater.from(mActivity).inflate(layoutResId, mScrollGroup, false);
            mPTitle.setTextColor(Color.parseColor("#222222"));
            mPTitle.setTextSize(14);
            mPTitle.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            mPTitle.setLineSpacing(sp14PxLinSpace, 1.0f);
            mPTitle.setText(mItemParagraph.title);
            mPTitle.setLayoutParams(mItemLp);
            Log.d(TAG,"mPTitle.getPaddingBottom()="+mPTitle.getPaddingBottom());
            mScrollGroup.addView(mPTitle);
        }
        if (!TextUtils.isEmpty(mItemParagraph.subTitle)) {
            TextView mPSubTitle = (TextView) LayoutInflater.from(mActivity).inflate(layoutResId, mScrollGroup, false);
            mPSubTitle.setTextColor(mActivity.getResources().getColor(R.color.anl_black_666666));
            mPSubTitle.setTextSize(14);
            mPSubTitle.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            mPSubTitle.setLineSpacing(sp14PxLinSpace, 1.0f);
            mPSubTitle.setText(mItemParagraph.subTitle);
            mPSubTitle.setLayoutParams(mSubTitleLp);
            mScrollGroup.addView(mPSubTitle);
            Log.d(TAG,"mPSubTitle.getPaddingBottom()="+mPSubTitle.getPaddingBottom());
        }
    }

    /**
     * 构建按钮，自动根据文本判断按钮方向
     *
     * @param mBuilder
     * @return
     */
    private void buildOpreationButton(final AnlDialogBuilder mBuilder) {
        // 数据合法性校验
        if (null == mBuilder || null == mBuilder.mOperationBtnList
                || mBuilder.mOperationBtnList.isEmpty()) return;
        int size = mBuilder.mOperationBtnList.size();

        //根据按钮文本长度判断按钮方向（>=4 垂直方向）
        Map<Integer, ButtonBean> mBtnTextLen = new TreeMap<>();
        for (int i = 0; i < size; i++) {
            ButtonBean mBtnBean = mBuilder.mOperationBtnList.get(i);
            mBtnTextLen.put(mBtnBean.label.length(), mBtnBean);
        }
        int maxLen = 0;
        for (Iterator<Integer> it = mBtnTextLen.keySet().iterator(); it.hasNext(); ) {
            maxLen = it.next();
        }
        mBuilder.mOperateBtnDirection = (maxLen >= 4) ? VERTICAL : HORIZONTAL;

        //确定方向
        ViewGroup mContainner = (HORIZONTAL == mBuilder.mOperateBtnDirection) ? mHBtnContainer : mVBtnContainer;
        mHBtnContainer.setVisibility((HORIZONTAL == mBuilder.mOperateBtnDirection) ? View.VISIBLE : View.GONE);
        mVBtnContainer.setVisibility((mHBtnContainer.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE);

        //构建UI
        int mHeight = getPxValueOfDp(50.0f);
        LinearLayout.LayoutParams mBtnLp =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight);
        mBtnLp.gravity = Gravity.CENTER;
        if (HORIZONTAL == mBuilder.mOperateBtnDirection) {
            mBtnLp.width = 0;
            mBtnLp.weight = 1.0f;
        }
        // 分割线布局参数,没有权重
        int mSplitLineH = getPxValueOfDp(0.34f);
        LinearLayout.LayoutParams mSplitLp =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mSplitLineH);
        if (HORIZONTAL == mBuilder.mOperateBtnDirection) {
            mSplitLp.width = mSplitLineH;
            mSplitLp.height = mHeight;
        } else {
            mSplitLp.height = mSplitLineH;
        }

        // 动态追加按钮
        for (int i = 0; i < size; i++) {
            // 增加分割线
            View split = new View(mActivity);
            split.setBackgroundColor(Color.parseColor("#eeeeee"));
            split.setLayoutParams(mSplitLp);
            mContainner.addView(split);

            ButtonBean mBtnBean = mBuilder.mOperationBtnList.get(i);

            //动态生成文本不居中问题
            Button btn = (Button) LayoutInflater.from(mActivity).inflate(R.layout.common_jr_dialog_item_button,mContainner,false);
            if (null != mBtnBean.tag) {
                btn.setTag(mBtnBean.tag);
            }
            btn.setId(mBtnBean.id);
            btn.setText(mBtnBean.label);
            btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, mBtnBean.textSize);
            if ( mBtnBean.nTextColor != 0 ){
                btn.setTextColor(mBtnBean.nTextColor);
            }
            else if (!TextUtils.isEmpty(mBtnBean.textColor)) {
                btn.setTextColor(Color.parseColor(mBtnBean.textColor));
            }
            btn.setLayoutParams(mBtnLp);
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mBuilder.clickDismissDialog) {
                        dismiss();
                    }

                    if (null != mBuilder.mOperateClickListener) {
                        mBuilder.mOperateClickListener.onClick(v);
                    }
                }
            });
            btn.setFocusable(false);
            mContainner.addView(btn);
        }
    }

    /**
     * 获取24dp间隙
     *
     * @return
     */
    private View getSpaceView() {
        View mSpace = new View(mActivity);
        LinearLayout.LayoutParams mBtuuomSpaceLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp24PxValue);
        //mSpace.setBackgroundColor(mActivity.getResources().getColor(R.color.red));
        mSpace.setLayoutParams(mBtuuomSpaceLp);
        return mSpace;
    }

    /**
     * 获取弹窗背景
     *
     * @return
     */
    private Drawable getDialogBackground() {
        GradientDrawable mSelectedDot = new GradientDrawable();
        mSelectedDot.setCornerRadius(getPxValueOfDp(1));
        mSelectedDot.setColor(mActivity.getResources().getColor(R.color.anl_white));
        mSelectedDot.setStroke(getPxValueOfDp(1), mActivity.getResources().getColor(R.color.anl_white));
        return mSelectedDot;
    }

    /**
     * 关闭动画
     */
    private void cancelAnimation() {
        AnlCommonDialog.super.cancel();

//        AnlDialogBuilder.playOutAnimation(mRootViewContainer, new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                AnlCommonDialog.super.cancel();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
    }

    @Override
    public void cancel() {
        if (mBuilder.mDelayedTime > 0) {
            mRootViewContainer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    cancelAnimation();
                }
            }, mBuilder.mDelayedTime);
        } else {
            cancelAnimation();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ib_close) {
            cancel();
        }
    }
}
